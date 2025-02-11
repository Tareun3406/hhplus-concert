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
) {
    companion object {
        fun from(reservation: Reservation): ReservationEntity {
            return ReservationEntity(
                id = reservation.reservationId,
                userId = reservation.userId,
            )
        }
    }

    fun toReservation(reservationItemEntityList: List<ReservationItemEntity>): Reservation {
        val itemRef = reservationItemEntityList[0]
        return Reservation(
            reservationId = id,
            userId = userId,
            concertScheduleId = itemRef.concertScheduleId,
            seatIds = reservationItemEntityList.map { it.seatId },
            _reservationStatus = itemRef.reservationStatus
        )
    }
}