package kr.tareun.concert.application.queue

import kr.tareun.concert.application.queue.model.QueueTokenResult
import kr.tareun.concert.domain.queue.QueueRepository
import kr.tareun.concert.domain.queue.model.QueueToken
import kr.tareun.concert.domain.queue.model.TokenStatusType
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class QueueService(
    private val queueRepository: QueueRepository
){
    fun createQueueToken(userId: Long): QueueTokenResult {
        val newToken = QueueToken(userId = userId)
        val newTokenId = queueRepository.saveQueueToken(newToken).tokenId
        val remainingQueue =  queueRepository.countQueueByIdLessThanAndStatus(newTokenId, TokenStatusType.PENDING)
        return QueueTokenResult.from(newToken, remainingQueue)
    }
    fun getQueueToken(uuid: UUID): QueueTokenResult {
        val token = queueRepository.getQueueByUuid(uuid)
        return when (token.status) {
            TokenStatusType.PENDING -> QueueTokenResult.from(token,  queueRepository.countQueueByIdLessThanAndStatus(token.tokenId, TokenStatusType.PENDING))
            TokenStatusType.ACTIVATED -> QueueTokenResult.from(token, 0)
            TokenStatusType.EXPIRED -> throw RuntimeException("만료된 토큰입니다.")
        }
    }
}