package kr.tareun.concert.domain.message.model

import kr.tareun.concert.common.enums.MessageStatus
import java.time.LocalDateTime

data class OutboxMessage<T: Any>(
    var id: Long = 0,
    var topic: String,
    var message: T,
    private var _status: MessageStatus,
    var createdAt: LocalDateTime = LocalDateTime.now(),
) {
    val status: MessageStatus
        get() = _status

    fun setStatus(messageStatus: MessageStatus) {
        if (this._status == messageStatus) {
            throw IllegalArgumentException("이미 해당 상태로 설정되어 있습니다. : $messageStatus")
        }
        this._status = messageStatus
    }
}