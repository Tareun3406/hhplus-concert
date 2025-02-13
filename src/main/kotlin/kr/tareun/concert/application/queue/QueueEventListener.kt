package kr.tareun.concert.application.queue

import kr.tareun.concert.application.queue.model.QueueOrderExpireEvent
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class QueueEventListener(
    private val queueService: QueueService,
) {

    @Async
    @EventListener
    fun handleExpireEvent(queueOrderExpireEvent: QueueOrderExpireEvent) {
        queueService.expireQueue(queueOrderExpireEvent.userId)
    }
}