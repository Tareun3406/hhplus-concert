package kr.tareun.concert.reservation

import kr.tareun.concert.application.payment.model.PayCommand
import kr.tareun.concert.application.reservation.ReservationService
import kr.tareun.concert.application.reservation.model.ReserveCommand
import kr.tareun.concert.domain.concert.model.Concert
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.redisson.api.RedissonClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.ClassPathResource
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.init.ScriptUtils
import java.sql.SQLException
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.CompletableFuture

@Suppress("NonAsciiCharacters")
@SpringBootTest
class ReservationServiceIntegrationTest {
    @Autowired
    private lateinit var reservationService: ReservationService

    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    @Autowired
    private lateinit var redissonClient: RedissonClient

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
        val reserveCommand = ReserveCommand(userId = 1, concertScheduleId = 1, seatIdList = listOf(2,3))

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

    @Test
    fun `콘서트의 예약이 많은 순위를 조회할 수 있다`() {
        // given
        var cacheCollectedTime =  LocalDateTime.now().minusMinutes(10)
        val truncatedMinutes = cacheCollectedTime.minute / 10 * 10
        cacheCollectedTime = cacheCollectedTime.withMinute(truncatedMinutes).withSecond(0).withNano(0)
        val cacheData = redissonClient.getScoredSortedSet<Concert>("concert-rank:${cacheCollectedTime}")

        val concertMap = mapOf(
            Pair(Concert(1, "콘서트 1 - 4순위", "가수 1"), 100.0),
            Pair(Concert(2, "콘서트 2 - 5순위", "가수 2"), 20.0),
            Pair(Concert(3, "콘서트 3 - 3순위", "가수 3"), 140.0),
            Pair(Concert(4, "콘서트 4 - 2순위", "가수 4"), 200.0),
            Pair(Concert(5, "콘서트 5 - 1순위", "가수 5"), 300.0),
        )
        cacheData.addAll(concertMap)

        // when
        val result = reservationService.getReservationRankedList(5)

        // then
        Assertions.assertEquals(5L, result[0].concertId)
        Assertions.assertEquals(4L, result[1].concertId)
        Assertions.assertEquals(3L, result[2].concertId)
        Assertions.assertEquals(1L, result[3].concertId)
        Assertions.assertEquals(2L, result[4].concertId)
    }
}