package kr.tareun.concert.interfaces.concert.model

import kr.tareun.concert.application.concert.model.ConcertResult

data class ConcertResponse (
    val concertId: Long,
    val concertName: String,
    val performer: String,
)  {
    companion object {
        fun from(concertResult: ConcertResult): ConcertResponse {
            return ConcertResponse(
                concertId = concertResult.concertId,
                concertName = concertResult.concertName,
                performer = concertResult.performer,
            )
        }
    }
}