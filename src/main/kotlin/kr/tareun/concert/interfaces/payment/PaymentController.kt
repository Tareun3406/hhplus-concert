package kr.tareun.concert.interfaces.payment

import kr.tareun.concert.application.payment.PaymentService
import kr.tareun.concert.application.payment.model.ChargeCommand
import kr.tareun.concert.application.payment.model.PayCommand
import kr.tareun.concert.interfaces.common.response.Response
import kr.tareun.concert.interfaces.common.response.ResponseResultType
import kr.tareun.concert.interfaces.payment.model.ChargeRequest
import kr.tareun.concert.interfaces.payment.model.PayRequest
import kr.tareun.concert.interfaces.payment.model.PaymentHistoryResponse
import kr.tareun.concert.interfaces.payment.model.PointResponse
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/payments")
class PaymentController(
    val paymentService: PaymentService,
) {

    @GetMapping("/points")
    fun getBalance(@RequestParam userId: Long): Response<PointResponse> {
        return Response(
            ResponseResultType.SUCCESS,
            PointResponse.from(paymentService.retrievePoint(userId))
//            PointResponse(userId, 100_000)
        )
    }

    @PostMapping("/charge")
    fun chargeBalance(@RequestBody chargeRequest: ChargeRequest): Response<PointResponse> {
        val command = ChargeCommand.from(chargeRequest)
        return Response(
            ResponseResultType.SUCCESS,
            PointResponse.from(paymentService.chargePoint(command))
//            PointResponse(chargeRequest.userId, 100_000)
        )
    }

    @PostMapping
    fun payReservedConcert(@RequestBody payRequest: PayRequest): Response<PaymentHistoryResponse> {
        val command = PayCommand.from(payRequest)
        return Response(
            ResponseResultType.SUCCESS,
            PaymentHistoryResponse.from(paymentService.payReservation(command))
//            PaymentHistoryResponse(
//                historyId = 1,
//                userId = payRequest.userId,
//                paidAmount = 100_000,
//                reservationId = payRequest.reservationId,
//            )
        )
    }
}