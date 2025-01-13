package kr.tareun.concert.domain.reservation.model

import kr.tareun.concert.infrastructure.persistence.reservation.entity.ReservationEntity
import kr.tareun.concert.infrastructure.persistence.reservation.entity.ReservationItemEntity

data class Reservation(
    var reservationId: Long = 0,
    var userId: Long,
    var concertScheduleId: Long,
    var seatIds: List<Long>,
    var priceAmount: Int,
    var reservationStatus: ReservationStatusType,
) {
    companion object {
        fun from(reservationEntity: ReservationEntity, reservationItemEntityList: List<ReservationItemEntity>): Reservation {
            val itemRef = reservationItemEntityList[0]
            return Reservation(
                reservationId = reservationEntity.id,
                userId = reservationEntity.userId,
                concertScheduleId = itemRef.concertScheduleId,
                seatIds = reservationItemEntityList.map { it.seatId },
                priceAmount = reservationEntity.priceAmount,
                reservationStatus = itemRef.reservationStatus
            )
        }
    }

    fun markedAsPaid() {
        this.reservationStatus = ReservationStatusType.PAID
    }
}