package kr.tareun.concert.infrastructure.messaging

import kr.tareun.concert.domain.message.MessagePublisher
import kr.tareun.concert.domain.message.model.OutboxMessage
import org.springframework.stereotype.Component

@Component
class MessagePublisherImpl(
    private val kafkaProducer: KafkaProducer,
    private val mockMessageApi: MockMessageApi
): MessagePublisher {
    override fun publishMock(message: OutboxMessage<String>) {
        mockMessageApi.send(message.topic, message.message)
    }

    override fun publishKafka(message: OutboxMessage<String>) {
        kafkaProducer.sendToString(message.topic, message.message)
    }
}