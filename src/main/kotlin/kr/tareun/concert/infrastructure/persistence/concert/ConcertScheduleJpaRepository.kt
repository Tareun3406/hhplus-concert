package kr.tareun.concert.infrastructure.persistence.concert

import kr.tareun.concert.domain.concert.model.ConcertSchedule
import kr.tareun.concert.infrastructure.persistence.concert.entity.ConcertScheduleEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ConcertScheduleJpaRepository: JpaRepository<ConcertScheduleEntity, Long> {
    @Query("""
        SELECT new kr.tareun.concert.domain.concert.model.ConcertSchedule(
            cs.id, 
            cs.concertId, 
            cs.ticketPrice, 
            cs.scheduledTime, 
            l.id, 
            l.name, 
            l.locationCapacity, 
            cs.reservedCount
        )
        FROM ConcertScheduleEntity cs
        JOIN LocationEntity l ON cs.locationId = l.id
        WHERE cs.concertId = :concertId
    """)
    fun findAllSchedule(concertId: Long): List<ConcertSchedule>
}