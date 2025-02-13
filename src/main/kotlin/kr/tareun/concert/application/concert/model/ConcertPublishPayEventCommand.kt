package kr.tareun.concert.application.concert.model

import kr.tareun.concert.application.reservation.model.ReservedConcertEvent

data class ConcertPublishPayEventCommand(
    val concertScheduleId: Long,
    val seats: List<Long>,
    val userId: Long,
    val reservationId: Long
) {
    companion object {
        fun from(reservedConcertEvent: ReservedConcertEvent): ConcertPublishPayEventCommand {
            return ConcertPublishPayEventCommand(
                concertScheduleId = reservedConcertEvent.concertScheduleId,
                seats = reservedConcertEvent.seats,
                userId = reservedConcertEvent.userId,
                reservationId = reservedConcertEvent.reservationId,
            )
        }
    }
}