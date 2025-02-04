package kr.tareun.concert.infrastructure.persistence.reservation

import kr.tareun.concert.common.config.ReservationProperties
import kr.tareun.concert.common.enums.RedisKeyType
import kr.tareun.concert.common.util.DateCalculator
import kr.tareun.concert.domain.concert.model.Concert
import kr.tareun.concert.infrastructure.persistence.reservation.entity.ConcertCacheEntity
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Repository
import java.time.Duration
import java.time.LocalDateTime

@Repository
class ReservationRedisCacheRepository(
    private val redissonClient: RedissonClient,
    reservationProperties: ReservationProperties
) {
    val collectCycleMinute = reservationProperties.reservationRankingCacheCycleMinute

    fun increaseReservationCountToConcert(concertCacheEntity: ConcertCacheEntity, referenceTime: LocalDateTime) {
        val variable = DateCalculator.truncatedDateTime(referenceTime, collectCycleMinute).toString()
        val rankedConcert = redissonClient.getScoredSortedSet<ConcertCacheEntity>(RedisKeyType.RESERVATION_CONCERT_RANKING.getKeyWithVariable(variable))
        rankedConcert.addScore(concertCacheEntity, 1)
        rankedConcert.expire(Duration.ofMinutes(60)) // 캐시 유효시간 60분 (첫 적용 후 갱신되지 않음)
    }

    fun getRankedConcertList(rankingSize: Int, referenceTime: LocalDateTime): List<ConcertCacheEntity> {
        val variable = DateCalculator.truncatedDateTime(referenceTime, collectCycleMinute).toString() // 캐시 수집 완료된 데이터로 리스트 조회
        val rankedConcert = redissonClient.getScoredSortedSet<ConcertCacheEntity>(RedisKeyType.RESERVATION_CONCERT_RANKING.getKeyWithVariable(variable))
        return rankedConcert.valueRangeReversed(0, rankingSize).toList()
    }
}