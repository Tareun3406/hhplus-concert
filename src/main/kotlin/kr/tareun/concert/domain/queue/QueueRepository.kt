package kr.tareun.concert.domain.queue

import kr.tareun.concert.domain.queue.model.QueueToken
import java.time.LocalDateTime
import java.util.*

interface QueueRepository {
    fun addQueueToken(token: QueueToken): QueueToken
    fun removeActivatedQueueToken(uuid: UUID)
    fun retrieveQueueRemaining(token: QueueToken): Int?
    fun retrieveQueueToken(uuid: UUID): QueueToken?

    fun removeExpiredTokens(unixTime: Long = System.currentTimeMillis()): Int
    fun countActivatedToken(): Int
    fun activateToken(count: Int, expireTime: LocalDateTime): Int
}