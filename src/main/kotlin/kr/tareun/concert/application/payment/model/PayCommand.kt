package kr.tareun.concert.application.payment.model

import kr.tareun.concert.common.enums.PayOrderType

data class PayCommand(
    val userId: Long,
    val amount: Int,
    val orderType: PayOrderType,
    val orderId: Long,
) {
    companion object {
        fun from(orderPayEvent: OrderPayEvent): PayCommand {
            return PayCommand(
                userId = orderPayEvent.userid,
                amount = orderPayEvent.amount,
                orderType = orderPayEvent.orderType,
                orderId = orderPayEvent.orderId,
            )
        }
    }
}
