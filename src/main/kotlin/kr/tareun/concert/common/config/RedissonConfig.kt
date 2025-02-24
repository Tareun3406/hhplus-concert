package kr.tareun.concert.common.config

import kr.tareun.concert.common.config.properties.RedisProperties
import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RedissonConfig(private val redisProperties: RedisProperties) {
    val redissonHostUrl = "redis://${redisProperties.host}:${redisProperties.port}"

    @Bean
    fun redissonClient(): RedissonClient {
        val config = Config()
        config.useSingleServer().address = redissonHostUrl
        config.useSingleServer().password = redisProperties.password
        return Redisson.create(config)
    }
}