package kr.tareun.concert.interfaces.reservation

import kr.tareun.concert.domain.reservation.model.ReservationInfo
import kr.tareun.concert.domain.concert.model.PaymentStatusType
import kr.tareun.concert.interfaces.common.response.Response
import kr.tareun.concert.interfaces.common.response.ResponseResultType
import kr.tareun.concert.interfaces.reservation.model.ReserveRequest
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RequestMapping("/reservation")
@RestController
class ReservationController {

    @PostMapping("/concerts")
    fun reserveConcertSchedule(@RequestBody request: ReserveRequest): Response<ReservationInfo> {
        return Response(
            ResponseResultType.SUCCESS,
            ReservationInfo(1, request.reservationId, listOf(1, 2, 3), request.userId, LocalDateTime.now().plusMinutes(5), PaymentStatusType.PENDING)
        )
    }

    @GetMapping("/concerts")
    fun getReservationConcerts(@RequestParam userId: Long): Response<List<ReservationInfo>> {
        return Response(
            ResponseResultType.SUCCESS,
            listOf(
                ReservationInfo(1, 1, listOf(1, 2, 3), userId, LocalDateTime.now().plusMinutes(5), PaymentStatusType.CONFIRMED),
                ReservationInfo(2, 2, listOf(1), userId, LocalDateTime.now().plusMinutes(5), PaymentStatusType.PENDING),
                ReservationInfo(3, 3, listOf(1, 2), userId, LocalDateTime.now().plusMinutes(5), PaymentStatusType.EXPIRED),
            )
        )
    }

}