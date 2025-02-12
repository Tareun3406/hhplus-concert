package kr.tareun.concert.domain.reservation.model

import kr.tareun.concert.common.enums.ReservationStatusType
import kr.tareun.concert.common.exception.CommonException
import kr.tareun.concert.common.enums.ErrorCode


data class Reservation(
    val reservationId: Long = 0,
    val userId: Long,
    val concertScheduleId: Long,
    val seatIds: List<Long>,
    private var _reservationStatus: ReservationStatusType,
) {
    val reservationStatus: ReservationStatusType
        get() = _reservationStatus

    fun markedAsPaid() {
        if (this.reservationStatus == ReservationStatusType.PAID) {
            throw CommonException(ErrorCode.RESERVATION_ALREADY_PAID)
        }
        this._reservationStatus = ReservationStatusType.PAID
    }
}