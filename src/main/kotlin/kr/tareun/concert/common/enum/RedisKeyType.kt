package kr.tareun.concert.common.enum

enum class RedisKeyType(val key: String) {
    QUEUE_TOKEN_PENDING("token:pending"),
    QUEUE_TOKEN_ACTIVATED("token:activated"),
}