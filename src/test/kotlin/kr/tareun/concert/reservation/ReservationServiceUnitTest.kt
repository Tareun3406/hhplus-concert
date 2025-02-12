package kr.tareun.concert.reservation

import kr.tareun.concert.application.payment.model.PayCommand
import kr.tareun.concert.application.reservation.ReservationService
import kr.tareun.concert.application.reservation.model.ReserveCommand
import kr.tareun.concert.domain.concert.ConcertRepository
import kr.tareun.concert.domain.concert.model.ConcertSchedule
import kr.tareun.concert.domain.payment.PaymentRepository
import kr.tareun.concert.domain.payment.model.PaymentHistory
import kr.tareun.concert.domain.payment.model.Point
import kr.tareun.concert.domain.queue.QueueRepository
import kr.tareun.concert.domain.queue.model.QueueToken
import kr.tareun.concert.domain.reservation.ReservationRepository
import kr.tareun.concert.domain.reservation.model.Reservation
import kr.tareun.concert.common.enums.ReservationStatusType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import java.time.LocalDateTime
import java.util.*

@Suppress("NonAsciiCharacters")
class ReservationServiceUnitTest {
    @Mock
    private lateinit var reservationRepository: ReservationRepository

    @Mock
    private lateinit var concertRepository: ConcertRepository

    @Mock
    private lateinit var paymentRepository: PaymentRepository

    @Mock
    private lateinit var queueRepository: QueueRepository

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
        val reservation = reserveCommand.toReservation()
        `when`(concertRepository.getScheduleByScheduleId(reserveCommand.concertScheduleId)).thenReturn(schedule)
        `when`(reservationRepository.saveReservation(reservation)).thenReturn(reservation)

        // when
        val result = reservationService.reserveConcert(reserveCommand)

        // then
        Assertions.assertEquals(reservation.reservationId, result.reservationId)
    }

    @Test
    fun `예약된 좌석을 결제할 수 있다`() {
        // given
        val userId = 1L
        val basePoint = 100_000
        val reservationId = 1L
        val point = Point(1, userId, basePoint)
        val reservation = Reservation(reservationId, userId, 1, listOf(1), ReservationStatusType.NON_PAID)
        val paymentHistory = PaymentHistory(1, userId, reservationId, 10_000)
        val tokenUuid = UUID.fromString("9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d")
        val payRequest = PayCommand(userId, reservationId, tokenUuid)
        val queueToken = QueueToken(1, tokenUuid)

        `when`(paymentRepository.getPointByUserIdForUpdate(userId)).thenReturn(point)
        `when`(reservationRepository.getReservationByIdForUpdate(reservationId)).thenReturn(reservation)
        `when`(paymentRepository.savePaymentHistory(any())).thenReturn(paymentHistory)

        // when
        val result = reservationService.payReservation(payRequest)

        // then
        Assertions.assertEquals(userId, result.userId)
        verify(queueRepository).removeActivatedQueueToken(queueToken.uuid)
    }
}