package kr.tareun.concert.infrastructure.persistence.payment.model

import jakarta.persistence.*
import kr.tareun.concert.domain.payment.model.PaymentHistory

@Entity
class PaymentHistoryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(nullable = false)
    var userId : Long,

    @Column(nullable = false)
    var reservationId : Long,

    @Column(nullable = false)
    var paidPoint: Int,
) {
    companion object {
        fun fromPaymentHistory(paymentHistory: PaymentHistory): PaymentHistoryEntity {
            return PaymentHistoryEntity(
                id = paymentHistory.historyId,
                userId = paymentHistory.userId,
                reservationId = paymentHistory.reservationId,
                paidPoint = paymentHistory.paidPoint
            )
        }
    }
    fun toPaymentHistory(): PaymentHistory {
        return PaymentHistory(
            historyId = id,
            userId = this.userId,
            reservationId = this.reservationId,
            paidPoint = this.paidPoint
        )
    }
}