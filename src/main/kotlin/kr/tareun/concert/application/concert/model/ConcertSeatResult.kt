package kr.tareun.concert.application.concert.model

import kr.tareun.concert.domain.concert.model.ConcertSeat
import kr.tareun.concert.domain.reservation.model.ReservationItem

data class ConcertSeatResult(
    val seatId: Long,
    val locationId: Long,
    val seatNumber: Int,
    var canReserve: Boolean
) {
    companion object {
        fun from(concertSeat: ConcertSeat, reservationItem: ReservationItem?): ConcertSeatResult {
            return ConcertSeatResult(
                seatId = concertSeat.seatId,
                locationId = concertSeat.locationId,
                seatNumber = concertSeat.seatNumber,
                canReserve = reservationItem == null
            )
        }
    }
}
