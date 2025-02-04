package kr.tareun.concert.domain.payment.model

import kr.tareun.concert.common.enums.ErrorCode
import kr.tareun.concert.common.exception.CommonException

data class Point(
    val pointId: Long = 0,
    val userId: Long,
    private var _point: Int,
) {
    val point: Int
        get() = _point

    fun chargePoint(amount: Int) {
        _point += amount
    }
    fun payPoint(amount: Int) {
        if (_point < amount) {
            throw CommonException(ErrorCode.PAYMENT_NOT_ENOUGH_POINT)
        }
        _point -= amount
    }
}