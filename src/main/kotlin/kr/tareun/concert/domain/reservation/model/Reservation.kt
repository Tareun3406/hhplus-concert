package kr.tareun.concert.domain.reservation.model

import java.lang.RuntimeException


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
        if (this.reservationStatus == ReservationStatusType.PAID) {
            throw RuntimeException("이미 결제 완료된 예약입니다.")
        }
        this._reservationStatus = ReservationStatusType.PAID
    }
}