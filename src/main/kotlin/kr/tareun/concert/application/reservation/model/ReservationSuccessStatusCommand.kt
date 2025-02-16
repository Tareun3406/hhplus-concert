package kr.tareun.concert.application.reservation.model

import kr.tareun.concert.application.payment.model.PaySuccessEvent

data class ReservationSuccessStatusCommand(
    val reservationId: Long,
    val userId: Long
) {
    companion object {
        fun from(paySuccessEvent: PaySuccessEvent): ReservationSuccessStatusCommand {
            return ReservationSuccessStatusCommand(
                reservationId = paySuccessEvent.orderId,
                userId = paySuccessEvent.userId
            )
        }
    }
}
