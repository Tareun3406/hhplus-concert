package kr.tareun.concert.infrastructure.persistence.message

import kr.tareun.concert.common.enums.MessageStatus
import kr.tareun.concert.infrastructure.persistence.message.entity.OutboxMessageEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface OutboxMessageJpaRepository: JpaRepository<OutboxMessageEntity, Long> {
    fun findAllByStatusAndCreatedAtBefore(messageStatus: MessageStatus, timestamp: LocalDateTime): List<OutboxMessageEntity>
}