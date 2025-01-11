package kr.tareun.concert.service.reservation

import kr.tareun.concert.application.reservation.ReservationService
import kr.tareun.concert.application.reservation.model.ReserveCommand
import kr.tareun.concert.domain.concert.ConcertRepository
import kr.tareun.concert.domain.concert.model.ConcertSchedule
import kr.tareun.concert.domain.reservation.ReservationRepository
import kr.tareun.concert.domain.reservation.model.Reservation
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.time.LocalDateTime

@Suppress("NonAsciiCharacters")
class ReservationServiceUnitTest {
    @Mock
    private lateinit var reservationRepository: ReservationRepository

    @Mock
    private lateinit var concertRepository: ConcertRepository

    @InjectMocks
    private lateinit var reservationService: ReservationService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `좌석을 예약할 수 있다`() {
        // given
        val seatList = listOf(1L, 2L)
        val schedule = ConcertSchedule(1, 1, 100_000, LocalDateTime.now().plusDays(1), 1, "공연장 1", 50, 48)
        val reserveCommand = ReserveCommand(1, 1, seatList)
        val reservation = reserveCommand.toReservation(schedule)
        `when`(concertRepository.getScheduleByScheduleId(reserveCommand.concertScheduleId)).thenReturn(schedule)
        `when`(reservationRepository.saveReserve(reservation)).thenReturn(reservation)

        // when
        val result = reservationService.reserveConcert(reserveCommand)

        // then
        Assertions.assertEquals(reservation.reservationId, result.reservationId)
    }
}