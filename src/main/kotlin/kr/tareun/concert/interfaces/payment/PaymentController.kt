package kr.tareun.concert.interfaces.payment

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
class PaymentController {

    @GetMapping("/points")
    fun getBalance(@RequestParam userId: Long): Response<PointResponse> {
        return Response(
            ResponseResultType.SUCCESS,
            PointResponse(userId, 100_000)
        )
    }

    @PostMapping("/charge")
    fun chargeBalance(@RequestBody chargeRequest: ChargeRequest): Response<PointResponse> {
        return Response(
            ResponseResultType.SUCCESS,
            PointResponse(chargeRequest.userId, 100_000)
        )
    }

    @PostMapping
    fun payReservedConcert(@RequestBody payRequest: PayRequest): Response<PaymentHistoryResponse> {
        return Response(
            ResponseResultType.SUCCESS,
            PaymentHistoryResponse(
                historyId = 1,
                userId = payRequest.userId,
                paidAmount = 100_000,
                reservationId = payRequest.reservationId,
            )
        )
    }
}