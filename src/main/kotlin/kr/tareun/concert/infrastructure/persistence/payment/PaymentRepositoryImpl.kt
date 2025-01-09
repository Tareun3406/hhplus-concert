package kr.tareun.concert.infrastructure.persistence.payment

import kr.tareun.concert.domain.payment.PaymentRepository
import kr.tareun.concert.domain.payment.model.PaymentHistory
import kr.tareun.concert.domain.payment.model.Point
import org.springframework.stereotype.Repository

@Repository
class PaymentRepositoryImpl: PaymentRepository {
    override fun savePoint(pointInfo: Point): Point {
        TODO("Not yet implemented")
    }

    override fun getPointByUserId(userId: Long): Point {
        TODO("Not yet implemented")
    }

    override fun getPointByUserIdForUpdate(userId: Long): Point {
        TODO("Not yet implemented")
    }

    override fun savePaymentHistory(paymentHistory: PaymentHistory): PaymentHistory {
        TODO("Not yet implemented")
    }
}