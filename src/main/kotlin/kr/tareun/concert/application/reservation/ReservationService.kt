package kr.tareun.concert.application.reservation

import kr.tareun.concert.application.payment.model.PayCommand
import kr.tareun.concert.application.payment.model.PaymentHistoryResult
import kr.tareun.concert.application.reservation.model.ReservationRankedConcertResult
import kr.tareun.concert.application.reservation.model.ReserveCommand
import kr.tareun.concert.application.reservation.model.ReservationResult
import kr.tareun.concert.common.aop.annotaion.RedisLock
import kr.tareun.concert.common.config.ReservationProperties
import kr.tareun.concert.common.exception.CommonException
import kr.tareun.concert.common.enums.ErrorCode
import kr.tareun.concert.domain.concert.ConcertRepository
import kr.tareun.concert.domain.payment.PaymentRepository
import kr.tareun.concert.domain.payment.model.PaymentHistory
import kr.tareun.concert.domain.queue.QueueRepository
import kr.tareun.concert.domain.reservation.ReservationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class ReservationService(
    private val reservationRepository: ReservationRepository,
    private val concertRepository: ConcertRepository,
    private val paymentRepository: PaymentRepository,
    private val queueRepository: QueueRepository,

    private val reservationProperties: ReservationProperties,
) {
    @Transactional
    @RedisLock(prefix = "'scheduleSeat:' + #reserveCommand.concertScheduleId + '-'", variableKeys = ["#reserveCommand.seatIdList"], ttlSec = 5)
    fun reserveConcert(reserveCommand: ReserveCommand): ReservationResult {
        // 중복 예약 체크
        val existReservedList = reservationRepository.getAllReservationItemByScheduleIdAndSeatId(reserveCommand.concertScheduleId, reserveCommand.seatIdList)
        if (existReservedList.isNotEmpty()) {
            throw CommonException(ErrorCode.RESERVATION_SEAT_ALREADY_TAKEN)
        }

        val schedule = concertRepository.getScheduleByScheduleId(reserveCommand.concertScheduleId)
        schedule.addReservedCount(reserveCommand.seatIdList.size)
        concertRepository.saveConcertSchedule(schedule)

        val newReservation = reserveCommand.toReservation()
        val resultReservation = reservationRepository.saveReservation(newReservation)

        // 예약된 콘서트의 예약 횟수 캐시데이터 추가.
        val concert = concertRepository.getConcertById(schedule.concertId)
        reservationRepository.incrementCacheReservationCount(concert)

        return ReservationResult.from(resultReservation)
    }

    @Transactional
    fun payReservation(payCommand: PayCommand): PaymentHistoryResult {
        val point = paymentRepository.getPointByUserIdForUpdate(payCommand.userId)
        val reservation = reservationRepository.getReservationByIdForUpdate(payCommand.reservationId)
        val schedule = concertRepository.getScheduleByScheduleId(reservation.concertScheduleId)
        val priceAmount = schedule.ticketPrice * reservation.seatIds.size

        point.payPoint(priceAmount)

        val paymentHistory = PaymentHistory(
            userId = payCommand.userId,
            reservationId = reservation.reservationId,
            paidPoint = priceAmount
        )
        paymentRepository.savePoint(point)

        reservation.markedAsPaid()
        reservationRepository.saveReservation(reservation)

        queueRepository.removeActivatedQueueToken(payCommand.tokenUuid)

        return PaymentHistoryResult.from(paymentRepository.savePaymentHistory(paymentHistory))
    }

    fun getReservationRankedList(rankingSize: Int): List<ReservationRankedConcertResult> {
        val referenceTime = LocalDateTime.now().minusMinutes(reservationProperties.reservationRankingCacheCycleMinute.toLong())
        return reservationRepository.getReservationRankedConcert(rankingSize, referenceTime).map { ReservationRankedConcertResult.from(it) }
    }
}