package kr.tareun.concert.interfaces.reservation.model

data class ReserveRequest(
    val concertScheduleId: Long,
    val userId: Long,
    val seatIdList: List<Long>
)
