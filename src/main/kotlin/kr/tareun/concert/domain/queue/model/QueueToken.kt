package kr.tareun.concert.domain.queue.model

import java.time.LocalDateTime
import java.util.*

data class QueueToken(
    var tokenId: Long = 0,
    var userId: Long,
    var uuid: UUID = UUID.randomUUID(),
    var status: TokenStatusType = TokenStatusType.PENDING,
    var expiredTime: LocalDateTime? = null,
) {
    fun markedAsExpired() {
        status = TokenStatusType.EXPIRED
    }
    fun markedAsActivated() {
        status = TokenStatusType.ACTIVATED
    }
    fun updateExpiredTime(time: LocalDateTime) {
        expiredTime = time
    }
}
