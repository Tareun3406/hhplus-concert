package kr.tareun.concert.application.reservation

import kr.tareun.concert.application.reservation.model.ReserveCommand
import kr.tareun.concert.application.reservation.model.ReservationResult
import kr.tareun.concert.domain.concert.ConcertRepository
import kr.tareun.concert.domain.reservation.ReservationRepository
import kr.tareun.concert.domain.reservation.model.Reservation
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReservationService(
    private val reservationRepository: ReservationRepository,
    private val concertRepository: ConcertRepository,
) {
    @Transactional
    fun reserveConcert(reserveCommand: ReserveCommand): ReservationResult {
        val schedule = concertRepository.getScheduleByScheduleId(reserveCommand.concertScheduleId)
        schedule.addReservedCount(reserveCommand.seatIdList.size)
        concertRepository.saveConcertSchedule(schedule)

        val newReservation = reserveCommand.toReservation(schedule)
        return ReservationResult.from(reservationRepository.saveReserve(newReservation))
    }
}