package kr.tareun.concert

import kr.tareun.concert.common.aop.annotaion.OutboxEvent
import kr.tareun.concert.common.enums.BrokerType
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.CountDownLatch

@Configuration
class KafkaTestConfiguration {
    @Component
    class TestKafkaConsumer {
        val latch = CountDownLatch(1)
        var objectPayload: KafkaMessage? = null

        @KafkaListener(topics = ["test-topic1"], groupId = "concert1")
        fun consume1(message: KafkaMessage) {
            objectPayload = message
            latch.countDown()
        }
    }

    @Component
    class TestKafkaProducer {
        @OutboxEvent("test-topic1", BrokerType.KAFKA)
        @Transactional
        fun produce1(message: KafkaMessage): KafkaMessage {
            return message
        }
    }

    class KafkaMessage(
        val value1: String,
        val value2: String,
    )
}