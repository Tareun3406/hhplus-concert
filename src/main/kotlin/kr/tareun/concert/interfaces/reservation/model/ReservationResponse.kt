package kr.tareun.concert.interfaces.reservation.model

import kr.tareun.concert.application.reservation.model.ReservationResult
import kr.tareun.concert.application.reservation.model.ReservedConcertEvent

data class ReservationResponse(
    val reservationId: Long,
    val userId: Long,
    val scheduleId: Long,
    val seatIds: List<Long>,
) {
    companion object {
        fun from(reservationResult: ReservationResult): ReservationResponse {
            return ReservationResponse(
                reservationId = reservationResult.reservationId,
                userId = reservationResult.userId,
                scheduleId = reservationResult.scheduleId,
                seatIds = reservationResult.seatIds,
            )
        }
        fun from(reservedConcertEvent: ReservedConcertEvent): ReservationResponse {
            return ReservationResponse(
                reservationId = reservedConcertEvent.reservationId,
                userId = reservedConcertEvent.userId,
                scheduleId = reservedConcertEvent.concertScheduleId,
                seatIds = reservedConcertEvent.seats
            )
        }
    }
}
