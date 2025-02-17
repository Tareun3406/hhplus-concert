package kr.tareun.concert.infrastructure.messaging

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class KafkaProducer(
    private val kafkaStringTemplate: KafkaTemplate<String, String>
) {
    fun sendToString(topic: String, message: String) {
        kafkaStringTemplate.send(topic, message)
    }
}