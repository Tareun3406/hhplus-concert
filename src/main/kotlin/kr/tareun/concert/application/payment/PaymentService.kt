package kr.tareun.concert.application.payment

import kr.tareun.concert.application.payment.model.*
import kr.tareun.concert.common.aop.annotaion.OutboxEvent
import kr.tareun.concert.common.enums.BrokerType
import kr.tareun.concert.domain.payment.PaymentRepository
import kr.tareun.concert.domain.payment.model.PaymentHistory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class PaymentService(
    private val paymentRepository: PaymentRepository,
) {
    fun retrievePoint(userId: Long): PointResult {
        return PointResult.from(paymentRepository.getPointByUserId(userId))
    }

    @Transactional
    fun chargePoint(chargeCommand: ChargeCommand): PointResult {
        val point = paymentRepository.getPointByUserIdForUpdate(chargeCommand.userId)
        point.chargePoint(chargeCommand.amount)
        return PointResult.from(paymentRepository.savePoint(point))
    }

    @OutboxEvent("payment.pay.success", BrokerType.KAFKA)
    @Transactional
    fun payPoint(payCommand: PayCommand): PaySuccessEvent {
        val point = paymentRepository.getPointByUserIdForUpdate(payCommand.userId)

        point.payPoint(payCommand.amount)

        val paymentHistory = PaymentHistory(
            userId = payCommand.userId,
            orderType = payCommand.orderType,
            orderId = payCommand.orderId,
            paidPoint = payCommand.amount
        )
        paymentRepository.savePoint(point)

        return PaySuccessEvent.from(paymentHistory)
    }
}