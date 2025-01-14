package kr.tareun.concert.domain.concert.model

data class Concert(
    val concertId: Long = 0,
    val concertName: String,
    val performer: String,
)
