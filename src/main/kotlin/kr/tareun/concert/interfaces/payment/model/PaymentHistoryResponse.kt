package kr.tareun.concert.interfaces.payment.model

import kr.tareun.concert.application.payment.model.PaymentHistoryResult

data class PaymentHistoryResponse(
    val historyId: Long,
    val userId: Long,
    val paidAmount: Int,
    val reservationId: Long,
) {
    companion object {
        fun from(paymentHistory: PaymentHistoryResult): PaymentHistoryResponse {
            return PaymentHistoryResponse(
                historyId = paymentHistory.historyId,
                userId = paymentHistory.userId,
                paidAmount = paymentHistory.paidPoint,
                reservationId = paymentHistory.reservationId,
            )
        }
    }
}
