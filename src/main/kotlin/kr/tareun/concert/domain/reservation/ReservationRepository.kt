package kr.tareun.concert.domain.reservation

import kr.tareun.concert.domain.reservation.model.Reservation
import kr.tareun.concert.domain.reservation.model.ReservationItem

interface ReservationRepository {
    fun getAllValidReservationItem(scheduleId: Long): List<ReservationItem>
    fun saveReservation(reservation: Reservation): Reservation
    fun getReservationByIdForUpdate(id: Long): Reservation
}