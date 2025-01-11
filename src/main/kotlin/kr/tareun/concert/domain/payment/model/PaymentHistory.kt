package kr.tareun.concert.domain.payment.model

data class PaymentHistory(
    var historyId: Long = 0,
    var userId: Long,
    var reservationId: Long,
    var paidPoint: Int,
)