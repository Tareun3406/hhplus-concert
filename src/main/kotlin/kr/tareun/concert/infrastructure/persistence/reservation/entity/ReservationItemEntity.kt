package kr.tareun.concert.infrastructure.persistence.reservation.entity

import jakarta.persistence.*
import kr.tareun.concert.domain.reservation.model.Reservation
import kr.tareun.concert.domain.reservation.model.ReservationItem
import kr.tareun.concert.common.enums.ReservationStatusType
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Table(name = "reservation_item")
// 조회 1: concertScheduleId, reservationStatus, expiredAt  (예약이 불가능한 좌석 리스트 조회. status 가 결제 완료 상태이거나 예약 유효시간이 남은 경우) -> 2번조회 인덱스 사용
// 조회 2: concertScheduleId, seatId, reservationStatus, expiredAt (예약 하기 전 이미 해당 좌석에 유요한 예약이 있는지 확인, 1번 조회에서도 같이 쓸 수 있도록 조회 조건 수정),
//          -> concertScheduleId, reservationStatus, expiredAt, seatId
// 조회 3: reservationID 외래키
// 조회 4: 외래키 concertScheduleId 는 필요하지 않음. (2번 조회 인덱스 활용 가능)
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