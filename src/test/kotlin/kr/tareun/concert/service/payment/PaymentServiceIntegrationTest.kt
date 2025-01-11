package kr.tareun.concert.service.payment

import kr.tareun.concert.application.payment.PaymentService
import kr.tareun.concert.application.payment.model.ChargeCommand
import kr.tareun.concert.application.payment.model.PayCommand
import kr.tareun.concert.application.reservation.model.ReserveCommand
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.ClassPathResource
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.init.ScriptUtils
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.GenericContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName
import java.sql.SQLException

@Suppress("NonAsciiCharacters")
@Testcontainers
@SpringBootTest
class PaymentServiceIntegrationTest {
    companion object {
        private const val USERNAME = "root"
        private const val PASSWORD = "test1234"
        private const val SCHEMA_NAME = "app"

        @Container
        private val mariadb = GenericContainer(DockerImageName.parse("mariadb:11.4"))
            .withExposedPorts(3306)
            .withEnv("MARIADB_ROOT_PASSWORD", PASSWORD)
            .withEnv("MARIADB_DATABASE", SCHEMA_NAME)
            .withReuse(false)

        @JvmStatic
        @DynamicPropertySource
        fun configureProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url") {
                "jdbc:mariadb://${mariadb.host}:${mariadb.firstMappedPort}/$SCHEMA_NAME"
            }
            registry.add("spring.datasource.username") { USERNAME }
            registry.add("spring.datasource.password") { PASSWORD }
        }
    }

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
    fun `예약한 좌석을 결제할 수 있다`() {
        // given
        val userId = 1L
        val reservationId = 1L
        val reserveCommand = PayCommand(userId, reservationId)

        // when - then
        Assertions.assertEquals(userId, paymentService.payReservation(reserveCommand).userId)
    }
}