package kr.tareun.concert.common.aop.annotaion

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class OutboxEvent(
    val topic: String,
)
