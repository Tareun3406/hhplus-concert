package kr.tareun.concert.infrastructure.persistence.message

import kr.tareun.concert.domain.message.OutboxMessageRepository
import kr.tareun.concert.domain.message.model.OutboxMessage
import kr.tareun.concert.infrastructure.persistence.message.entity.OutboxMessageEntity
import org.springframework.kafka.support.serializer.JsonSerializer
import org.springframework.stereotype.Repository

@Repository
class OutboxMessageRepositoryImpl(
    private val outboxMessageJpaRepository: OutboxMessageJpaRepository,
): OutboxMessageRepository {
    override fun saveOutboxMessage(outboxMessage: OutboxMessage<*>): OutboxMessage<String> {
        val serializer = JsonSerializer<Any>() // JSON 직렬화 하여 DB 저장. Kafka 의 Serializer 사용.
        val serialized: ByteArray = serializer.serialize(outboxMessage.topic, outboxMessage.message) // JSON 문자열이 들어올 경우 문자열 그대로 ByteArray 로 변환됨.
            ?: throw IllegalArgumentException("메세지 직렬화 실패")

        val outboxMessageEntity = OutboxMessageEntity(
            id = outboxMessage.id,
            topic = outboxMessage.topic,
            message = String(serialized),
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
}