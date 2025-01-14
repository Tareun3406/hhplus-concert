package kr.tareun.concert.domain.user.model

data class User(
    val userId: Long = 0,
    val email: String = "",
    val name: String = "",
)