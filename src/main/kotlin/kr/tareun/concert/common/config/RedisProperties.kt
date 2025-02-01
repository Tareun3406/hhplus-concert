package kr.tareun.concert.common.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "spring.redis")
class RedisProperties {
    var host = "localhost"
    var port = 6379
}