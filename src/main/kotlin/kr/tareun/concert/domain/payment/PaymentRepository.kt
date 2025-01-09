package kr.tareun.concert.domain.payment

import kr.tareun.concert.domain.payment.model.PaymentHistory
import kr.tareun.concert.domain.payment.model.Point

interface PaymentRepository {
    fun savePoint(point: Point): Point
    fun getPointByUserId(userId: Long): Point
    fun getPointByUserIdForUpdate(userId: Long): Point

    fun savePaymentHistory(paymentHistory: PaymentHistory): PaymentHistory
}