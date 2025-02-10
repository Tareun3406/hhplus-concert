package kr.tareun.concert.application.queue

import kr.tareun.concert.domain.queue.QueueRepository
import kr.tareun.concert.common.config.QueueProperties
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Component
class QueueTokenScheduler(
    private val queueRepository: QueueRepository,
    private val queueProperties: QueueProperties,
) {

    @Transactional
    @Scheduled(fixedDelay = 5000)
    fun scheduleActivateToken() {
        queueRepository.removeExpiredTokens()
        val activatedTokenCount = queueRepository.countActivatedToken()
        val availableTokenCount = queueProperties.maxActivateTokenSize - activatedTokenCount
        queueRepository.activateToken(availableTokenCount, LocalDateTime.now().plusMinutes(queueProperties.expiredTimeMinute))
    }

    // 놀이공원 방식
    @Transactional
    @Scheduled(fixedDelay = 6000) // M = 약 6초
    fun scheduleActivateToken2() {
        queueRepository.removeExpiredTokens()
        queueRepository.activateToken(1300, LocalDateTime.now().plusMinutes(queueProperties.expiredTimeMinute)) // N 개 (1300) 토큰 활성화
    }
}