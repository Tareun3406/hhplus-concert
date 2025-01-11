package kr.tareun.concert.interfaces.concert.model

import kr.tareun.concert.application.concert.model.ConcertScheduleResult
import java.time.LocalDateTime

data class ConcertScheduleResponse(
    val concertId: Long,
    val scheduleId: Long,
    val ticketPrice: Int,
    val scheduledDate: LocalDateTime,
    val locationName: String,
    val canReserve: Boolean,
) {
    companion object {
        fun from(concertScheduleResult: ConcertScheduleResult): ConcertScheduleResponse {
            return ConcertScheduleResponse(
                concertId = concertScheduleResult.concertId,
                scheduleId = concertScheduleResult.scheduleId,
                ticketPrice = concertScheduleResult.ticketPrice,
                scheduledDate = concertScheduleResult.scheduledDate,
                locationName = concertScheduleResult.locationName,
                canReserve = concertScheduleResult.canReserve,
            )
        }
    }
}
