package kr.tareun.concert.application.concert

import kr.tareun.concert.application.concert.model.ConcertResult
import kr.tareun.concert.application.concert.model.ConcertScheduleResult
import kr.tareun.concert.application.concert.model.ConcertSeatResult
import kr.tareun.concert.application.reservation.model.ReservedConcertEvent
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

    @Transactional
    fun handleOnConcertReserved(reservedConcertEvent: ReservedConcertEvent) {
        val schedule = concertRepository.getScheduleByScheduleId(reservedConcertEvent.concertScheduleId)
        schedule.addReservedCount(reservedConcertEvent.seatCount)
        concertRepository.saveConcertSchedule(schedule)

        // 예약된 콘서트의 예약 횟수 캐시데이터 추가.
        val concert = concertRepository.getConcertById(schedule.concertId)
        reservationRepository.incrementCacheReservationCount(concert)

        applicationEventPublisher.publishEvent()
    }
}