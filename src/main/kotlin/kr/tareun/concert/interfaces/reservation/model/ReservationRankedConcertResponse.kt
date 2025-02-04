package kr.tareun.concert.interfaces.reservation.model

import kr.tareun.concert.application.reservation.model.ReservationRankedConcertResult

data class ReservationRankedConcertResponse(
    val concertId: Long,
    val concertName: String,
    val performer: String,
) {
    companion object {
        fun from(reservationRankedConcertResult: ReservationRankedConcertResult): ReservationRankedConcertResponse {
            return ReservationRankedConcertResponse(
                reservationRankedConcertResult.concertId,
                reservationRankedConcertResult.concertName,
                reservationRankedConcertResult.performer,
            )
        }
    }
}