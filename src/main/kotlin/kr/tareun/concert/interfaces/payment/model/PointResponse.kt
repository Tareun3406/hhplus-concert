package kr.tareun.concert.interfaces.payment.model

import kr.tareun.concert.application.payment.model.PointResult

data class PointResponse(
    val userId: Long,
    val point: Int
) {
    companion object {
        fun from(pointResult: PointResult): PointResponse {
            return PointResponse(
                userId = pointResult.userId,
                point = pointResult.point
            )
        }
    }
}