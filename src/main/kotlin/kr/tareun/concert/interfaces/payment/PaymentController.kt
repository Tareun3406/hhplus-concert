package kr.tareun.concert.interfaces.payment

import kr.tareun.concert.domain.payment.model.ConcertPaymentHistoryInfo
import kr.tareun.concert.domain.payment.model.BalanceInfo
import kr.tareun.concert.interfaces.common.response.Response
import kr.tareun.concert.interfaces.common.response.ResponseResultType
import kr.tareun.concert.interfaces.payment.model.ChargeRequest
import kr.tareun.concert.interfaces.payment.model.PayRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/payments")
class PaymentController {

    @GetMapping("/balance")
    fun getBalance(@RequestParam userId: Long): Response<BalanceInfo> {
        return Response(
            ResponseResultType.SUCCESS,
            BalanceInfo(1, userId, 100_000)
        )
    }

    @PostMapping("/charge")
    fun chargeBalance(@RequestBody chargeRequest: ChargeRequest): Response<BalanceInfo> {
        return Response(
            ResponseResultType.SUCCESS,
            BalanceInfo(1, chargeRequest.userId, 100_000)
        )
    }

    @PostMapping
    fun payReservedConcert(@RequestBody payRequest: PayRequest): Response<ConcertPaymentHistoryInfo> {
        return Response(
            ResponseResultType.SUCCESS,
            ConcertPaymentHistoryInfo(1, payRequest.userId, payRequest.reservationId, 100_000, 10_000, 90_000)
        )
    }

    @GetMapping
    fun getPaymentHistory(): Response<List<ConcertPaymentHistoryInfo>> {
        return Response(
            ResponseResultType.SUCCESS,
            listOf(
                ConcertPaymentHistoryInfo(1, 1, 1, 100_000, 10_000, 90_000),
                ConcertPaymentHistoryInfo(2, 1, 2, 90_000, 50_000, 40_000),
                ConcertPaymentHistoryInfo(3, 1, 3, 100_000, 10_000, 90_000)
            )
        )
    }
}