package kr.tareun.concert.application.concert.model

import kr.tareun.concert.domain.concert.model.ConcertSchedule
import java.time.LocalDateTime

data class ConcertScheduleResult(
    val concertId: Long,
    val scheduleId: Long,
    val ticketPrice: Int,
    val scheduledDate: LocalDateTime,
    val locationName: String,
    val canReserve: Boolean,
) {
    companion object {
        fun from(concertSchedule: ConcertSchedule): ConcertScheduleResult {
            return ConcertScheduleResult(
                concertId = concertSchedule.concertId,
                scheduleId = concertSchedule.scheduleId,
                ticketPrice = concertSchedule.ticketPrice,
                scheduledDate = concertSchedule.scheduledDate,
                locationName = concertSchedule.locationName,
                canReserve = concertSchedule.reservedCount < concertSchedule.locationCapacity
            )
        }
    }
}
