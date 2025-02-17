package kr.tareun.concert.application.reservation

import kr.tareun.concert.application.concert.model.ConcertPublishPayEventCommand
import kr.tareun.concert.application.payment.model.OrderPayEvent
import kr.tareun.concert.application.queue.model.QueueOrderExpireEvent
import kr.tareun.concert.application.reservation.model.*
import kr.tareun.concert.common.aop.annotaion.RedisLock
import kr.tareun.concert.common.config.properties.ReservationProperties
import kr.tareun.concert.common.exception.CommonException
import kr.tareun.concert.common.enums.ErrorCode
import kr.tareun.concert.common.enums.PayOrderType
import kr.tareun.concert.domain.concert.ConcertRepository
import kr.tareun.concert.domain.reservation.ReservationRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class ReservationService(
    private val reservationRepository: ReservationRepository,
    private val reservationProperties: ReservationProperties,
    private val concertRepository: ConcertRepository,

    private val applicationEventPublisher: ApplicationEventPublisher,
) {
    @Transactional
    @RedisLock(prefix = "'scheduleSeat:' + #reserveCommand.concertScheduleId + '-'", variableKeys = ["#reserveCommand.seatIdList"], ttlSec = 5)
    fun reserveConcert(reserveCommand: ReserveCommand): ReservationResult {
        // 중복 예약 체크
        val existReservedList = reservationRepository.getAllReservationItemByScheduleIdAndSeatId(reserveCommand.concertScheduleId, reserveCommand.seatIdList)
        if (existReservedList.isNotEmpty()) {
            throw CommonException(ErrorCode.RESERVATION_SEAT_ALREADY_TAKEN)
        }

        // 예약 생성
        val newReservation = reserveCommand.toReservation()
        val resultReservation = reservationRepository.saveReservation(newReservation)

        // 예약 완료 이벤트 발행
        applicationEventPublisher.publishEvent(ReservedConcertEvent.from(resultReservation))
        return ReservationResult.from(resultReservation)
    }

    @Transactional
    fun publishPayOrderEvent(concertPublishPayEventCommand: ConcertPublishPayEventCommand) {
        val schedule = concertRepository.getScheduleByScheduleId(concertPublishPayEventCommand.concertScheduleId)

        val orderPayEvent = OrderPayEvent(
            userid = concertPublishPayEventCommand.userId,
            amount = schedule.ticketPrice * concertPublishPayEventCommand.seats.size,
            orderType = PayOrderType.CONCERT,
            orderId = concertPublishPayEventCommand.reservationId
        )

        // 결제 요청 이벤트 발행
        applicationEventPublisher.publishEvent(orderPayEvent)
    }

    @Transactional
    fun setSuccessConcertReservationStatus(command: ReservationSuccessStatusCommand) {
        val reservation = reservationRepository.getReservationByIdForUpdate(command.reservationId)
        reservation.markedAsPaid()
        reservationRepository.saveReservation(reservation)

        // 토큰 만료 요청 이벤트 발행
        applicationEventPublisher.publishEvent(QueueOrderExpireEvent(command.userId))
    }

    @Transactional
    fun increaseConcertReservationCount(reservedConcertEvent: ReservedConcertEvent) {
        val schedule = concertRepository.getScheduleByScheduleId(reservedConcertEvent.concertScheduleId)
        val concert = concertRepository.getConcertById(schedule.concertId)
        reservationRepository.incrementCacheReservationCount(concert)
    }

    fun getReservationRankedList(rankingSize: Int): List<ReservationRankedConcertResult> {
        val referenceTime = LocalDateTime.now().minusMinutes(reservationProperties.reservationRankingCacheCycleMinute.toLong())
        return reservationRepository.getReservationRankedConcert(rankingSize, referenceTime).map { ReservationRankedConcertResult.from(it) }
    }
}