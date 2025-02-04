package kr.tareun.concert.infrastructure.persistence.queue

import kr.tareun.concert.domain.queue.QueueRepository
import kr.tareun.concert.domain.queue.model.QueueToken
import kr.tareun.concert.common.enums.TokenStatusType
import kr.tareun.concert.infrastructure.persistence.queue.model.QueueTokenEntity
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
class QueueRepositoryImpl(
    private val queueTokenJpaRepository: QueueTokenJpaRepository
): QueueRepository {
    override fun saveQueueToken(queue: QueueToken): QueueToken {
        return queueTokenJpaRepository.save(QueueTokenEntity.from(queue)).toQueueToken()
    }

    override fun saveAllQueueTokens(queues: List<QueueToken>): List<QueueToken> {
        return queueTokenJpaRepository.saveAll(queues.map { QueueTokenEntity.from(it) }).map { it.toQueueToken() }
    }

    override fun getQueueByUuid(uuid: UUID): QueueToken {
        return queueTokenJpaRepository.getByTokenUuid(uuid).toQueueToken()
    }

    override fun countQueueByIdLessThanAndStatus(id: Long, status: TokenStatusType): Long {
        return queueTokenJpaRepository.countQueueByIdLessThanAndStatus(id, status)
    }

    override fun getAllByStatusAndExpiredTimeLessThan(status: TokenStatusType, time: LocalDateTime): List<QueueToken> {
        return queueTokenJpaRepository.getAllByStatusAndExpiredTimeLessThan(status, time).map { it.toQueueToken() }
    }

    override fun countByStatus(status: TokenStatusType): Long {
        return queueTokenJpaRepository.countByStatus(status)
    }

    override fun getAllByStatusOrderByIdAscWithLimit(status: TokenStatusType, limit: Int): List<QueueToken> {
        return queueTokenJpaRepository.getAllByStatusOrderByIdAsc(status, PageRequest.of(0, limit)).map { it.toQueueToken() }
    }

}
