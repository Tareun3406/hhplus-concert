package kr.tareun.concert.interfaces.concert

import kr.tareun.concert.application.concert.ConcertService
import kr.tareun.concert.interfaces.common.response.Response
import kr.tareun.concert.interfaces.common.response.ResponseResultType
import kr.tareun.concert.interfaces.concert.model.ConcertResponse
import kr.tareun.concert.interfaces.concert.model.ConcertScheduleResponse
import kr.tareun.concert.interfaces.concert.model.ConcertSeatResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/concerts")
class ConcertController(
    private val concertService: ConcertService,
) {

    @GetMapping()
    fun getAllConcerts(@RequestParam pageNumber: Int): Response<List<ConcertResponse>> {
        return Response(
            ResponseResultType.SUCCESS,
            concertService.retrieveConcertList(pageNumber).map { ConcertResponse.from(it) }
        )
    }

    @GetMapping("/{concertId}/schedules")
    fun getConcertSchedulesById(@PathVariable concertId: Long): Response<List<ConcertScheduleResponse>> {
        return Response(
            ResponseResultType.SUCCESS,
            concertService.retrieveConcertScheduleList(concertId).map { ConcertScheduleResponse.from(it) },
        )
    }

    @GetMapping("/{concertId}/schedules/{scheduleId}")
    fun getConcertSeatsById(@PathVariable concertId: Long, @PathVariable scheduleId: Long): Response<List<ConcertSeatResponse>> {
        return Response(
            ResponseResultType.SUCCESS,
            concertService.retrieveConcertSeatList(scheduleId).map { ConcertSeatResponse.from(it) }
        )
    }
}