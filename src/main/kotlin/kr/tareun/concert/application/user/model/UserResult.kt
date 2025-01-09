package kr.tareun.concert.application.user.model

import kr.tareun.concert.domain.payment.model.Point
import kr.tareun.concert.domain.user.model.User

data class UserResult(
    val userId: Long,
    val email: String,
    val userName: String,
    val point: Int,
) {
    companion object {
        fun from(user: User, point: Point): UserResult {
            return UserResult(
                userId = user.userId,
                email = user.email,
                userName = user.name,
                point = point.point,
            )
        }
    }
}