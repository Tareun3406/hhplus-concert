package kr.tareun.concert.infrastructure.messaging

import kr.tareun.concert.domain.message.MessagePublisher
import kr.tareun.concert.domain.message.model.OutboxMessage
import org.springframework.stereotype.Component

@Component
class MessagePublisherImpl(
    private val kafkaProducer: KafkaProducer
): MessagePublisher {
    override fun publish(message: OutboxMessage<String>) {
        kafkaProducer.sendToString(message.topic, message.message)
    }
}