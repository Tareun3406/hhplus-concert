package kr.tareun.concert.infrastructure.persistence.payment

import kr.tareun.concert.domain.payment.PaymentRepository
import kr.tareun.concert.domain.payment.model.Point
import org.springframework.stereotype.Repository

@Repository
class PaymentRepositoryImpl: PaymentRepository {
    override fun getPointByUserId(userId: Long): Point {
        TODO("Not yet implemented")
    }
}