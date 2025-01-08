package kr.tareun.concert.domain.reservation.model

import java.time.LocalDateTime

data class ReservationInfo(
    val id: Long,
    val concertId: Long,
    val seatNumbers: List<Long>,
    val userId: Long,
    val expirationTime: LocalDateTime,
    val paymentStatus: ReservationStatusType,
)