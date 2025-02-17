package kr.tareun.concert.application.message

import kr.tareun.concert.domain.message.OutboxMessageDomainService
import kr.tareun.concert.domain.message.OutboxMessageRepository
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class MessageScheduler(
    private val messageDomainService: OutboxMessageDomainService,
    private val messageRepository: OutboxMessageRepository
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Scheduled(fixedDelay = 5000)
    fun retryOutboxMessage() {
        messageRepository.retrieveOutboxListToNeedRetry().forEach {
            try {
                messageDomainService.publishMessageAndUpdateStatus(it)
            } catch (e: Exception) {
                logger.error("메세지 재전송 실패 - id:${it.id}", e)
            }
        }
    }
}