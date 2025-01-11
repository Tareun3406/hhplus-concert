package kr.tareun.concert.application.payment

import kr.tareun.concert.application.payment.model.ChargeCommand
import kr.tareun.concert.application.payment.model.PayCommand
import kr.tareun.concert.application.payment.model.PaymentHistoryResult
import kr.tareun.concert.application.payment.model.PointResult
import kr.tareun.concert.domain.payment.PaymentRepository
import kr.tareun.concert.domain.payment.model.PaymentHistory
import kr.tareun.concert.domain.reservation.ReservationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class PaymentService(
    val paymentRepository: PaymentRepository,
    val reservationRepository: ReservationRepository,
) {
    fun retrievePoint(userId: Long): PointResult {
        return PointResult.from(paymentRepository.getPointByUserId(userId))
    }

    fun chargePoint(chargeCommand: ChargeCommand): PointResult {
        val point = paymentRepository.getPointByUserIdForUpdate(chargeCommand.userId)
        point.chargePoint(chargeCommand.amount)
        return PointResult.from(paymentRepository.savePoint(point))
    }

    @Transactional
    fun payReservation(payCommand: PayCommand): PaymentHistoryResult {
        val point = paymentRepository.getPointByUserIdForUpdate(payCommand.userId)
        val reservation = reservationRepository.getReservationByIdForUpdate(payCommand.reservationId)
        point.payPoint(reservation.priceAmount)

        val paymentHistory = PaymentHistory(
            userId = payCommand.userId,
            reservationId = reservation.reservationId,
            paidPoint = reservation.priceAmount
        )
        paymentRepository.savePoint(point)
        return PaymentHistoryResult.from(paymentRepository.savePaymentHistory(paymentHistory))
    }
}