package kr.tareun.concert.application.payment.model

import kr.tareun.concert.interfaces.payment.model.ChargeRequest

data class ChargeCommand(
    val userId: Long,
    val amount: Int,
) {
    companion object {
        fun from(chargeRequest: ChargeRequest): ChargeCommand {
            return ChargeCommand(
                userId = chargeRequest.userId,
                amount = chargeRequest.chargeAmount
            )
        }
    }
}
