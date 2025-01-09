package kr.tareun.concert.domain.reservation.model

data class ReservationItem(
    var itemId: Long = 0,
    var reservationId: Long,
    var concertScheduleId: Long,
    var seatId: Long,
    var reservationStatus: ReservationStatusType
)