package kr.tareun.concert.infrastructure.persistence.concert.entity

import jakarta.persistence.*
import kr.tareun.concert.domain.concert.model.ConcertSchedule
import java.time.LocalDateTime

@Table(name = "concert_schedule")
@Entity
class ConcertScheduleEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(nullable = false)
    var concertId: Long,

    @Column(nullable = false)
    var ticketPrice: Int,

    @Column(nullable = false)
    var scheduledTime: LocalDateTime,

    @Column(nullable = false)
    var locationId: Long,

    @Column(nullable = false)
    var reservedCount: Int = 0,
) {
    companion object {
        fun from(concertSchedule: ConcertSchedule): ConcertScheduleEntity {
            return ConcertScheduleEntity(
                id = concertSchedule.scheduleId,
                concertId = concertSchedule.concertId,
                ticketPrice = concertSchedule.ticketPrice,
                scheduledTime = concertSchedule.scheduledDate,
                locationId = concertSchedule.locationId
            )
        }
    }
    fun toConcertSchedule(locationEntity: LocationEntity): ConcertSchedule {
        return ConcertSchedule(
            concertId = concertId,
            scheduleId = id,
            ticketPrice = ticketPrice,
            scheduledDate = scheduledTime,
            locationId = locationEntity.id,
            locationName = locationEntity.name,
            locationCapacity = locationEntity.locationCapacity,
            _reservedCount = reservedCount

        )
    }
}