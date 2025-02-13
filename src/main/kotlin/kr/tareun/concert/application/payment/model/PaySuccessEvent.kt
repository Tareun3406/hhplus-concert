package kr.tareun.concert.application.payment.model

import kr.tareun.concert.common.enums.PayOrderType
import kr.tareun.concert.domain.payment.model.PaymentHistory

data class PaySuccessEvent(
    val userId: Long,
    val orderType: PayOrderType,
    val orderId: Long,
    val paidPoint: Int,
) {
    companion object {
        fun from(paymentHistory: PaymentHistory): PaySuccessEvent {
            return PaySuccessEvent(
                userId = paymentHistory.userId,
                orderId = paymentHistory.orderId,
                orderType = paymentHistory.orderType,
                paidPoint = paymentHistory.paidPoint,
            )
        }
    }
}
