package kr.tareun.concert.infrastructure.persistence.payment

import kr.tareun.concert.infrastructure.persistence.payment.model.PaymentHistoryEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PaymentHistoryJpaRepository: JpaRepository<PaymentHistoryEntity, Long>