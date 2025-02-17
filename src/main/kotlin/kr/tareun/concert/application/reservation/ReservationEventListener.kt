package kr.tareun.concert.application.reservation

import kr.tareun.concert.application.concert.model.ConcertPublishPayEventCommand
import kr.tareun.concert.application.payment.model.PaySuccessEvent
import kr.tareun.concert.application.reservation.model.RequestedReserveConcertEvent
import kr.tareun.concert.application.reservation.model.ReservationSuccessStatusCommand
import kr.tareun.concert.application.reservation.model.ReservedConcertEvent
import kr.tareun.concert.common.enums.PayOrderType
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class ReservationEventListener(
    private val reservationService: ReservationService,
) {
    private val logger = LoggerFactory.getLogger(ReservationEventListener::class.java)

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handlePublishPayOrderEvent(reservedConcertEvent: ReservedConcertEvent) {
        try {
            reservationService.publishPayOrderEvent(ConcertPublishPayEventCommand.from(reservedConcertEvent))
        } catch (e: Exception) {
            logger.error("결제 요청 실패", e)
        }
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handlePaidReservation(paySuccessEvent: PaySuccessEvent){
        try {
            when(paySuccessEvent.orderType) {
                PayOrderType.CONCERT -> reservationService.setSuccessConcertReservationStatus(ReservationSuccessStatusCommand.from(paySuccessEvent))
            }
        } catch (e: Exception) {
            logger.error("결제 완료 상태 변경 실패", e)
        }

    }

    @Async
    @EventListener
    fun handleOnReserveConcert(requestedReserveConcertEvent: RequestedReserveConcertEvent){
        reservationService.increaseConcertReservationCount(requestedReserveConcertEvent.concert)
    }
}