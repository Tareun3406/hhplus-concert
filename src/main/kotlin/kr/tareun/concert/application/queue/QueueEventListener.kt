package kr.tareun.concert.application.queue

import kr.tareun.concert.application.queue.model.QueueOrderExpireEvent
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class QueueEventListener(
    private val queueService: QueueService,
) {
    private val logger = LoggerFactory.getLogger(QueueEventListener::class.java)

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handleExpireEvent(queueOrderExpireEvent: QueueOrderExpireEvent) {
        try {
            queueService.expireQueue(queueOrderExpireEvent.userId)
        } catch (e: Exception) {
            logger.error("토큰 만료 처리 실패", e)
        }
    }
}