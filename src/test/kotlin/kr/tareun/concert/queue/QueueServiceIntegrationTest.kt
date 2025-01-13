package kr.tareun.concert.queue

import kr.tareun.concert.application.queue.QueueService
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

@Suppress("NonAsciiCharacters")
@SpringBootTest
class QueueServiceIntegrationTest {
    @Autowired
    private lateinit var queueService: QueueService


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
    fun `대기열 토큰을 발급 할 수 있다`() {
        // given
        val userId = 1L

        // when - then
        Assertions.assertEquals(userId, queueService.createQueueToken(userId).userId)
    }

    @Test
    fun `대기열 토큰을 확인 할 수 있다`() {
        val uuid = UUID.fromString("9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d")

        // when - then
        Assertions.assertEquals(uuid, queueService.getQueueToken(uuid).uuid)
    }
}