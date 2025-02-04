package kr.tareun.concert.infrastructure.persistence.reservation

import kr.tareun.concert.domain.reservation.ReservationRepository
import kr.tareun.concert.domain.reservation.model.Reservation
import kr.tareun.concert.domain.reservation.model.ReservationItem
import kr.tareun.concert.common.enums.ReservationStatusType
import kr.tareun.concert.common.config.ConcertProperties
import kr.tareun.concert.infrastructure.persistence.reservation.entity.ReservationEntity
import kr.tareun.concert.infrastructure.persistence.reservation.entity.ReservationItemEntity
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class ReservationRepositoryImpl (
    private val reservationJpaRepository: ReservationJpaRepository,
    private val reservationItemJpaRepository: ReservationItemJpaRepository,

    private val concertProperties: ConcertProperties
): ReservationRepository {
    override fun getAllValidReservationItem(scheduleId: Long): List<ReservationItem> {
        val itemList = reservationItemJpaRepository.findAllByScheduleIdAndStatusOrExpiredAt(scheduleId, ReservationStatusType.PAID, LocalDateTime.now())
        return itemList.map { it.toReservationItem() }
    }

    override fun getAllReservationItemByScheduleIdAndSeatId(scheduleId: Long, seatIds: List<Long>): List<ReservationItem> {
        return reservationItemJpaRepository.findAllByScheduleIdAndSeatIdAndStatusOrExpiredAt(scheduleId, seatIds, ReservationStatusType.PAID, LocalDateTime.now())
            .map { it.toReservationItem() }
    }

    override fun saveReservation(reservation: Reservation): Reservation {
        val reservationEntity = ReservationEntity.from(reservation)
        val reservationResult = reservationJpaRepository.save(reservationEntity)

        val items: List<ReservationItemEntity>
        if (reservation.reservationId == 0L) {
            items = ReservationItemEntity.createNewReservationItems(reservation, LocalDateTime.now().plusMinutes(concertProperties.expiredMinute))
        } else {
            items = reservationItemJpaRepository.findAllByReservationId(reservation.reservationId)
            items.forEach{ it.reservationStatus = reservation.reservationStatus }
        }
        val itemsResult = reservationItemJpaRepository.saveAll(items)

        return reservationResult.toReservation(itemsResult)
    }

    override fun getReservationByIdForUpdate(id: Long): Reservation {
        val reservationEntity = reservationJpaRepository.getReferenceById(id)
        val items = reservationItemJpaRepository.findAllByReservationId(reservationEntity.id)
        return reservationEntity.toReservation(items)
    }

    override fun acquireLockByScheduleId(concertScheduleId: Long) {
        reservationItemJpaRepository.findAllWithWriteLockByConcertScheduleId(concertScheduleId)
    }
}