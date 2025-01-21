package kr.tareun.concert.application.payment.model

import kr.tareun.concert.interfaces.payment.model.PayRequest
import java.util.UUID

data class PayCommand(
    val userId: Long,
    val reservationId: Long,
    val tokenUuid: UUID
) {
    companion object {
        fun from(payRequest: PayRequest, tokenUuid: UUID): PayCommand {
            return PayCommand(
                userId = payRequest.userId,
                reservationId = payRequest.reservationId,
                tokenUuid = tokenUuid
            )
        }
    }
}
