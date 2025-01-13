package kr.tareun.concert.infrastructure.persistence.reservation.entity

import jakarta.persistence.*
import kr.tareun.concert.domain.reservation.model.Reservation
import kr.tareun.concert.domain.reservation.model.ReservationItem
import kr.tareun.concert.domain.reservation.model.ReservationStatusType
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

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
    var reservationStatus : ReservationStatusType = ReservationStatusType.NON_PAID,

    @CreationTimestamp
    @Column(nullable = false)
    var expiredAt : LocalDateTime
) {
    companion object {
        fun createNewReservationItems(reservation: Reservation, expiredAt: LocalDateTime): List<ReservationItemEntity> {
            return reservation.seatIds.map {
                ReservationItemEntity(
                    reservationId = reservation.reservationId,
                    concertScheduleId = reservation.concertScheduleId,
                    seatId = it,
                    reservationStatus = reservation.reservationStatus,
                    expiredAt = expiredAt
                )
            }
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