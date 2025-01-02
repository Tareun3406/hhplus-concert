package kr.tareun.concert.domain.concert.model

data class ConcertSeatInfo(
    val id: Long,
    val venueId: Long,
    val seatNumber: Int,
    val canReserve: Boolean
)