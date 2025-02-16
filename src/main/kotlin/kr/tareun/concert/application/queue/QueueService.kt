package kr.tareun.concert.application.queue

import kr.tareun.concert.application.queue.model.QueueTokenResult
import kr.tareun.concert.common.exception.CommonException
import kr.tareun.concert.common.enums.ErrorCode
import kr.tareun.concert.domain.queue.QueueRepository
import kr.tareun.concert.domain.queue.model.QueueToken
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class QueueService(
    private val queueRepository: QueueRepository
){
    fun createQueueToken(userId: Long): QueueTokenResult {
        val newToken = QueueToken()
        val resultToken = queueRepository.addQueueToken(newToken)
        return QueueTokenResult.from(resultToken)
    }
    fun getQueueToken(uuid: UUID): QueueTokenResult {
        val token = queueRepository.retrieveQueueToken(uuid) ?: throw CommonException(ErrorCode.QUEUE_TOKEN_EXPIRED)
        return QueueTokenResult.from(token)
    }

    fun expireQueue(uuid: UUID) {
        queueRepository.removeActivatedQueueToken(uuid)
    }
    fun expireQueue(userId: Long){
        // todo userId 로 토큰 조회
    }
}