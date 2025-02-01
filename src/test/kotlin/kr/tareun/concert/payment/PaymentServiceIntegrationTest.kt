package kr.tareun.concert.payment

import kr.tareun.concert.TestcontainersConfiguration
import kr.tareun.concert.application.payment.PaymentService
import kr.tareun.concert.application.payment.model.ChargeCommand
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.core.io.ClassPathResource
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.init.ScriptUtils
import java.sql.SQLException
import java.util.concurrent.CompletableFuture

@Suppress("NonAsciiCharacters")
@Import(TestcontainersConfiguration::class)
@SpringBootTest
class PaymentServiceIntegrationTest {
    @Autowired
    private lateinit var paymentService: PaymentService


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
    fun `잔액을 조회할 수 있다`() {
        // given
        val userId = 1L

        // when - then
        Assertions.assertEquals(userId, paymentService.retrievePoint(userId).userId)
    }


    @Test
    fun `잔액을 충전할 수 있다`() {
        // given
        val userId = 1L
        val chargeCommand = ChargeCommand(userId, 10000)

        // when - then
        Assertions.assertEquals(userId, paymentService.chargePoint(chargeCommand).userId)
    }

    @Test
    fun `잔액 충전 요청이 동시에 들어올 경우 순차적으로 처리된다`() {
        // given
        val userId = 1L
        val chargeCommand = ChargeCommand(userId, 10000)

        // when
        val futures = (1..10).map {
            CompletableFuture.supplyAsync {
                try {
                    paymentService.chargePoint(chargeCommand)
                    true
                } catch (e: Exception) {
                    false
                }
            }
        }
        val results = futures.map { it.join() }

        Assertions.assertEquals(10, results.size)
        Assertions.assertEquals(110000, paymentService.retrievePoint(userId).point)
    }
}