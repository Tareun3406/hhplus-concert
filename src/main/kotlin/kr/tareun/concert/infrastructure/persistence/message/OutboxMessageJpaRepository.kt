package kr.tareun.concert.infrastructure.persistence.message

import kr.tareun.concert.infrastructure.persistence.message.entity.OutboxMessageEntity
import org.springframework.data.jpa.repository.JpaRepository

interface OutboxMessageJpaRepository: JpaRepository<OutboxMessageEntity, Long>