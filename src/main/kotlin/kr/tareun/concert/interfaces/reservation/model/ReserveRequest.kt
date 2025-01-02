package kr.tareun.concert.interfaces.reservation.model

data class ReserveRequest(
    val reservationId: Long,
    val userId: Long,
    val items: List<Item>
) {
    data class Item(
        val concertScheduleId: Long,
        val seatId: Long
    )
}
