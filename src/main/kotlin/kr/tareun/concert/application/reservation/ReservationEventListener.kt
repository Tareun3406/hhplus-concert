package kr.tareun.concert.application.reservation

import kr.tareun.concert.application.concert.model.ConcertPublishPayEventCommand
import kr.tareun.concert.application.payment.model.PaySuccessEvent
import kr.tareun.concert.application.reservation.model.ReservationSuccessStatusCommand
import kr.tareun.concert.application.reservation.model.ReservedConcertEvent
import kr.tareun.concert.common.enums.PayOrderType
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class ReservationEventListener(
    private val reservationService: ReservationService,
) {
    private val logger = LoggerFactory.getLogger(ReservationEventListener::class.java)

    @Async // 캐시 저장 로직은 메인 프로세스에 영향을 주지 않음.  // todo 재시도 로직 및 로깅
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handleIncreaseConcertReserveCount(reservedConcertEvent: ReservedConcertEvent) {
        try{
            reservationService.increaseConcertReservationCount(reservedConcertEvent)
        } catch (e: Exception) {
            logger.error("예약 횟수 캐시 저장 실패", e)
        }
    }

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
}