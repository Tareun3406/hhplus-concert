package kr.tareun.concert.infrastructure.persistence.queue

import kr.tareun.concert.common.enum.RedisKeyType
import kr.tareun.concert.domain.queue.QueueRepository
import kr.tareun.concert.domain.queue.model.QueueToken
import kr.tareun.concert.domain.queue.model.TokenStatusType
import org.redisson.api.RedissonClient
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Repository
class QueueRepositoryImpl(
    private val redissonClient: RedissonClient
): QueueRepository {

    override fun addQueueToken(token: QueueToken): QueueToken {
        val pendingTokens = redissonClient.getScoredSortedSet<String>(RedisKeyType.QUEUE_TOKEN_PENDING.key)
        val remainingQueue = pendingTokens.addAndGetRank(System.currentTimeMillis().toDouble(), token.uuid.toString())
        return QueueToken(uuid = token.uuid, remainingQueue = remainingQueue)
    }

    override fun removeActivatedQueueToken(uuid: UUID) {
        val activatedTokens = redissonClient.getScoredSortedSet<String>(RedisKeyType.QUEUE_TOKEN_ACTIVATED.key)
        activatedTokens.remove(uuid.toString())
    }

    override fun retrieveQueueRemaining(token: QueueToken): Int? {
        val pendingTokens = redissonClient.getScoredSortedSet<String>(RedisKeyType.QUEUE_TOKEN_PENDING.key)
        val activatedTokens = redissonClient.getScoredSortedSet<String>(RedisKeyType.QUEUE_TOKEN_ACTIVATED.key)
        return pendingTokens.rank(token.uuid.toString()) ?: activatedTokens.rank(token.uuid.toString())
    }

    override fun retrieveQueueToken(uuid: UUID): QueueToken? {
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

    override fun removeExpiredTokens(unixTime: Long): Int {
        val activatedTokens = redissonClient.getScoredSortedSet<String>(RedisKeyType.QUEUE_TOKEN_ACTIVATED.key)
        return activatedTokens.removeRangeByScore(0.0, true, unixTime.toDouble(), true)
    }

    override fun countActivatedToken(): Int {
        val activatedTokens = redissonClient.getScoredSortedSet<String>(RedisKeyType.QUEUE_TOKEN_ACTIVATED.key)
        return activatedTokens.size()
    }

    override fun activateToken(count: Int, expiredTime: LocalDateTime): Int {
        val pendingTokens = redissonClient.getScoredSortedSet<String>(RedisKeyType.QUEUE_TOKEN_PENDING.key)
        val activatedTokens = redissonClient.getScoredSortedSet<String>(RedisKeyType.QUEUE_TOKEN_ACTIVATED.key)

        val activatingTokens = pendingTokens.pollFirst(count)
        activatingTokens.forEach { activatedTokens.add(expiredTime.atZone(ZoneId.systemDefault()).toEpochSecond().toDouble(), it)}

        return activatingTokens.size
    }

}
