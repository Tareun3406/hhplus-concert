package kr.tareun.concert.infrastructure.persistence.reservation

import jakarta.persistence.LockModeType
import kr.tareun.concert.common.enums.ReservationStatusType
import kr.tareun.concert.infrastructure.persistence.reservation.entity.ReservationItemEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository

interface ReservationItemJpaRepository: JpaRepository<ReservationItemEntity, Long> {
    fun findAllByReservationId(reservationId: Long): List<ReservationItemEntity>

    @Query("""
        SELECT e FROM ReservationItemEntity e
        WHERE e.concertScheduleId = :scheduleId
          AND (e.reservationStatus = :reservationStatus OR e.expiredAt > :expiredAt)
    """)
    fun findAllByScheduleIdAndStatusOrExpiredAt(scheduleId: Long, reservationStatus: ReservationStatusType, expiredAt: LocalDateTime): List<ReservationItemEntity>

    @Query("""
        SELECT e FROM ReservationItemEntity e
        WHERE e.concertScheduleId = :scheduleId
          AND (e.reservationStatus = :reservationStatus OR e.expiredAt > :expiredAt)
          AND e.seatId IN :seatIds
    """)
    fun findAllByScheduleIdAndSeatIdAndStatusOrExpiredAt(scheduleId: Long, seatIds: List<Long>, reservationStatus: ReservationStatusType, expiredAt: LocalDateTime): List<ReservationItemEntity>

    // concertScheduleId 컬럼에 index 필수
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findAllWithWriteLockByConcertScheduleId(scheduleId: Long): List<ReservationItemEntity>
}