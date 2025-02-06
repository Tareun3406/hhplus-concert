package kr.tareun.concert.infrastructure.persistence.queue

import kr.tareun.concert.common.enums.RedisKeyType
import kr.tareun.concert.common.enums.TokenStatusType
import kr.tareun.concert.domain.queue.model.QueueToken
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Repository
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Repository
class QueueRedisRepository(private val redissonClient: RedissonClient) {
    fun addQueueToken(token: QueueToken): QueueToken {
        val pendingTokens = redissonClient.getScoredSortedSet<String>(RedisKeyType.QUEUE_TOKEN_PENDING.key)
        val remainingQueue = pendingTokens.addAndGetRank(System.currentTimeMillis().toDouble(), token.uuid.toString())
        return QueueToken(uuid = token.uuid, remainingQueue = remainingQueue)
    }

    fun removeActivatedQueueToken(uuid: UUID) {
        val activatedTokens = redissonClient.getScoredSortedSet<String>(RedisKeyType.QUEUE_TOKEN_ACTIVATED.key)
        activatedTokens.remove(uuid.toString())
    }

    fun retrieveQueueRemaining(token: QueueToken): Int? {
        val pendingTokens = redissonClient.getScoredSortedSet<String>(RedisKeyType.QUEUE_TOKEN_PENDING.key)
        val activatedTokens = redissonClient.getScoredSortedSet<String>(RedisKeyType.QUEUE_TOKEN_ACTIVATED.key)
        return pendingTokens.rank(token.uuid.toString()) ?: activatedTokens.rank(token.uuid.toString())
    }

    fun retrieveQueueToken(uuid: UUID): QueueToken? {
        val pendingTokens = redissonClient.getScoredSortedSet<String>(RedisKeyType.QUEUE_TOKEN_PENDING.key)
        val activatedTokens = redissonClient.getScoredSortedSet<String>(RedisKeyType.QUEUE_TOKEN_ACTIVATED.key)

        pendingTokens.rank(uuid.toString())?.let {
            return QueueToken(
                uuid = uuid,
                remainingQueue = it
            )
        }

        activatedTokens.getScore(uuid.toString())?.let {
            return QueueToken(
                uuid = uuid,
                _status = TokenStatusType.ACTIVATED,
                _expiredTime = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(it.toLong()),
                    ZoneId.systemDefault()
                )
            )
        }

        return null
    }

    fun removeExpiredTokens(unixTime: Long): Int {
        val activatedTokens = redissonClient.getScoredSortedSet<String>(RedisKeyType.QUEUE_TOKEN_ACTIVATED.key)
        return activatedTokens.removeRangeByScore(0.0, true, unixTime.toDouble(), true)
    }

    fun countActivatedToken(): Int {
        val activatedTokens = redissonClient.getScoredSortedSet<String>(RedisKeyType.QUEUE_TOKEN_ACTIVATED.key)
        return activatedTokens.size()
    }

    fun activateToken(count: Int, expireTime: LocalDateTime): Int {
        val pendingTokens = redissonClient.getScoredSortedSet<String>(RedisKeyType.QUEUE_TOKEN_PENDING.key)
        val activatedTokens = redissonClient.getScoredSortedSet<String>(RedisKeyType.QUEUE_TOKEN_ACTIVATED.key)

        val activatingTokens = pendingTokens.pollFirst(count)
        activatingTokens.forEach { activatedTokens.add(expireTime.atZone(ZoneId.systemDefault()).toEpochSecond().toDouble(), it)}

        return activatingTokens.size
    }
}