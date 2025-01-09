package kr.tareun.concert.infrastructure.persistence.reservation

import kr.tareun.concert.domain.reservation.ReservationRepository
import kr.tareun.concert.domain.reservation.model.Reservation
import kr.tareun.concert.domain.reservation.model.ReservationItem
import kr.tareun.concert.domain.reservation.model.ReservationStatusType
import org.springframework.stereotype.Repository

@Repository
class ReservationRepositoryImpl: ReservationRepository {
    override fun getReservationItemListByScheduleIdAndReservationStatusNot(
        scheduleId: Long,
        status: ReservationStatusType
    ): List<ReservationItem> {
        TODO("Not yet implemented")
    }

    override fun saveReserve(reservation: Reservation): Reservation {
        TODO("Not yet implemented")
    }
}