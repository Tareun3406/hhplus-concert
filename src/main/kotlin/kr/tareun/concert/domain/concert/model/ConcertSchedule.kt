package kr.tareun.concert.domain.concert.model

import kr.tareun.concert.common.enums.ErrorCode
import kr.tareun.concert.common.exception.CommonException
import java.time.LocalDateTime

data class ConcertSchedule(
    val scheduleId: Long = 0,
    val concertId: Long,
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
            throw CommonException(ErrorCode.CONCERT_SCHEDULE_CAPACITY_EXCEEDED)
        }
    }
}