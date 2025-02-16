package kr.tareun.concert.application.payment.model

import kr.tareun.concert.common.enums.PayOrderType

data class OrderPayEvent(
    val userid: Long,
    val amount: Int,
    val orderType: PayOrderType,
    val orderId: Long,
)
