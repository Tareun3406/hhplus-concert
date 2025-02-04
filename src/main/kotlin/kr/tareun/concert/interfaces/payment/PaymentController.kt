package kr.tareun.concert.interfaces.payment

import kr.tareun.concert.application.payment.PaymentService
import kr.tareun.concert.application.payment.model.ChargeCommand
import kr.tareun.concert.interfaces.common.response.Response
import kr.tareun.concert.common.enums.ResponseResultType
import kr.tareun.concert.interfaces.payment.model.ChargeRequest
import kr.tareun.concert.interfaces.payment.model.PointResponse
import org.springframework.web.bind.annotation.*

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
        )
    }

    @PostMapping("/charge")
    fun chargeBalance(@RequestBody chargeRequest: ChargeRequest): Response<PointResponse> {
        val command = ChargeCommand.from(chargeRequest)
        return Response(
            ResponseResultType.SUCCESS,
            PointResponse.from(paymentService.chargePoint(command))
        )
    }
}