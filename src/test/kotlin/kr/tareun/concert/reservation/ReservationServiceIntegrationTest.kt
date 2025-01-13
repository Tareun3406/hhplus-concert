package kr.tareun.concert.reservation

import kr.tareun.concert.application.payment.model.PayCommand
import kr.tareun.concert.application.reservation.ReservationService
import kr.tareun.concert.application.reservation.model.ReserveCommand
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.ClassPathResource
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.init.ScriptUtils
import java.sql.SQLException
import java.util.Arrays.asList

@Suppress("NonAsciiCharacters")
@SpringBootTest
class ReservationServiceIntegrationTest {
    @Autowired
    private lateinit var reservationService: ReservationService

    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate


    @BeforeEach
    fun resetDatabase() {
        val resource = ClassPathResource("init.sql")
        try {
            ScriptUtils.executeSqlScript(jdbcTemplate.dataSource!!.connection, resource)
        } catch (ex: SQLException) {
            throw RuntimeException("Database reset failed: ${ex.message}", ex)
        }
    }

    @Test
    fun `좌석을 예약할 수 있다`() {
        // given
        val reserveCommand = ReserveCommand(userId = 1, concertScheduleId = 1, seatIdList = asList(2,3))

        // when
        val result = reservationService.reserveConcert(reserveCommand)

        // then
        Assertions.assertEquals(reserveCommand.userId, result.userId)
    }

    @Test
    fun `예약한 좌석을 결제할 수 있다`() {
        // given
        val userId = 1L
        val reservationId = 1L
        val reserveCommand = PayCommand(userId, reservationId)

        // when - then
        Assertions.assertEquals(userId, reservationService.payReservation(reserveCommand).userId)
    }
}