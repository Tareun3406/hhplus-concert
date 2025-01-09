package kr.tareun.concert.interfaces.queue.model

import kr.tareun.concert.application.queue.model.QueueTokenResult
import java.util.*

data class QueueResponse(
    val uuid: UUID,
    val userId: Long,
    val remainingQueue: Long
) {
    companion object {
        fun from(queueTokenResult: QueueTokenResult): QueueResponse {
            return QueueResponse(
                uuid = queueTokenResult.uuid,
                userId = queueTokenResult.userId,
                remainingQueue = queueTokenResult.remainingQueue
            )
        }
    }
}