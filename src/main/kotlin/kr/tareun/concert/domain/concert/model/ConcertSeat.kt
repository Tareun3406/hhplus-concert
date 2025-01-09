package kr.tareun.concert.domain.concert.model

data class ConcertSeat(
    var seatId : Long = 0,
    var locationId : Long,
    var seatNumber : Int,
)
