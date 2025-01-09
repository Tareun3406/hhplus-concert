package kr.tareun.concert.domain.concert.model

data class Concert(
    var concertId: Long = 0,
    var concertName: String,
    var performer: String,
)
