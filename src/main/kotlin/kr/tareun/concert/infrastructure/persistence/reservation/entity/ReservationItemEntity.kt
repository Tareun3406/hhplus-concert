package kr.tareun.concert.infrastructure.persistence.reservation.entity

import jakarta.persistence.*
import kr.tareun.concert.domain.reservation.model.Reservation
import kr.tareun.concert.domain.reservation.model.ReservationItem
import kr.tareun.concert.domain.reservation.model.ReservationStatusType

@Table(name = "reservation_item")
@Entity
class ReservationItemEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(nullable = false)
    var reservationId : Long,

    @Column(nullable = false)
    var concertScheduleId : Long,

    @Column(nullable = false)
    var seatId : Long,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var reservationStatus : ReservationStatusType = ReservationStatusType.PENDING,
) {
    companion object {
        fun createNewReservationItem(reservation: Reservation, seatId: Long): ReservationItemEntity {
            return ReservationItemEntity(
                reservationId = reservation.reservationId,
                concertScheduleId = reservation.concertScheduleId,
                seatId = seatId,
                reservationStatus = reservation.reservationStatus
            )
        }
    }

    fun toReservationItem(): ReservationItem {
        return ReservationItem(
            itemId = id,
            reservationId = reservationId,
            concertScheduleId = concertScheduleId,
            seatId = seatId,
            reservationStatus = reservationStatus
        )
    }
}