package kr.tareun.concert.infrastructure.persistence.concert

import kr.tareun.concert.infrastructure.persistence.concert.entity.SeatEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SeatJpaRepository: JpaRepository<SeatEntity, Long> {
    fun findAllByLocationId(locationId: Long): List<SeatEntity>
}