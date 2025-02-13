package kr.tareun.concert.domain.payment.model

import kr.tareun.concert.common.enums.PayOrderType

data class PaymentHistory(
    val historyId: Long = 0,
    val userId: Long,
    val orderType: PayOrderType,
    val orderId: Long,
    val paidPoint: Int,
)