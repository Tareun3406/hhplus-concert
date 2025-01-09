package kr.tareun.concert.interfaces.user.model

data class UserResponse(
    val id: Long,
    val email: String,
    val name: String,
    val point: Int,
)
