package kr.tareun.concert.domain.queue.model

import kr.tareun.concert.common.enums.TokenStatusType
import java.time.LocalDateTime
import java.util.*

data class QueueToken(
    val tokenId: Long = 0,
    val uuid: UUID = UUID.randomUUID(),
    val remainingQueue: Int = 0,
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
