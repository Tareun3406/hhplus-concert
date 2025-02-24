package kr.tareun.concert.domain.message.model

import kr.tareun.concert.common.enums.BrokerType
import kr.tareun.concert.common.enums.ErrorCode
import kr.tareun.concert.common.enums.MessageStatus
import kr.tareun.concert.common.exception.CommonException
import java.time.LocalDateTime

data class OutboxMessage<T: Any>(
    val id: Long = 0,
    val topic: String,
    val message: T,
    val brokerType: BrokerType,
    private var _status: MessageStatus,
    val createdAt: LocalDateTime = LocalDateTime.now(),
) {
    val status: MessageStatus
        get() = _status

    fun setStatus(messageStatus: MessageStatus) {
        if (this._status == messageStatus) {
            throw CommonException(ErrorCode.OUTBOX_STATUS_ALREADY_CHANGED)
        }
        this._status = messageStatus
    }
}