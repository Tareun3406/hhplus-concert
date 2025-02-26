package kr.tareun.concert.common.aop

import kr.tareun.concert.common.aop.annotaion.RedisLock
import kr.tareun.concert.common.aop.util.CustomSpringELParser
import kr.tareun.concert.common.exception.RedisLockException
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.redisson.api.RedissonClient
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Aspect
@Component
@Order(Ordered.LOWEST_PRECEDENCE - 1) // @Transactional 의 order=Ordered.LOWEST_PRECEDENCE
class RedisLockAop(private val redissonClient: RedissonClient) {
    private val customSpringELParser = CustomSpringELParser()

    @Around("@annotation(redisLock)")
    fun handleLock(joinPoint: ProceedingJoinPoint, redisLock: RedisLock): Any {
        val methodSignature = joinPoint.signature as MethodSignature
        val methodParameterNames =methodSignature.method.parameters.map { it.name }.toTypedArray()
        val methodArgs = joinPoint.args

        val keys = customSpringELParser.parseSpELKeys(redisLock.prefix, redisLock.variableKeys, methodArgs, methodParameterNames)
        val locks = keys.sorted().map {redissonClient.getLock(it)}.toTypedArray() // 정렬이 없을 경우 데드락 발생 가능
        val multiLock = redissonClient.getMultiLock(*locks)

        // 락 획득 대기 10초, 획득후 ttl 초 유지
        val isLockAcquired = multiLock.tryLock(10000, redisLock.ttlSec, java.util.concurrent.TimeUnit.SECONDS)
        return try {
            if (isLockAcquired) {
                // 락 획득 성공 시 메서드 실행
                joinPoint.proceed()
            } else {
                throw RedisLockException("Could not acquire lock for key : prefix - ${redisLock.prefix}, variable - $keys")
            }
        } finally {
            if (isLockAcquired) {
                multiLock.unlock() // 락 해제
            }
        }
    }
}