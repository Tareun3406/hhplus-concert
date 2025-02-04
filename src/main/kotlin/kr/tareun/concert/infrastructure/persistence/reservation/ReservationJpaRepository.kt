package kr.tareun.concert.infrastructure.persistence.reservation

import kr.tareun.concert.infrastructure.persistence.reservation.entity.ReservationEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReservationJpaRepository: JpaRepository<ReservationEntity, Long>