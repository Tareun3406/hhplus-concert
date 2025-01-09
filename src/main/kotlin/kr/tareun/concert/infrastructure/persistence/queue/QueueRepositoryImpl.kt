package kr.tareun.concert.infrastructure.persistence.queue

import kr.tareun.concert.domain.queue.QueueRepository
import kr.tareun.concert.domain.queue.model.QueueToken
import kr.tareun.concert.domain.queue.model.TokenStatusType
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
class QueueRepositoryImpl: QueueRepository {
    override fun saveQueueToken(queue: QueueToken): QueueToken {
        TODO("Not yet implemented")
    }

    override fun saveAllQueueTokens(queues: List<QueueToken>): List<QueueToken> {
        TODO("Not yet implemented")
    }

    override fun getQueueByUuid(uuid: UUID): QueueToken {
        TODO("Not yet implemented")
    }

    override fun countQueueByIdLessThanAndStatus(id: Long, status: TokenStatusType): Long {
        TODO("Not yet implemented")
    }

    override fun getAllByStatusAndExpiredTimeLessThan(status: TokenStatusType, time: LocalDateTime): List<QueueToken> {
        TODO("Not yet implemented")
    }

    override fun countByStatus(status: TokenStatusType): Int {
        TODO("Not yet implemented")
    }

    override fun getAllByStatusOrderByIdAscWithLimit(status: TokenStatusType, limit: Int): List<QueueToken> {
        TODO("Not yet implemented")
    }

}
