package kr.tareun.concert.application.concert

import kr.tareun.concert.application.reservation.model.ReservedConcertEvent
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class ConcertEventListener(
    private val concertService: ConcertService
) {

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun handleOnConcertReserved(reservedConcertEvent: ReservedConcertEvent) {
        concertService.handleOnConcertReserved(reservedConcertEvent)
    }
}