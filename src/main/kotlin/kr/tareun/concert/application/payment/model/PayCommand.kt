package kr.tareun.concert.application.payment.model

import kr.tareun.concert.interfaces.payment.model.PayRequest

data class PayCommand(
    val userId: Long,
    val reservationId: Long
) {
    companion object {
        fun from(payRequest: PayRequest): PayCommand {
            return PayCommand(
                userId = payRequest.userId,
                reservationId = payRequest.reservationId
            )
        }
    }
}
