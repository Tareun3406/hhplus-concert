package kr.tareun.concert.interfaces.reservation

import kr.tareun.concert.interfaces.common.response.Response
import kr.tareun.concert.interfaces.common.response.ResponseResultType
import kr.tareun.concert.interfaces.reservation.model.ReservationResponse
import kr.tareun.concert.interfaces.reservation.model.ReserveRequest
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RequestMapping("/reservation")
@RestController
class ReservationController {

    @PostMapping("/concerts")
    fun reserveConcertSchedule(@RequestBody request: ReserveRequest): Response<ReservationResponse> {
        return Response(
            ResponseResultType.SUCCESS,
            ReservationResponse(
                reservationId = 1,
                userId = request.userId,
                scheduleId = request.concertScheduleId,
                seatNumbers = listOf(1, 2, 3),
                expirationTime = LocalDateTime.now().plusMinutes(5)
            )
        )
    }
}