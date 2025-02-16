package kr.tareun.concert.application.concert

import kr.tareun.concert.application.concert.model.*
import kr.tareun.concert.application.payment.model.OrderPayEvent
import kr.tareun.concert.application.reservation.model.RequestedReserveConcertEvent
import kr.tareun.concert.common.enums.PayOrderType
import kr.tareun.concert.domain.concert.ConcertRepository
import kr.tareun.concert.domain.reservation.ReservationRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class ConcertService(
    private val concertRepository: ConcertRepository,
    private val reservationRepository: ReservationRepository,

    private val applicationEventPublisher: ApplicationEventPublisher
) {
    fun retrieveConcertList(pageNumber: Int): List<ConcertResult> {
        return concertRepository.getConcertList(pageNumber).map{ ConcertResult.from(it) }
    }

    fun retrieveConcertScheduleList(concertId: Long): List<ConcertScheduleResult> {
        return concertRepository.getConcertScheduleListByConcertId(concertId).map { ConcertScheduleResult.from(it) }
    }

    fun retrieveConcertSeatList(scheduleId: Long): List<ConcertSeatResult> {
        val schedule = concertRepository.getScheduleByScheduleId(scheduleId)
        val seatList = concertRepository.getConcertSeatListByLocationId(schedule.locationId)
        val seatReservationMap = reservationRepository.getAllValidReservationItem(scheduleId)
            .associateBy { it.seatId }

        return seatList.map { ConcertSeatResult.from(it, seatReservationMap[it.seatId]) }
    }

    fun increaseConcertReserveCount(concertScheduleId: Long) {
        val concertSchedule = concertRepository.getScheduleByScheduleId(concertScheduleId)

        // 예약된 콘서트의 예약 횟수 캐시데이터 추가.
        val concert = concertRepository.getConcertById(concertSchedule.concertId)
        applicationEventPublisher.publishEvent(RequestedReserveConcertEvent(concert))
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
}