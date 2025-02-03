package kr.tareun.concert.common.enums

enum class RedisKeyType(val key: String) {
    QUEUE_TOKEN_PENDING("token:pending"),
    QUEUE_TOKEN_ACTIVATED("token:activated"),
}