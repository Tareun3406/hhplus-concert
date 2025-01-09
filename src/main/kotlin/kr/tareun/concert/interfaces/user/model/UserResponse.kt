package kr.tareun.concert.interfaces.user.model

import kr.tareun.concert.application.user.model.UserResult

data class UserResponse(
    val id: Long,
    val email: String,
    val name: String,
    val point: Int,
) {
    companion object {
        fun from(userResult: UserResult): UserResponse {
            return UserResponse(
                id = userResult.userId,
                email = userResult.email,
                name = userResult.userName,
                point = userResult.point,
            )
        }
    }
}
