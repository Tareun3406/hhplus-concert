package kr.tareun.concert.application.reservation.model

import kr.tareun.concert.domain.reservation.model.Reservation

data class ReservationResult(
    val reservationId: Long,
    val userId: Long,
    val scheduleId: Long,
    val priceAmount: Int,
    val seats: List<Int>,
) {
    companion object {
        fun from(reservation: Reservation): ReservationResult {
            return ReservationResult(
                reservationId = reservation.reservationId,
                userId = reservation.userId,
                scheduleId = reservation.concertScheduleId,
                priceAmount = reservation.priceAmount,
                seats = reservation.seats,
            )
        }
    }
}
