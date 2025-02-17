package kr.tareun.concert.application.concert

import kr.tareun.concert.application.concert.model.ConcertPublishPayEventCommand
import kr.tareun.concert.application.reservation.model.ReservedConcertEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class ConcertEventListener(
    private val concertService: ConcertService
) {
    private val logger: Logger = LoggerFactory.getLogger(ConcertEventListener::class.java)

    @Async // 캐시 저장 로직은 메인 프로세스에 영향을 주지 않음.  // todo 재시도 로직 및 로깅
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handleIncreaseConcertReserveCount(reservedConcertEvent: ReservedConcertEvent) {
        try{
            concertService.increaseConcertReserveCount(reservedConcertEvent.concertScheduleId)
        } catch (e: Exception) {
            logger.error("예약 횟수 캐시 저장 실패", e)
        }
    }
}