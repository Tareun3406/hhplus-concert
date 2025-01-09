package kr.tareun.concert.domain.payment.model

data class Point(
    var pointId: Long = 0,
    var userId: Long,
    var point: Int,
)