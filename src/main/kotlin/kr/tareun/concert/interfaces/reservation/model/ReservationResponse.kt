package kr.tareun.concert.interfaces.reservation.model

import kr.tareun.concert.application.reservation.model.ReservationResult

data class ReservationResponse(
    val reservationId: Long,
    val userId: Long,
    val scheduleId: Long,
    val seatNumbers: List<Int>,
) {
    companion object {
        fun from(reservationResult: ReservationResult): ReservationResponse {
            return ReservationResponse(
                reservationId = reservationResult.reservationId,
                userId = reservationResult.userId,
                scheduleId = reservationResult.scheduleId,
                seatNumbers = reservationResult.seats,
            )
        }
    }
}
