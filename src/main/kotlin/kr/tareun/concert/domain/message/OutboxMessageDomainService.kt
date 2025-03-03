package kr.tareun.concert.domain.message

import kr.tareun.concert.common.enums.BrokerType
import kr.tareun.concert.common.enums.ErrorCode
import kr.tareun.concert.common.enums.MessageStatus
import kr.tareun.concert.common.exception.CommonException
import kr.tareun.concert.domain.message.model.OutboxMessage
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OutboxMessageDomainService(
    private val outboxMessageRepository: OutboxMessageRepository,
    private val messagePublisher: MessagePublisher,
) {
    @Transactional
    fun createAndSaveOutboxMessage(topic: String, message: Any, brokerType: BrokerType): OutboxMessage<String> {
        val outboxMessage = OutboxMessage(
            topic = topic,
            message = message,
            brokerType = brokerType,
            _status = MessageStatus.PENDING,
        )
        return outboxMessageRepository.saveOutboxMessage(outboxMessage)
    }

    @Transactional
    fun publishMessageAndUpdateStatus(outboxMessage: OutboxMessage<String>): OutboxMessage<String> {
        when(outboxMessage.brokerType) {
            BrokerType.MOCK -> messagePublisher.publishMock(outboxMessage)
            BrokerType.KAFKA -> messagePublisher.publishKafka(outboxMessage)
        }

        // 아웃박스 DB 상태 변경
        try {
            outboxMessage.setStatus(MessageStatus.SEND)
            return outboxMessageRepository.saveOutboxMessage(outboxMessage)
        } catch (e: Exception) {
            throw CommonException(ErrorCode.OUTBOX_MESSAGE_SENT_BUT_STATUS_SAVE_FAILED, e) // 메세지 전송 완료 상태 저장 실패.
        }
    }
}