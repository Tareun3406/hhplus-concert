package kr.tareun.concert.infrastructure.persistence.reservation.entity

import jakarta.persistence.*
import kr.tareun.concert.domain.reservation.model.Reservation

@Table(name = "reservation")
@Entity
class ReservationEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(nullable = false)
    var userId: Long,

    @Column(nullable = false)
    var priceAmount: Int,
) {
    fun toReservation(reservationItemEntityList: List<ReservationItemEntity>): Reservation {
        val itemRef = reservationItemEntityList[0]
        return Reservation(
            reservationId = id,
            userId = userId,
            concertScheduleId = itemRef.concertScheduleId,
            seatIdList = reservationItemEntityList.map { it.seatId },
            priceAmount = priceAmount,
            reservationStatus = itemRef.reservationStatus
        )
    }
}