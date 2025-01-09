package kr.tareun.concert.interfaces.queue.model

import java.util.*

data class QueueResponse(
    val uuid: UUID,
    val userId: Long,
    val remainingQueue: Long
)