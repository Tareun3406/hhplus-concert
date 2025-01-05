package kr.tareun.concert.domain.queue.model

import java.util.UUID

data class QueueTokenInfo(
    val uuid: UUID,
    val userId: Long,
    val remainingQueue: Long
)