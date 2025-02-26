package kr.tareun.concert.application.reservation

import kr.tareun.concert.application.concert.model.ConcertPublishPayEventCommand
import kr.tareun.concert.application.payment.model.PaySuccessEvent
import kr.tareun.concert.application.reservation.model.ReservationSuccessStatusCommand
import kr.tareun.concert.application.reservation.model.ReservedConcertEvent
import kr.tareun.concert.common.enums.PayOrderType
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class ReservationEventListener(
    private val reservationService: ReservationService,
) {
    private val logger = LoggerFactory.getLogger(ReservationEventListener::class.java)

    @KafkaListener(topics = ["reservation.concert.reserved"], groupId = "reservation.cache.request.producer")
    fun handleIncreaseConcertReserveCount(reservedConcertEvent: ReservedConcertEvent) {
        try{
            reservationService.increaseConcertReservationCount(reservedConcertEvent)
        } catch (e: Exception) {
            logger.error("예약 횟수 캐시 저장 실패", e)
        }
    }

    @KafkaListener(topics = ["reservation.concert.reserved"], groupId = "reservation.pay.request.producer")
    fun handlePublishPayOrderEvent(reservedConcertEvent: ReservedConcertEvent) {
        try {
            reservationService.publishPayOrderEvent(ConcertPublishPayEventCommand.from(reservedConcertEvent))
        } catch (e: Exception) {
            logger.error("결제 요청 실패", e)
        }
    }

    @KafkaListener(topics = ["payment.pay.success"], groupId = "reservation.status.update")
    fun handlePaidReservation(paySuccessEvent: PaySuccessEvent){
        try {
            when(paySuccessEvent.orderType) {
                PayOrderType.CONCERT -> reservationService.updateSuccessConcertReservationStatus(ReservationSuccessStatusCommand.from(paySuccessEvent))
            }
        } catch (e: Exception) {
            logger.error("결제 완료 상태 변경 실패", e)
        }

    }
}