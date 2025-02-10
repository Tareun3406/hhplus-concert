package kr.tareun.concert.infrastructure.persistence.reservation.entity

import kr.tareun.concert.domain.concert.model.Concert

data class ConcertCacheEntity(
    val concertId: Long = 0,
    val concertName: String,
    val performer: String,
) {
    companion object {
        fun from(concert: Concert): ConcertCacheEntity {
            return ConcertCacheEntity(
                concertId = concert.concertId,
                concertName = concert.concertName,
                performer = concert.performer,
            )
        }
    }
    fun toConcert(): Concert {
        return Concert(
            concertId = concertId,
            concertName = concertName,
            performer = performer,
        )
    }
}