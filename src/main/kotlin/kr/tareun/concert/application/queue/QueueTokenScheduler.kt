package kr.tareun.concert.application.queue

import kr.tareun.concert.domain.queue.QueueRepository
import kr.tareun.concert.domain.queue.model.TokenStatusType
import kr.tareun.concert.infrastructure.config.QueueProperties
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
        val expiredTokens = queueRepository.getAllByStatusAndExpiredTimeLessThan(TokenStatusType.ACTIVATED, LocalDateTime.now())
        expiredTokens.forEach{ it.markedAsExpired() }
        queueRepository.saveAllQueueTokens(expiredTokens)

        val activatedTokenCount = queueRepository.countByStatus(TokenStatusType.ACTIVATED)
        val availableTokenCount = queueProperties.maxActivateTokenSize - activatedTokenCount.toInt() + expiredTokens.size
        val availableTokens = queueRepository.getAllByStatusOrderByIdAscWithLimit(TokenStatusType.ACTIVATED, availableTokenCount)
        availableTokens.forEach {
            it.markedAsActivated();
            it.updateExpiredTime(LocalDateTime.now().plusMinutes(queueProperties.expiredTimeMinute))
        }
        queueRepository.saveAllQueueTokens(availableTokens)
    }
}