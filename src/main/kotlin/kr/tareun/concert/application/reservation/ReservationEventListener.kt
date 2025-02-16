package kr.tareun.concert.application.reservation

import kr.tareun.concert.application.payment.model.PaySuccessEvent
import kr.tareun.concert.application.reservation.model.RequestedReserveConcertEvent
import kr.tareun.concert.application.reservation.model.ReservationSuccessStatusCommand
import kr.tareun.concert.common.enums.PayOrderType
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class ReservationEventListener(
    private val reservationService: ReservationService,
) {
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun handlePaidReservation(paySuccessEvent: PaySuccessEvent){
        when(paySuccessEvent.orderType) {
            PayOrderType.CONCERT -> reservationService.setSuccessConcertReservationStatus(ReservationSuccessStatusCommand.from(paySuccessEvent))
        }
    }

    @Async
    @EventListener
    fun handleOnReserveConcert(requestedReserveConcertEvent: RequestedReserveConcertEvent){
        reservationService.increaseConcertReservationCount(requestedReserveConcertEvent.concert)
    }
}