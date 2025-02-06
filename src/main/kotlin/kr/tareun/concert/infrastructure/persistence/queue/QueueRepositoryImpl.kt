package kr.tareun.concert.infrastructure.persistence.queue

import kr.tareun.concert.domain.queue.QueueRepository
import kr.tareun.concert.domain.queue.model.QueueToken
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
class QueueRepositoryImpl(
    private val queueRedisRepository: QueueRedisRepository
): QueueRepository {

    override fun addQueueToken(token: QueueToken): QueueToken {
        return queueRedisRepository.addQueueToken(token)
    }

    override fun removeActivatedQueueToken(uuid: UUID) {
        queueRedisRepository.removeActivatedQueueToken(uuid)
    }

    override fun retrieveQueueRemaining(token: QueueToken): Int? {
        return queueRedisRepository.retrieveQueueRemaining(token)
    }

    override fun retrieveQueueToken(uuid: UUID): QueueToken? {
        return queueRedisRepository.retrieveQueueToken(uuid)
    }

    override fun removeExpiredTokens(unixTime: Long): Int {
        return queueRedisRepository.removeExpiredTokens(unixTime)
    }

    override fun countActivatedToken(): Int {
        return queueRedisRepository.countActivatedToken()
    }

    override fun activateToken(count: Int, expireTime: LocalDateTime): Int {
        return queueRedisRepository.activateToken(count, expireTime)
    }

}
