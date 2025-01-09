package kr.tareun.concert.infrastructure.persistence.concert

import kr.tareun.concert.infrastructure.persistence.concert.entity.ConcertEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ConcertJpaRepository: JpaRepository<ConcertEntity, Long> {
}