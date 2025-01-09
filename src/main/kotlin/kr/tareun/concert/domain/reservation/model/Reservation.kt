package kr.tareun.concert.domain.reservation.model

import kr.tareun.concert.application.reservation.model.ReserveCommand
import kr.tareun.concert.domain.concert.model.ConcertSchedule

data class Reservation(
    var reservationId: Long = 0,
    var userId: Long,
    var concertScheduleId: Long,
    var seats: List<Int>,
    var priceAmount: Int,
    var reservationStatus: ReservationStatusType,
) {
    companion object {
        fun from(reserveCommand: ReserveCommand, scheduleInfo: ConcertSchedule): Reservation {
            return Reservation(
                userId = reserveCommand.userId,
                concertScheduleId = reserveCommand.concertScheduleId,
                seats = reserveCommand.seats,
                priceAmount = scheduleInfo.ticketPrice * reserveCommand.seats.size,
                reservationStatus = ReservationStatusType.PENDING
            )
        }
    }
}