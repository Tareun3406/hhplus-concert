package kr.tareun.concert.infrastructure.persistence.reservation

import kr.tareun.concert.domain.reservation.ReservationRepository
import kr.tareun.concert.domain.reservation.model.Reservation
import kr.tareun.concert.domain.reservation.model.ReservationItem
import kr.tareun.concert.domain.reservation.model.ReservationStatusType
import kr.tareun.concert.infrastructure.persistence.reservation.entity.ReservationEntity
import kr.tareun.concert.infrastructure.persistence.reservation.entity.ReservationItemEntity
import org.springframework.stereotype.Repository

@Repository
class ReservationRepositoryImpl (
    private val reservationJpaRepository: ReservationJpaRepository,
    private val reservationItemJpaRepository: ReservationItemJpaRepository
): ReservationRepository {
    override fun getReservationItemListByScheduleIdAndReservationStatusNot(
        scheduleId: Long,
        status: ReservationStatusType
    ): List<ReservationItem> {
        val itemList = reservationItemJpaRepository.getReservationItemListByConcertScheduleIdAndReservationStatusNot(scheduleId, status)
        return itemList.map { it.toReservationItem() }
    }

    override fun saveReserve(reservation: Reservation): Reservation {
        val reservationEntity = ReservationEntity(userId = reservation.userId, priceAmount = reservation.priceAmount)
        val reservationResult = reservationJpaRepository.save(reservationEntity)

        val items = reservation.seatIdList.map {
            ReservationItemEntity(reservationId = reservation.reservationId, concertScheduleId = reservation.concertScheduleId, seatId = it, reservationStatus = reservation.reservationStatus)
        }
        val itemsResult = reservationItemJpaRepository.saveAll(items)

        return reservationResult.toReservation(itemsResult)
    }

    override fun getReservationByIdForUpdate(id: Long): Reservation {
        val reservationEntity = reservationJpaRepository.getReferenceById(id)
        val items = reservationItemJpaRepository.findAllByReservationId(reservationEntity.id)
        return reservationEntity.toReservation(items)
    }
}