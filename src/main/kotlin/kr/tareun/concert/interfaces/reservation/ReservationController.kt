package kr.tareun.concert.interfaces.reservation

import kr.tareun.concert.application.reservation.ReservationService
import kr.tareun.concert.application.reservation.model.ReserveCommand
import kr.tareun.concert.interfaces.common.response.Response
import kr.tareun.concert.common.enums.ResponseResultType
import kr.tareun.concert.interfaces.reservation.model.ReservationRankedConcertResponse
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
        )
    }

    // fixme 예약 - 결제 플로우가 이벤트로 연결됨.
//    @PostMapping("/pay")
//    fun payReservedConcert(@RequestBody payRequest: PayRequest, @RequestHeader("Queue-Token") tokenUuid: UUID): Response<PaymentHistoryResponse> {
//        val command = PayCommand.from(payRequest, tokenUuid)
//        return Response(
//            ResponseResultType.SUCCESS,
//            PaymentHistoryResponse.from(reservationService.payReservation(command))
//        )
//    }

    @GetMapping("/rankedConcerts")
    fun getRankedConcerts(@RequestParam rankingSize: Int): Response<List<ReservationRankedConcertResponse>> {
        return Response(
            ResponseResultType.SUCCESS,
            reservationService.getReservationRankedList(rankingSize).map { ReservationRankedConcertResponse.from(it) }
        )
    }
}