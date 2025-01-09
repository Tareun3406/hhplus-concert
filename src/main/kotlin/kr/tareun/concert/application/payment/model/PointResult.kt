package kr.tareun.concert.application.payment.model

import kr.tareun.concert.domain.payment.model.Point

data class PointResult(
    val userId: Long,
    val point: Int,
) {
    companion object {
        fun from(pointInfo: Point): PointResult {
            return PointResult(
                userId = pointInfo.userId,
                point = pointInfo.point,
            )
        }
    }
}
