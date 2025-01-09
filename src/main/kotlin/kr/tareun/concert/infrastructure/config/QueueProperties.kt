package kr.tareun.concert.infrastructure.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "app.queue")
class QueueProperties {
    val maxActivateTokenSize = 100
    val expiredTimeMinute = 10L
}