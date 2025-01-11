package kr.tareun.concert.infrastructure.persistence.concert.entity

import jakarta.persistence.*
import kr.tareun.concert.domain.concert.model.ConcertSeat

@Table(name = "seat")
@Entity
class SeatEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(nullable = false)
    var locationId : Long,

    @Column(nullable = false)
    var seatNumber: Int,
) {
    fun toConcertSeat(): ConcertSeat {
        return ConcertSeat(id, locationId, seatNumber)
    }
}