package kr.tareun.concert.interfaces.reservation

import kr.tareun.concert.application.reservation.ReservationService
import kr.tareun.concert.application.reservation.model.ReserveCommand
import kr.tareun.concert.interfaces.common.response.Response
import kr.tareun.concert.interfaces.common.response.ResponseResultType
import kr.tareun.concert.interfaces.reservation.model.ReservationResponse
import kr.tareun.concert.interfaces.reservation.model.ReserveRequest
import org.springframework.web.bind.annotation.*

@RequestMapping("/reservation")
@RestController
class ReservationController(
    val reservationService: ReservationService,
) {

    @PostMapping("/concerts")
    fun reserveConcertSchedule(@RequestBody request: ReserveRequest): Response<ReservationResponse> {
        val command = ReserveCommand.from(request)
        return Response(
            ResponseResultType.SUCCESS,
            ReservationResponse.from(reservationService.reserveConcert(command))
//            ReservationResponse(
//                reservationId = 1,
//                userId = request.userId,
//                scheduleId = request.concertScheduleId,
//                seatNumbers = listOf(1, 2, 3),
//            )
        )
    }
}