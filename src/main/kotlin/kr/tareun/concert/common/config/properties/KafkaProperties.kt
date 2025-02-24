package kr.tareun.concert.common.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "spring.kafka")
class KafkaProperties {
    var bootstrapServers: String = "localhost:9092"
}
