package kr.tareun.concert.common

import kr.tareun.concert.KafkaTestConfiguration
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.core.KafkaTemplate
import java.util.concurrent.TimeUnit


@Suppress("NonAsciiCharacters")
@SpringBootTest
class KafkaTest {

    @Autowired
    lateinit var kafkaTemplate: KafkaTemplate<String, Any>

    @Autowired
    lateinit var testKafkaConsumer: KafkaTestConfiguration.TestKafkaConsumer

    @Test
    fun `카프카에 메세지를 전달 하고 읽을 수 있다`() {
        // given
        val topic = "test-topic"
        val message = "Hello, Kafka!"

        // when
        kafkaTemplate.send(topic, message) // 메세지 보내기
        val received = testKafkaConsumer.latch1.await(10, TimeUnit.SECONDS) // 메세지 받기

        // then
        assert(received) { "메시지를 수신하지 못했습니다." }
        Assertions.assertEquals(message, testKafkaConsumer.payload)
    }

    @Test
    fun `카프카에 객체를 직렬화하여 전달 하고 읽을 수 있다`() {
        // given
        val topic = "test-topic2"
        val message = KafkaTestConfiguration.KafkaMessage("1", "2")

        // when
        kafkaTemplate.send(topic, message) // 메세지 보내기
        val received = testKafkaConsumer.latch2.await(10, TimeUnit.SECONDS) // 메세지 받기

        // then
        assert(received) { "메시지를 수신하지 못했습니다." }
        Assertions.assertEquals(message.value1, testKafkaConsumer.objectPayload?.value1)
    }
}