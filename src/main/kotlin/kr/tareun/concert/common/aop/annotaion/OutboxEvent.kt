package kr.tareun.concert.common.aop.annotaion

import kr.tareun.concert.common.enums.BrokerType

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class OutboxEvent(
    val topic: String,
    val brokerType: BrokerType,
)
