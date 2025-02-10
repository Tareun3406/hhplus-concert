package kr.tareun.concert.domain.reservation

import kr.tareun.concert.domain.concert.model.Concert
import kr.tareun.concert.domain.reservation.model.Reservation
import kr.tareun.concert.domain.reservation.model.ReservationItem
import java.time.LocalDateTime

interface ReservationRepository {
    fun getAllValidReservationItem(scheduleId: Long): List<ReservationItem>
    fun getAllReservationItemByScheduleIdAndSeatId(scheduleId: Long, seatIds: List<Long>): List<ReservationItem>
    fun saveReservation(reservation: Reservation): Reservation
    fun getReservationByIdForUpdate(id: Long): Reservation
    fun acquireLockByScheduleId(concertScheduleId: Long)
    fun incrementCacheReservationCount(concert: Concert)
    fun getReservationRankedConcert(rankingSize: Int, referenceTime: LocalDateTime): List<Concert>
}