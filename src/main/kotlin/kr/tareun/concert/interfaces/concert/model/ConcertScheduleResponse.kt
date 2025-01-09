package kr.tareun.concert.interfaces.concert.model

import java.time.LocalDateTime

data class ConcertScheduleResponse(
    val concertId: Long,
    val scheduleId: Long,
    val ticketPrice: Int,
    val scheduledDate: LocalDateTime,
    val locationName: String,
    val canReserve: Boolean,
)
