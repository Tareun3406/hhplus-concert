package kr.tareun.concert.interfaces.reservation

import kr.tareun.concert.application.payment.model.PayCommand
import kr.tareun.concert.application.reservation.ReservationService
import kr.tareun.concert.application.reservation.model.ReserveCommand
import kr.tareun.concert.interfaces.common.response.Response
import kr.tareun.concert.interfaces.common.response.ResponseResultType
import kr.tareun.concert.interfaces.payment.model.PayRequest
import kr.tareun.concert.interfaces.payment.model.PaymentHistoryResponse
import kr.tareun.concert.interfaces.reservation.model.ReservationResponse
import kr.tareun.concert.interfaces.reservation.model.ReserveRequest
import org.springframework.web.bind.annotation.*
import java.util.UUID

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
        )
    }

    @PostMapping("/pay")
    fun payReservedConcert(@RequestBody payRequest: PayRequest, @RequestHeader("Queue-Token") tokenUuid: UUID): Response<PaymentHistoryResponse> {
        val command = PayCommand.from(payRequest, tokenUuid)
        return Response(
            ResponseResultType.SUCCESS,
            PaymentHistoryResponse.from(reservationService.payReservation(command))
        )
    }
}