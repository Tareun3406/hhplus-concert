package kr.tareun.concert.application.concert

import kr.tareun.concert.application.concert.model.*
import kr.tareun.concert.domain.concert.ConcertRepository
import kr.tareun.concert.domain.reservation.ReservationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class ConcertService(
    private val concertRepository: ConcertRepository,
    private val reservationRepository: ReservationRepository,
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
}