package kr.tareun.concert.infrastructure.persistence.payment

import jakarta.persistence.LockModeType
import kr.tareun.concert.infrastructure.persistence.payment.model.PointEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.stereotype.Repository

@Repository
interface PointJpaRepository: JpaRepository<PointEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun getForUpdateByUserId(userId: Long): PointEntity

}