package kr.tareun.concert.domain.concert.model

import java.time.LocalDateTime

data class ConcertSchedule(
    val concertId: Long = 0,
    val scheduleId: Long,
    val ticketPrice: Int,
    val scheduledDate: LocalDateTime,
    val locationId: Long,
    val locationName: String,
    val locationCapacity: Int,
    private var _reservedCount: Int,
) {
    val reservedCount: Int
        get() = _reservedCount

    fun addReservedCount(count: Int) {
        _reservedCount += count
        if (_reservedCount > locationCapacity) {
            throw RuntimeException("예약 가능 인원 수를 초과하였습니다.")
        }
    }
}