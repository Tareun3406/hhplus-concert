package kr.tareun.concert.domain.payment.model

data class PaymentHistory(
    val historyId: Long = 0,
    val userId: Long,
    val reservationId: Long,
    val paidPoint: Int,
)