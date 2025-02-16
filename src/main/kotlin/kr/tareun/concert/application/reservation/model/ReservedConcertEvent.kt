package kr.tareun.concert.application.reservation.model

import kr.tareun.concert.domain.reservation.model.Reservation

data class ReservedConcertEvent(
    val reservationId: Long,
    val userId: Long,
    val concertScheduleId: Long,
    val seats: List<Long>
) {
    companion object {
        fun from(reservation: Reservation): ReservedConcertEvent {
            return ReservedConcertEvent(
                reservationId = reservation.reservationId,
                userId = reservation.userId,
                concertScheduleId = reservation.concertScheduleId,
                seats = reservation.seatIds
            )
        }
    }
}