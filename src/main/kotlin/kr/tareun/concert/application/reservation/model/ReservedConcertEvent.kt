package kr.tareun.concert.application.reservation.model

import kr.tareun.concert.domain.reservation.model.Reservation

data class ReservedConcertEvent(
    val reservationId: Long,
    val userId: Long,
    val concertScheduleId: Long,
    val seatCount: Int
) {
    companion object {
        fun from(reservation: Reservation): ReservedConcertEvent {
            return ReservedConcertEvent(
                reservationId = reservation.reservationId,
                userId = reservation.userId,
                concertScheduleId = reservation.concertScheduleId,
                seatCount = reservation.seatIds.size
            )
        }
    }
}