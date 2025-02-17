package kr.tareun.concert

import kr.tareun.concert.common.aop.annotaion.OutboxEvent
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.CountDownLatch

@Configuration
class KafkaTestConfiguration {
    @Component
    class TestKafkaConsumer {
        val latch = CountDownLatch(1)
        var objectPayload: KafkaMessage? = null

        @KafkaListener(
            topics = ["test-topic1"],
            groupId = "concert1",
            properties = [
                "${JsonDeserializer.USE_TYPE_INFO_HEADERS}:false",
                "${JsonDeserializer.VALUE_DEFAULT_TYPE}:kr.tareun.concert.KafkaTestConfiguration.KafkaMessage"
            ])
        fun consume1(message: KafkaMessage) {
            objectPayload = message
            latch.countDown()
        }
    }

    @Component
    class TestKafkaProducer {
        @OutboxEvent("test-topic1")
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