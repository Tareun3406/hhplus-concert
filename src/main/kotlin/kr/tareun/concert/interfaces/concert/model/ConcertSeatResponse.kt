package kr.tareun.concert.interfaces.concert.model

data class ConcertSeatResponse(
    val seatId: Long,
    val locationId: Long,
    val seatNumber: Int,
    val canReserve: Boolean
)
