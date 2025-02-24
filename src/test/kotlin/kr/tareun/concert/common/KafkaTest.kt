package kr.tareun.concert.common

import com.fasterxml.jackson.databind.ObjectMapper
import kr.tareun.concert.KafkaTestConfiguration
//import org.apache.kafka.common.serialization.StringSerializer
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.ClassPathResource
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.init.ScriptUtils
import org.springframework.kafka.core.KafkaTemplate
//import org.springframework.kafka.support.serializer.JsonDeserializer
import java.sql.SQLException
import java.util.concurrent.TimeUnit


@Suppress("NonAsciiCharacters")
@SpringBootTest
class KafkaTest {

    @Autowired
    lateinit var kafkaTemplate: KafkaTemplate<String, String>

    @Autowired
    lateinit var testKafkaConsumer: KafkaTestConfiguration.TestKafkaConsumer

    @Autowired
    lateinit var testKafkaProducer: KafkaTestConfiguration.TestKafkaProducer

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun resetDatabase() {
        val resource = ClassPathResource("init.sql")
        try {
            ScriptUtils.executeSqlScript(jdbcTemplate.dataSource!!.connection, resource)
        } catch (ex: SQLException) {
            throw RuntimeException("Database reset failed: ${ex.message}", ex)
        }
    }

    @Test
    fun `JSON 문자열로 변환된 메세지를 카프카에 전달하고 객체로 읽을 수 있다`() {
        // given
        val message = KafkaTestConfiguration.KafkaMessage("1", "2")
        val jsonMessage = objectMapper.writeValueAsString(message) // DB에 저장할하고 카프카로 전송할 JSON 문자열

        // 카프카에 전송 및 읽기 시뮬레이션
//        val stringSerialized = StringSerializer().serialize("test-topic2", jsonMessage)!! // DB에 저장했던 JSON 문자열을 직렬화
//        val deserializer = JsonDeserializer(KafkaTestConfiguration.KafkaMessage::class.java) // JSON 문자열을 직렬화 한 메세지를 객체로 역직렬화 가능한지 테스트
//        val deserializedMessage = deserializer.deserialize("test-topic2", stringSerialized)

        // when
        kafkaTemplate.send("test-topic1", jsonMessage)
        val received = testKafkaConsumer.latch.await(10, TimeUnit.SECONDS)

        // then
        assert(received) { "메세지를 수신하지 못했습니다." }
        Assertions.assertEquals(message.value1, testKafkaConsumer.objectPayload?.value1)
        Assertions.assertEquals(message.value2, testKafkaConsumer.objectPayload?.value2)
    }

    @Test
    fun `어노테이션으로 카프카에 메세지를 전달하고 읽을 수 있다`() {
        // given
        val message = KafkaTestConfiguration.KafkaMessage("1", "2")

        // when
        testKafkaProducer.produce1(message)
        val received = testKafkaConsumer.latch.await(10, TimeUnit.SECONDS)

        // then
        assert(received) { "메세지를 수신하지 못했습니다." }
        Assertions.assertEquals(message.value1, testKafkaConsumer.objectPayload?.value1)
        Assertions.assertEquals(message.value2, testKafkaConsumer.objectPayload?.value2)
    }
}