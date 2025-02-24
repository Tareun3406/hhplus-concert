package kr.tareun.concert.infrastructure.persistence.message

import kr.tareun.concert.common.config.properties.AppProperties
import kr.tareun.concert.common.enums.MessageStatus
import com.fasterxml.jackson.databind.ObjectMapper
import kr.tareun.concert.domain.message.OutboxMessageRepository
import kr.tareun.concert.domain.message.model.OutboxMessage
import kr.tareun.concert.infrastructure.persistence.message.entity.OutboxMessageEntity
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class OutboxMessageRepositoryImpl(
    private val outboxMessageJpaRepository: OutboxMessageJpaRepository,
    private val appProperties: AppProperties,
    private val objectMapper: ObjectMapper,
): OutboxMessageRepository {
    override fun saveOutboxMessage(outboxMessage: OutboxMessage<*>): OutboxMessage<String> {
        val jsonMessage = objectMapper.writeValueAsString(outboxMessage.message) // JSON 문자열로 변환하여 DB 저장.
        val outboxMessageEntity = OutboxMessageEntity(
            id = outboxMessage.id,
            topic = outboxMessage.topic,
            message = jsonMessage,
            brokerType = outboxMessage.brokerType,
            status = outboxMessage.status,
            createdAt = outboxMessage.createdAt,
        )
        return outboxMessageJpaRepository.save(outboxMessageEntity).toOutboxMessage()
    }

    override fun saveOutboxMessageForString(outboxMessage: OutboxMessage<String>): OutboxMessage<String> {
        return outboxMessageJpaRepository.save(OutboxMessageEntity.fromStringOutboxMessage(outboxMessage)).toOutboxMessage()
    }

    override fun getOutboxMessageById(id: Long): OutboxMessage<String> {
        return outboxMessageJpaRepository.getReferenceById(id).toOutboxMessage()
    }

    override fun retrieveOutboxListToNeedRetry(): List<OutboxMessage<String>> {
        val beforeTime = LocalDateTime.now().minusMinutes(appProperties.messageRetryDelayMinute)
        return outboxMessageJpaRepository.findAllByStatusAndCreatedAtBefore(MessageStatus.PENDING, beforeTime)
            .map { it.toOutboxMessage() }
    }
}