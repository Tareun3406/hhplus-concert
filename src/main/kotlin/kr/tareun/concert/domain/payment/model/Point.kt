package kr.tareun.concert.domain.payment.model

data class Point(
    var pointId: Long = 0,
    var userId: Long,
    var point: Int,
) {
    fun chargePoint(amount: Int) {
        point += amount
    }
    fun payPoint(amount: Int) {
        if (point < amount) {
            throw RuntimeException("잔액 부족")
        }
        point -= amount
    }
}