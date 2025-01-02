package kr.tareun.concert.interfaces.concert

import kr.tareun.concert.domain.concert.model.*
import kr.tareun.concert.interfaces.common.response.Response
import kr.tareun.concert.interfaces.common.response.ResponseResultType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/concerts")
class ConcertController {

    @GetMapping()
    fun getAllConcerts(): Response<List<ConcertInfo>> {
        return Response(
            ResponseResultType.SUCCESS,
            listOf(
                ConcertInfo(1, "콘서트 1", "공연자 1"),
                ConcertInfo(2, "콘서트 2", "공연자 2"),
                ConcertInfo(3, "콘서트 3", "공연자 3")
            )
        )
    }

    @GetMapping("/{concertId}/schedules")
    fun getConcertSchedulesById(@PathVariable concertId: Long): Response<List<ConcertScheduleInfo>> {
        return Response(
            ResponseResultType.SUCCESS,
            listOf(
                ConcertScheduleInfo(concertId, 1, 100_000, LocalDateTime.now().plusDays(1), "공연 장소 1", false),
                ConcertScheduleInfo(concertId, 2, 120_000, LocalDateTime.now().plusDays(2), "공연 장소 1", false),
                ConcertScheduleInfo(concertId, 3, 110_000, LocalDateTime.now().plusDays(3), "공연 장소 2", true),
                ConcertScheduleInfo(concertId, 4, 130_000, LocalDateTime.now().plusDays(4), "공연 장소 2", true),
            )
        )
    }

    @GetMapping("/{concertId}/schedules/{scheduleId}")
    fun getConcertSeatsById(@PathVariable concertId: Long, @PathVariable scheduleId: Long): Response<List<ConcertSeatInfo>> {
        val seatList = mutableListOf<ConcertSeatInfo>()
        for (i in 1 .. 50) {
            seatList.add(ConcertSeatInfo(i.toLong(), 1, i, true))
        }

        return Response(
            ResponseResultType.SUCCESS,
            seatList
        )
    }
}