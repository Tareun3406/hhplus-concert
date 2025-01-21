package kr.tareun.concert.queue

import kr.tareun.concert.domain.queue.model.QueueToken
import kr.tareun.concert.domain.queue.model.TokenStatusType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.*

@Suppress("NonAsciiCharacters")
class QueueTokenUnitTest {

    @Test
    fun `대기열 토큰을 만료 처리 할 수 있다`() {
        // given
        val token = QueueToken(
            tokenId = 1,
            userId = 1,
            uuid = UUID.fromString("9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d"),
            _status = TokenStatusType.PENDING,
        )

        // when
        token.markedAsExpired()

        // then
        Assertions.assertEquals(TokenStatusType.EXPIRED, token.status)
    }

    @Test
    fun `대기열 토큰을 활성화 처리 할 수 있다`() {
        // given
        val token = QueueToken(
            tokenId = 1,
            userId = 1,
            uuid = UUID.fromString("9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d"),
            _status = TokenStatusType.PENDING,
        )

        // when
        token.markedAsActivated()

        // then
        Assertions.assertEquals(TokenStatusType.ACTIVATED, token.status)
    }

    @Test
    fun `대기열 토큰의 만료 시간을 설정할 수 있다`() {
        // given
        val token = QueueToken(
            tokenId = 1,
            userId = 1,
            uuid = UUID.fromString("9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d"),
            _status = TokenStatusType.ACTIVATED,
        )

        // when
        token.updateExpiredTime(LocalDateTime.now().plusMinutes(15))

        // then
        Assertions.assertNotNull(token.expiredTime)
    }
}