package kr.tareun.concert.user

import kr.tareun.concert.application.user.UserService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.ClassPathResource
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.init.ScriptUtils
import java.sql.SQLException

@Suppress("NonAsciiCharacters")
@SpringBootTest
class UserServiceIntegrationTest {
    @Autowired
    private lateinit var userService: UserService

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
    fun `유저 정보를 조회할 수 있다`() {
        // given
        val userId = 1L

        // when - then
        Assertions.assertEquals(userId, userService.retrieveUserDetail(userId).userId)
    }
}