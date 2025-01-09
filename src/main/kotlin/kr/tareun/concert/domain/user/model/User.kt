package kr.tareun.concert.domain.user.model

data class User(
    var userId: Long = 0,
    var email: String = "",
    var name: String = "",
)