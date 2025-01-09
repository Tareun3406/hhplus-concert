package kr.tareun.concert.interfaces.concert

import kr.tareun.concert.interfaces.common.response.Response
import kr.tareun.concert.interfaces.common.response.ResponseResultType
import kr.tareun.concert.interfaces.concert.model.ConcertResponse
import kr.tareun.concert.interfaces.concert.model.ConcertScheduleResponse
import kr.tareun.concert.interfaces.concert.model.ConcertSeatResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/concerts")
class ConcertController {

    @GetMapping()
    fun getAllConcerts(): Response<List<ConcertResponse>> {
        return Response(
            ResponseResultType.SUCCESS,
            listOf(
                ConcertResponse(1, "콘서트 1", "공연자 1"),
                ConcertResponse(2, "콘서트 2", "공연자 2"),
                ConcertResponse(3, "콘서트 3", "공연자 3")
            )
        )
    }

    @GetMapping("/{concertId}/schedules")
    fun getConcertSchedulesById(@PathVariable concertId: Long): Response<List<ConcertScheduleResponse>> {
        return Response(
            ResponseResultType.SUCCESS,
            listOf(
                ConcertScheduleResponse(concertId, 1, 100_000, LocalDateTime.now().plusDays(1), "공연 장소 1", false),
                ConcertScheduleResponse(concertId, 2, 120_000, LocalDateTime.now().plusDays(2), "공연 장소 1", false),
                ConcertScheduleResponse(concertId, 3, 110_000, LocalDateTime.now().plusDays(3), "공연 장소 2", true),
                ConcertScheduleResponse(concertId, 4, 130_000, LocalDateTime.now().plusDays(4), "공연 장소 2", true),
            )
        )
    }

    @GetMapping("/{concertId}/schedules/{scheduleId}")
    fun getConcertSeatsById(@PathVariable concertId: Long, @PathVariable scheduleId: Long): Response<List<ConcertSeatResponse>> {
        val seatList = mutableListOf<ConcertSeatResponse>()
        for (i in 1 .. 50) {
            seatList.add(ConcertSeatResponse(i.toLong(), 1, i, true))
        }

        return Response(
            ResponseResultType.SUCCESS,
            seatList
        )
    }
}