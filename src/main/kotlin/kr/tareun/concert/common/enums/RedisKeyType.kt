package kr.tareun.concert.common.enums

enum class RedisKeyType(val key: String) {
    QUEUE_TOKEN_PENDING("token:pending"),
    QUEUE_TOKEN_ACTIVATED("token:activated"),
    RESERVATION_CONCERT_RANKING("concert-ranking"), ;

    fun getKeyWithVariable(variable: String): String {
        return "$key:$variable"
    }
}