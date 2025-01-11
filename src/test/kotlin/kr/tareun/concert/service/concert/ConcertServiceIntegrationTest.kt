package kr.tareun.concert.service.concert

import kr.tareun.concert.application.concert.ConcertService
import kr.tareun.concert.application.payment.PaymentService
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
class ConcertServiceIntegrationTest {
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
    private lateinit var concertService: ConcertService


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
    fun `콘서트를 조회할 수 있다`() {
        // given
        val pageNo = 0

        // when - then
        Assertions.assertEquals(1, concertService.retrieveConcertList(pageNo).size)
    }

    @Test
    fun `콘서트 일정을 조회할 수 있다`() {
        // given
        val concertId = 1L

        // when - then
        Assertions.assertEquals(1, concertService.retrieveConcertScheduleList(concertId).size)
    }

    @Test
    fun `콘서트 좌석을 조회 할 수 있다`() {
        // given
        val scheduleId = 1L

        // when - then
        Assertions.assertEquals(4, concertService.retrieveConcertSeatList(scheduleId).size)
    }
}