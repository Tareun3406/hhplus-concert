package kr.tareun.concert.interfaces.concert.model

import kr.tareun.concert.application.concert.model.ConcertSeatResult

data class ConcertSeatResponse(
    val seatId: Long,
    val locationId: Long,
    val seatNumber: Int,
    val canReserve: Boolean
) {
    companion object {
        fun from(concertSeatResult: ConcertSeatResult): ConcertSeatResponse {
            return ConcertSeatResponse(
                seatId = concertSeatResult.seatId,
                locationId = concertSeatResult.locationId,
                seatNumber = concertSeatResult.seatNumber,
                canReserve = concertSeatResult.canReserve
            )
        }
    }
}
