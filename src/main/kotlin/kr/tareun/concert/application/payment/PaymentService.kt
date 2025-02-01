package kr.tareun.concert.application.payment

import kr.tareun.concert.application.payment.model.ChargeCommand
import kr.tareun.concert.application.payment.model.PointResult
import kr.tareun.concert.domain.payment.PaymentRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class PaymentService(
    val paymentRepository: PaymentRepository,
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
}