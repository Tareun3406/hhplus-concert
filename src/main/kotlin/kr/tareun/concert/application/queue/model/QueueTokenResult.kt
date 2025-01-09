package kr.tareun.concert.application.queue.model

import kr.tareun.concert.domain.queue.model.QueueToken
import kr.tareun.concert.domain.queue.model.TokenStatusType
import java.util.UUID

data class QueueTokenResult(
    val userId: Long,
    val uuid: UUID,
    val status: TokenStatusType,
    val remainingQueue: Long
) {
    companion object {
        fun from(queueToken: QueueToken, remainingQueue: Long): QueueTokenResult {
            return QueueTokenResult(
                userId = queueToken.userId,
                uuid = queueToken.uuid,
                status = queueToken.status,
                remainingQueue = remainingQueue
            )
        }
    }
}
