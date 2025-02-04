package kr.tareun.concert.domain.reservation.model

import kr.tareun.concert.common.enums.ReservationStatusType

data class ReservationItem(
    val itemId: Long = 0,
    val reservationId: Long,
    val concertScheduleId: Long,
    val seatId: Long,
    val reservationStatus: ReservationStatusType
)