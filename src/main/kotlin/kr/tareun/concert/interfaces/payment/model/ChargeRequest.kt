package kr.tareun.concert.interfaces.payment.model

data class ChargeRequest(
    val userId: Long,
    val chargeAmount: Int
)