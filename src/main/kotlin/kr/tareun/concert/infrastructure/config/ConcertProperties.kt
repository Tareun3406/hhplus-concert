package kr.tareun.concert.infrastructure.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "app.concert")
class ConcertProperties {
    val concertListPageSize = 10
}