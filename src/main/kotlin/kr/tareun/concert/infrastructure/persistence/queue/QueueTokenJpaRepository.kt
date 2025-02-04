package kr.tareun.concert.infrastructure.persistence.queue

import kr.tareun.concert.common.enums.TokenStatusType
import kr.tareun.concert.infrastructure.persistence.queue.model.QueueTokenEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.UUID

@Repository
interface QueueTokenJpaRepository: JpaRepository<QueueTokenEntity, Long> {
    fun getByTokenUuid(uuid: UUID): QueueTokenEntity
    fun countQueueByIdLessThanAndStatus(queueId: Long, status: TokenStatusType): Long
    fun getAllByStatusAndExpiredTimeLessThan(status: TokenStatusType, expiredTime: LocalDateTime): List<QueueTokenEntity>
    fun countByStatus(status: TokenStatusType): Long
    fun getAllByStatusOrderByIdAsc(status: TokenStatusType, pageable: Pageable): List<QueueTokenEntity>
}