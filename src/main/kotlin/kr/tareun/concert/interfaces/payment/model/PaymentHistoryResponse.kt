package kr.tareun.concert.interfaces.payment.model

data class PaymentHistoryResponse(
    val historyId: Long,
    val userId: Long,
    val paidAmount: Int,
    val reservationId: Long,
)
