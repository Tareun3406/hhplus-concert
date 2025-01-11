package kr.tareun.concert.application.concert.model

import kr.tareun.concert.domain.concert.model.Concert

data class ConcertResult(
    val concertId: Long,
    val concertName: String,
    val performer: String,
) {
    companion object {
        fun from(concert: Concert): ConcertResult {
            return ConcertResult(
                concertId = concert.concertId,
                concertName = concert.concertName,
                performer = concert.performer
            )
        }
    }
}
