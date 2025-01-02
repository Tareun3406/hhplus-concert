package kr.tareun.concert.domain.concert.model

import java.time.LocalDateTime

data class ConcertScheduleInfo(
    val concertId: Long,
    val scheduleId: Long,
    val ticketPrice: Int,
    val scheduledDate: LocalDateTime,
    val venueName: String,
    val canReserve: Boolean,
)