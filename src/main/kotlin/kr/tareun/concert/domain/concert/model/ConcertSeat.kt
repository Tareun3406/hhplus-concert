package kr.tareun.concert.domain.concert.model

data class ConcertSeat(
    val seatId : Long = 0,
    val locationId : Long,
    val seatNumber : Int,
)
