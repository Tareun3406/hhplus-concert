package kr.tareun.concert.application.concert

import kr.tareun.concert.application.concert.model.ConcertPublishPayEventCommand
import kr.tareun.concert.application.reservation.model.ReservedConcertEvent
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class ConcertEventListener(
    private val concertService: ConcertService
) {

    @Async // 캐시 저장 로직은 메인 프로세스에 영향을 주지 않음.  // todo 재시도 로직 및 로깅
    @EventListener
    fun handleIncreaseConcertReserveCount(reservedConcertEvent: ReservedConcertEvent) {
        concertService.increaseConcertReserveCount(reservedConcertEvent.concertScheduleId)
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun handlePublishPayOrderEvent(reservedConcertEvent: ReservedConcertEvent) {
        concertService.publishPayOrderEvent(ConcertPublishPayEventCommand.from(reservedConcertEvent))
    }
}