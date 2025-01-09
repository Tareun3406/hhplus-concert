package kr.tareun.concert.domain.concert.model

import java.time.LocalDateTime

data class ConcertSchedule(
    var concertId: Long = 0,
    var scheduleId: Long,
    var ticketPrice: Int,
    var scheduledDate: LocalDateTime,
    var locationId: Long,
    var locationName: String,
    var locationCapacity: Int,
    var reservedCount: Int,
)