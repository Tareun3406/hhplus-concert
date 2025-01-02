package kr.tareun.concert.interfaces.payment.model

data class PayRequest (
    val userId: Long,
    val reservationId: Long
)