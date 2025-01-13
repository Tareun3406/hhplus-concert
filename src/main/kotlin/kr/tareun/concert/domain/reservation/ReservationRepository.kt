package kr.tareun.concert.domain.reservation

import kr.tareun.concert.domain.reservation.model.Reservation
import kr.tareun.concert.domain.reservation.model.ReservationItem
import kr.tareun.concert.domain.reservation.model.ReservationStatusType

interface ReservationRepository {
    fun getReservationItemListByScheduleIdAndReservationStatusNot(scheduleId: Long, status: ReservationStatusType): List<ReservationItem>
    fun saveReservation(reservation: Reservation): Reservation
    fun getReservationByIdForUpdate(id: Long): Reservation
}