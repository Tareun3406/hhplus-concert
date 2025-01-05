package kr.tareun.concert.domain.payment.model

data class ConcertPaymentHistoryInfo(
    val id: Long,
    val userId: Long,
    val reservationId: Long,
    val baseAmount: Long,
    val paidAmount: Long,
    val settledAmount: Long,
)