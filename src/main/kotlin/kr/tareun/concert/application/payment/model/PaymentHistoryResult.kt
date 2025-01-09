package kr.tareun.concert.application.payment.model

import kr.tareun.concert.domain.payment.model.PaymentHistory

data class PaymentHistoryResult(
    var historyId: Long = 0,
    var userId: Long,
    var reservationId: Long,
    var paidPoint: Int,
) {
    companion object {
        fun from(paymentHistory: PaymentHistory): PaymentHistoryResult {
            return PaymentHistoryResult(
                historyId = paymentHistory.historyId,
                userId = paymentHistory.userId,
                reservationId = paymentHistory.reservationId,
                paidPoint = paymentHistory.paidPoint
            )
        }
    }
}
