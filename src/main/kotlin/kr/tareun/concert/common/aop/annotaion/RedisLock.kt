package kr.tareun.concert.common.aop.annotaion

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RedisLock(
    val prefix: String = "",
    val variableKeys: Array<String>,
    val ttlSec: Long
)
