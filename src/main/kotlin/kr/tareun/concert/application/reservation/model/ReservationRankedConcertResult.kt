package kr.tareun.concert.application.reservation.model

import kr.tareun.concert.domain.concert.model.Concert

data class ReservationRankedConcertResult(
    val concertId: Long,
    val concertName: String,
    val performer: String,
) {
    companion object {
        fun from(concert: Concert): ReservationRankedConcertResult {
            return ReservationRankedConcertResult(
                concertId = concert.concertId,
                concertName = concert.concertName,
                performer = concert.performer
            )
        }
    }
}