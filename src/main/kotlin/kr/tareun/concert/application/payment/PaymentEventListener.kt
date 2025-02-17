package kr.tareun.concert.application.payment

import kr.tareun.concert.application.payment.model.OrderPayEvent
import kr.tareun.concert.application.payment.model.PayCommand
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class PaymentEventListener(
    private val paymentService: PaymentService,
) {
    private val logger = LoggerFactory.getLogger(PaymentEventListener::class.java)

    @KafkaListener(topics = ["payment.pay.request"], groupId = "payment.pay.request.consumer")
    fun handlePaymentEvent(orderPayEvent: OrderPayEvent) {
        try {
            paymentService.payPoint(PayCommand.from(orderPayEvent))
        } catch (e: Exception) {
            logger.error("결제 처리 실패", e)
        }
    }
}