package kr.tareun.concert.infrastructure.persistence.reservation

import kr.tareun.concert.domain.reservation.model.ReservationStatusType
import kr.tareun.concert.infrastructure.persistence.reservation.entity.ReservationItemEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface ReservationItemJpaRepository: JpaRepository<ReservationItemEntity, Long> {
    fun findAllByReservationId(reservationId: Long): List<ReservationItemEntity>

    @Query("""
        SELECT e FROM ReservationItemEntity e
        WHERE e.concertScheduleId = :scheduleId
          AND (e.reservationStatus = :reservationStatus OR e.expiredAt < :expiredAt)
    """)
    fun findAllByScheduleIdAndStatusOrExpiredAt(scheduleId: Long, reservationStatus: ReservationStatusType, expiredAt: LocalDateTime): List<ReservationItemEntity>
}