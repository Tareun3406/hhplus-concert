package kr.tareun.concert

import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import java.util.concurrent.CountDownLatch

@Configuration
class KafkaTestConfiguration {
    @Component
    class TestKafkaConsumer {
        val latch1 = CountDownLatch(1)
        val latch2 = CountDownLatch(1)
        var payload: String? = null
        var objectPayload: KafkaMessage? = null

        @KafkaListener(topics = ["test-topic"], groupId = "concert")
        fun consume(message: String) {
            payload = message
            latch1.countDown()
        }

        @KafkaListener(topics = ["test-topic2"], groupId = "concert")
        fun consume2(message: KafkaMessage) {
            objectPayload = message
            latch2.countDown()
        }
    }
    class KafkaMessage(
        val value1: String,
        val value2: String,
    )
}