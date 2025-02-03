package kr.tareun.concert.domain.queue.model

import java.time.LocalDateTime
import java.util.*

data class QueueToken(
    val tokenId: Long = 0,
    val uuid: UUID = UUID.randomUUID(),
    private var _status: TokenStatusType = TokenStatusType.PENDING,
    private var _expiredTime: LocalDateTime? = null,
) {
    val status: TokenStatusType
        get() = _status

    val expiredTime: LocalDateTime?
        get() = _expiredTime

    fun markedAsExpired() {
        _status = TokenStatusType.EXPIRED
    }
    fun markedAsActivated() {
        _status = TokenStatusType.ACTIVATED
    }
    fun updateExpiredTime(time: LocalDateTime) {
        _expiredTime = time
    }
}
