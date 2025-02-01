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
import java.util.*
import java.util.Arrays.asList
import java.util.concurrent.CompletableFuture

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
        val tokenUuid = UUID.fromString("9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d")
        val reserveCommand = PayCommand(userId, reservationId, tokenUuid)

        // when - then
        Assertions.assertEquals(userId, reservationService.payReservation(reserveCommand).userId)
    }

    @Test
    fun `하나의 좌석에 대해 여러번 예약 요청이 들어왔을 경우 하나만 성공한다`() {
        // given
        val reserveCommand = ReserveCommand(1, 1, listOf(4L))

        // when
        val futures = (1..10).map {
            CompletableFuture.supplyAsync {
                try {
                    reservationService.reserveConcert(reserveCommand)
                    true
                } catch (e: Exception) {
                    false
                }
            }
        }
        val results = futures.map { it.join() }

        // then
        val successfulReservations = results.count { it }
        Assertions.assertEquals(1, successfulReservations)
    }

    @Test
    fun `하나의 예약에 대해 여러번 결제 요청이 들어왔을 경우 하나만 성공한다`() {
        // given
        val payCommand = PayCommand(userId = 1, reservationId = 1, tokenUuid = UUID.fromString("9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d"))

        // when
        val futures = (1..10).map {
            CompletableFuture.supplyAsync {
                try {
                    reservationService.payReservation(payCommand)
                    true
                } catch (e: Exception) {
                    false
                }
            }
        }
        val results = futures.map { it.join() }

        // then
        val successfulReservations = results.count { it }
        Assertions.assertEquals(1, successfulReservations)
    }
}