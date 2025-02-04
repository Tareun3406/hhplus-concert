package kr.tareun.concert.concert

import kr.tareun.concert.common.enums.ErrorCode
import kr.tareun.concert.common.exception.CommonException
import kr.tareun.concert.domain.concert.model.ConcertSchedule
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

@Suppress("NonAsciiCharacters")
class ConcertScheduleUnitTest {

    @Test
    fun `콘서트 일정에 예매 횟수를 추가 할 수 있다`() {
        // given
        val schedule = ConcertSchedule(
            concertId = 1,
            scheduleId = 1,
            ticketPrice = 10000,
            scheduledDate = LocalDateTime.now().plusDays(1),
            locationId = 1,
            locationName = "공연장 1",
            locationCapacity = 100,
            _reservedCount = 99
        )

        // when
        schedule.addReservedCount(1)

        // then
        Assertions.assertEquals(100 , schedule.reservedCount)
    }

    @Test
    fun `콘서트 예매시 최대 횟수를 초과할 경우 에러 코드 CONCERT_SCHEDULE_CAPACITY_EXCEEDED 를 던진다`() {
        // when
        val schedule = ConcertSchedule(
            concertId = 1,
            scheduleId = 1,
            ticketPrice = 10000,
            scheduledDate = LocalDateTime.now().plusDays(1),
            locationId = 1,
            locationName = "공연장 1",
            locationCapacity = 100,
            _reservedCount = 100
        )

        // when - then
        val thrown = Assertions.assertThrows(CommonException::class.java) {
            schedule.addReservedCount(1)
        }
        Assertions.assertEquals(ErrorCode.CONCERT_SCHEDULE_CAPACITY_EXCEEDED, thrown.errorCode)
    }
}