package kr.tareun.concert.application.reservation.model

import kr.tareun.concert.domain.reservation.model.Reservation
import kr.tareun.concert.common.enums.ReservationStatusType
import kr.tareun.concert.interfaces.reservation.model.ReserveRequest

data class ReserveCommand(
    val concertScheduleId: Long,
    val userId: Long,
    val seatIdList: List<Long>
) {
    companion object {
        fun from(reserveRequest: ReserveRequest): ReserveCommand {
            return ReserveCommand(
                concertScheduleId = reserveRequest.concertScheduleId,
                userId = reserveRequest.userId,
                seatIdList = reserveRequest.seatIdList
            )
        }
    }
    fun toReservation(): Reservation {
        return Reservation(
            userId = userId,
            concertScheduleId = concertScheduleId,
            seatIds = seatIdList,
            _reservationStatus = ReservationStatusType.NON_PAID
        )
    }
}
