package kr.tareun.concert.interfaces.concert.model

data class ConcertResponse (
    val concertId: Long,
    val concertName: String,
    val performer: String,
)