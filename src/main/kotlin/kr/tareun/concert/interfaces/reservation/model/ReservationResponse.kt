package kr.tareun.concert.interfaces.reservation.model

import java.time.LocalDateTime

data class ReservationResponse(
    val reservationId: Long,
    val userId: Long,
    val scheduleId: Long,
    val seatNumbers: List<Long>,
    val expirationTime: LocalDateTime,
)
