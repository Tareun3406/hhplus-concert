package kr.tareun.concert.application.payment

import kr.tareun.concert.application.payment.model.OrderPayEvent
import kr.tareun.concert.application.payment.model.PayCommand
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class PaymentEventListener(
    private val paymentService: PaymentService,
) {

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun handlePaymentEvent(orderPayEvent: OrderPayEvent) {
        paymentService.payPoint(PayCommand.from(orderPayEvent))
    }
}