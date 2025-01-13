package kr.tareun.concert.concert

import kr.tareun.concert.application.concert.ConcertService
import kr.tareun.concert.domain.concert.ConcertRepository
import kr.tareun.concert.domain.concert.model.Concert
import kr.tareun.concert.domain.concert.model.ConcertSchedule
import kr.tareun.concert.domain.concert.model.ConcertSeat
import kr.tareun.concert.domain.reservation.ReservationRepository
import kr.tareun.concert.domain.reservation.model.ReservationItem
import kr.tareun.concert.domain.reservation.model.ReservationStatusType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.time.LocalDateTime

@Suppress("NonAsciiCharacters")
class ConcertServiceUnitTest {
    @Mock
    private lateinit var reservationRepository: ReservationRepository

    @Mock
    private lateinit var concertRepository: ConcertRepository

    @InjectMocks
    private lateinit var concertService: ConcertService

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `콘서트 목록을 조회할 수 있다`() {
        // given
        val concertList = mutableListOf<Concert>()
        for (i in 1..10) {
            concertList.add(Concert(i.toLong(), "공연자 $i", "콘서트 $i"))
        }
        val pageNum = 1
        `when`(concertRepository.getConcertList(pageNum)).thenReturn(concertList)

        // when
        val result = concertService.retrieveConcertList(pageNum)

        // then
        result.forEachIndexed { index, concertInfo ->
            Assertions.assertEquals(index+1L, concertInfo.concertId)
        }
    }

    @Test
    fun `콘서트 일정을 조회할 수 있다`() {
        // given
        val scheduleList = mutableListOf<ConcertSchedule>()
        val concertId = 1L
        for (i in 1..5) {
            val schedule = ConcertSchedule(concertId, i.toLong(), 100_000, LocalDateTime.now().plusDays(i.toLong()), i.toLong(),
                "공연장소$i", 50, 10)
            scheduleList.add(schedule)
        }
        `when`(concertRepository.getConcertScheduleListByConcertId(concertId)).thenReturn(scheduleList)

        // when
        val result = concertService.retrieveConcertScheduleList(concertId)

        // then
        result.forEachIndexed { index, concertSchedule ->
            Assertions.assertEquals(index + 1L, concertSchedule.scheduleId)
        }
    }

    @Test
    fun `콘서트 좌석 목록을 조회할 수 있다`() {
        // given
        val concertId = 1L
        val scheduleId = 1L
        val schedule = ConcertSchedule(concertId, scheduleId, 100_000, LocalDateTime.now().plusDays(1), 1, "공연장 1", 50, 10)

        val seats = mutableListOf<ConcertSeat>()
        for (i in 1..50) { seats.add(ConcertSeat(i.toLong(), schedule.locationId, i)) }

        val reservationItems = mutableListOf<ReservationItem>()
        for (i in 1..10) { reservationItems.add(ReservationItem(i.toLong(), i.toLong(), scheduleId, i.toLong(), ReservationStatusType.PAID)) }

        `when`(concertRepository.getScheduleByScheduleId(scheduleId)).thenReturn(schedule)
        `when`(concertRepository.getConcertSeatListByLocationId(schedule.locationId)).thenReturn(seats)
        `when`(reservationRepository.getAllValidReservationItem(schedule.scheduleId)).thenReturn(reservationItems)


        // when
        val result = concertService.retrieveConcertSeatList(schedule.scheduleId)

        // then
        result.forEachIndexed { index, seat ->
            Assertions.assertEquals(index+1L, seat.seatId)
        }
    }
}