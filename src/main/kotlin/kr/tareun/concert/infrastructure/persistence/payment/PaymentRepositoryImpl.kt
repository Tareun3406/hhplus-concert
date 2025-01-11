package kr.tareun.concert.infrastructure.persistence.payment

import kr.tareun.concert.domain.payment.PaymentRepository
import kr.tareun.concert.domain.payment.model.PaymentHistory
import kr.tareun.concert.domain.payment.model.Point
import kr.tareun.concert.infrastructure.persistence.payment.model.PaymentHistoryEntity
import kr.tareun.concert.infrastructure.persistence.payment.model.PointEntity
import org.springframework.stereotype.Repository

@Repository
class PaymentRepositoryImpl(
    private val pointJpaRepository: PointJpaRepository,
    private val paymentHistoryJpaRepository: PaymentHistoryJpaRepository
): PaymentRepository {
    override fun savePoint(point: Point): Point {
        return pointJpaRepository.save(PointEntity.from(point)).toPoint()
    }

    override fun getPointByUserId(userId: Long): Point {
        return pointJpaRepository.getReferenceById(userId).toPoint()
    }

    override fun getPointByUserIdForUpdate(userId: Long): Point {
        return pointJpaRepository.getForUpdateByUserId(userId).toPoint()
    }

    override fun savePaymentHistory(paymentHistory: PaymentHistory): PaymentHistory {
        return paymentHistoryJpaRepository.save(PaymentHistoryEntity.fromPaymentHistory(paymentHistory)).toPaymentHistory()
    }
}