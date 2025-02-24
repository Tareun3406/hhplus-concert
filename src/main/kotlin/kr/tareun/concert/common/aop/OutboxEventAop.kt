package kr.tareun.concert.common.aop

import kr.tareun.concert.common.aop.annotaion.OutboxEvent
import kr.tareun.concert.common.enums.ErrorCode
import kr.tareun.concert.common.exception.CommonException
import kr.tareun.concert.domain.message.OutboxMessageDomainService
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.stereotype.Component
import org.springframework.transaction.support.TransactionSynchronization
import org.springframework.transaction.support.TransactionSynchronizationManager

@Aspect
@Component
class OutboxEventAop(
    private val outboxMessageDomainService: OutboxMessageDomainService,
) {
    @Pointcut("@annotation(kr.tareun.concert.common.aop.annotaion.OutboxEvent)")
    fun eventPublishMethods() {}

    @AfterReturning(pointcut = "eventPublishMethods()", returning = "result")
    fun publishEvent(joinPoint: JoinPoint, result: Any?) {
        if (!TransactionSynchronizationManager.isSynchronizationActive()) {
            throw CommonException(ErrorCode.OUTBOX_ANNOTATION_REQUIRED_TRANSACTION) // @Transactional 미적용시 예외
        }

        val methodSignature = joinPoint.signature as MethodSignature
        val method = methodSignature.method
        val annotation = method.getAnnotation(OutboxEvent::class.java)
        result?.let {

            // 트랜잭션 커밋 이전 DB 저장 로직 (@AfterReturning 메서드는 같은 트랜잭션에서 실행됨)
            val outboxMessage = outboxMessageDomainService.createAndSaveOutboxMessage(annotation.topic, it, annotation.brokerType)

            // 트랜잭션 커밋 이후 동작 설정
            TransactionSynchronizationManager.registerSynchronization(object : TransactionSynchronization {
                override fun afterCommit() {
                    outboxMessageDomainService.publishMessageAndUpdateStatus(outboxMessage)
                }
            })
        }
    }
}