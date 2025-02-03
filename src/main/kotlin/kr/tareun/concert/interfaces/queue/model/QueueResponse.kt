package kr.tareun.concert.interfaces.queue.model

import kr.tareun.concert.application.queue.model.QueueTokenResult
import java.util.*

data class QueueResponse(
    val uuid: UUID,
    val remainingQueue: Int
) {
    companion object {
        fun from(queueTokenResult: QueueTokenResult): QueueResponse {
            return QueueResponse(
                uuid = queueTokenResult.uuid,
                remainingQueue = queueTokenResult.remainingQueue
            )
        }
    }
}