package kr.tareun.concert.domain.queue

import kr.tareun.concert.domain.queue.model.QueueToken
import kr.tareun.concert.domain.queue.model.TokenStatusType
import java.time.LocalDateTime
import java.util.*

interface QueueRepository {
    fun saveQueueToken(queue: QueueToken): QueueToken
    fun saveAllQueueTokens(queues: List<QueueToken>): List<QueueToken>
    fun getQueueByUuid(uuid: UUID): QueueToken
    fun countQueueByIdLessThanAndStatus(id: Long, status: TokenStatusType): Long

    fun getAllByStatusAndExpiredTimeLessThan(status: TokenStatusType, time: LocalDateTime): List<QueueToken>
    fun countByStatus(status: TokenStatusType): Int
    fun getAllByStatusOrderByIdAscWithLimit(status: TokenStatusType, limit: Int): List<QueueToken>
}