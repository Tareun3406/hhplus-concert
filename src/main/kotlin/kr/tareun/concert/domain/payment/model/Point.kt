package kr.tareun.concert.domain.payment.model

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
            throw RuntimeException("잔액 부족")
        }
        _point -= amount
    }
}