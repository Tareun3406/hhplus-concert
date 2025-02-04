package kr.tareun.concert.reservation

import kr.tareun.concert.common.exception.CommonException
import kr.tareun.concert.common.enums.ErrorCode
import kr.tareun.concert.domain.reservation.model.Reservation
import kr.tareun.concert.common.enums.ReservationStatusType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

@Suppress("NonAsciiCharacters")
class ReservationUnitTest {
    @Test
    fun `예약 내역을 결제 완료 처리 할 수 있다`() {
        // given
        val reservation = Reservation(
            reservationId = 1,
            userId = 1,
            concertScheduleId = 1,
            seatIds = listOf(1, 2),
            priceAmount = 20_000,
            _reservationStatus = ReservationStatusType.NON_PAID
        )

        // when
        reservation.markedAsPaid()

        // then
        Assertions.assertEquals(ReservationStatusType.PAID, reservation.reservationStatus)
    }

    @Test
    fun `이미 결제 완료된 예약을 결제할 경우 에러코드 RESERVATION_ALREADY_PAID 을 던진다`() {
        // given
        val reservation = Reservation(
            reservationId = 1,
            userId = 1,
            concertScheduleId = 1,
            seatIds = listOf(1, 2),
            priceAmount = 20_000,
            _reservationStatus = ReservationStatusType.PAID
        )

        // when
        val thrown = Assertions.assertThrows(CommonException::class.java) {
            reservation.markedAsPaid()
        }

        // then
        Assertions.assertEquals(ErrorCode.RESERVATION_ALREADY_PAID, thrown.errorCode)
    }
}