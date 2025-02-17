package kr.tareun.concert.domain.message

import kr.tareun.concert.common.enums.MessageStatus
import kr.tareun.concert.domain.message.model.OutboxMessage
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OutboxMessageDomainService(
    private val outboxMessageRepository: OutboxMessageRepository,
    private val messagePublisher: MessagePublisher,
) {
    private val logger = LoggerFactory.getLogger(OutboxMessageDomainService::class.java)
    @Transactional
    fun createAndSaveOutboxMessage(topic: String, message: Any): OutboxMessage<String> {
        val outboxMessage = OutboxMessage(
            topic = topic,
            message = message,
            _status = MessageStatus.PENDING,
        )
        return outboxMessageRepository.saveOutboxMessage(outboxMessage)
    }

    @Transactional
    fun publishMessageAndUpdateStatus(outboxMessage: OutboxMessage<String>): OutboxMessage<String> {
        messagePublisher.publish(outboxMessage)
        try{
            outboxMessage.setStatus(MessageStatus.SEND)
        } catch (e: Exception) {
            logger.error("메세지 전송에는 성공하였으나 아웃박스 상태 변경에 실패햐였습니다.", e)
        }
        return outboxMessageRepository.saveOutboxMessage(outboxMessage)
    }
}