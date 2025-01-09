package kr.tareun.concert.application.reservation.model

import kr.tareun.concert.interfaces.reservation.model.ReserveRequest

data class ReserveCommand(
    val concertScheduleId: Long,
    val userId: Long,
    val seats: List<Int>
) {
    companion object {
        fun from(reserveRequest: ReserveRequest): ReserveCommand {
            return ReserveCommand(
                concertScheduleId = reserveRequest.concertScheduleId,
                userId = reserveRequest.userId,
                seats = reserveRequest.seats
            )
        }
    }
}
