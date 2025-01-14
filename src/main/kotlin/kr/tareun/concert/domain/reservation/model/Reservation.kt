package kr.tareun.concert.domain.reservation.model


data class Reservation(
    val reservationId: Long = 0,
    val userId: Long,
    val concertScheduleId: Long,
    val seatIds: List<Long>,
    val priceAmount: Int,
    private var _reservationStatus: ReservationStatusType,
) {
    val reservationStatus: ReservationStatusType
        get() = _reservationStatus

    fun markedAsPaid() {
        this._reservationStatus = ReservationStatusType.PAID
    }
}