package kr.tareun.concert.common.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "app.concert")
class ConcertProperties {
    val concertListPageSize = 10
    val expiredMinute = 5L
}