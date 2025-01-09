package kr.tareun.concert.infrastructure.persistence.reservation

import kr.tareun.concert.domain.reservation.model.ReservationStatusType
import kr.tareun.concert.infrastructure.persistence.reservation.entity.ReservationItemEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReservationItemJpaRepository: JpaRepository<ReservationItemEntity, Long> {
    fun findAllByReservationId(reservationId: Long): List<ReservationItemEntity>
    fun getReservationItemListByConcertScheduleIdAndReservationStatusNot(scheduleId: Long, paymentStatus: ReservationStatusType): List<ReservationItemEntity>
}