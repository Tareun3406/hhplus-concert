package kr.tareun.concert.domain.payment

import kr.tareun.concert.domain.payment.model.Point

interface PaymentRepository {
    fun getPointByUserId(userId: Long): Point
}