package kr.tareun.concert.application.reservation.model

import kr.tareun.concert.domain.concert.model.ConcertSchedule
import kr.tareun.concert.domain.reservation.model.Reservation
import kr.tareun.concert.domain.reservation.model.ReservationStatusType
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
    fun toReservation(scheduleInfo: ConcertSchedule): Reservation {
        return Reservation(
            userId = userId,
            concertScheduleId = concertScheduleId,
            seatIds = seatIdList,
            priceAmount = scheduleInfo.ticketPrice * seatIdList.size,
            _reservationStatus = ReservationStatusType.NON_PAID
        )
    }
}
