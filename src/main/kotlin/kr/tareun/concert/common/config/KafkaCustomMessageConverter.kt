package kr.tareun.concert.common.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.kafka.support.Acknowledgment
import org.springframework.kafka.support.converter.RecordMessageConverter
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component
import java.lang.reflect.Type

@Component
class KafkaCustomMessageConverter(
    private val objectMapper: ObjectMapper
) : RecordMessageConverter {

    override fun toMessage(
        record: ConsumerRecord<*, *>?,
        acknowledgment: Acknowledgment?,
        consumer: Consumer<*, *>?,
        payloadType: Type?
    ): Message<*> {
        val bytes = record?.value() as? ByteArray ?: throw IllegalArgumentException("레코드의 값이 바이트 배열이 아닙니다.")
        val targetType = objectMapper.typeFactory.constructType(payloadType) // @KafkaListener 어노테이션이 적용된 메서드의 타입 시그니처 확인
        val deserializedPayload = objectMapper.readerFor(targetType).readValue<Any>(bytes)
        return MessageBuilder.withPayload(deserializedPayload).build()
    }

    override fun fromMessage(message: Message<*>?, defaultTopic: String?): ProducerRecord<*, *> {
        TODO("Not yet implemented") // producer 에 적용할 경우 구현 필요.
    }
}