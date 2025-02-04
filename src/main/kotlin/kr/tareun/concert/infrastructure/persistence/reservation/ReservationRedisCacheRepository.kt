package kr.tareun.concert.infrastructure.persistence.reservation

import kr.tareun.concert.domain.concert.model.Concert
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class ReservationRedisCacheRepository(private val redissonClient: RedissonClient) {

    fun increaseReservationCountToConcert(concert: Concert) {
        val rankedConcert = redissonClient.getScoredSortedSet<Concert>("concert-rank${truncatedDateTime(LocalDateTime.now())}")
        rankedConcert.addScore(concert, 1)
    }

    fun getRankedConcertList(rankingSize: Int): List<Concert> {
        val rankedConcert = redissonClient.getScoredSortedSet<Concert>("concert-rank:${truncatedDateTime(LocalDateTime.now()).minusMinutes(10)}")
        return rankedConcert.valueRangeReversed(0, rankingSize).toList()
    }

    private fun truncatedDateTime(date: LocalDateTime): LocalDateTime {
        val truncatedMinutes = date.minute / 10 * 10
        return date.withMinute(truncatedMinutes).withSecond(0).withNano(0)
    }
}