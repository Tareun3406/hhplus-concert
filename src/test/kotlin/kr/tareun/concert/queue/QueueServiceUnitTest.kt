package kr.tareun.concert.queue

import kr.tareun.concert.application.queue.QueueService
import kr.tareun.concert.domain.queue.QueueRepository
import kr.tareun.concert.domain.queue.model.QueueToken
import kr.tareun.concert.common.enums.TokenStatusType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import java.util.*

@Suppress("NonAsciiCharacters")
class QueueServiceUnitTest {

    @Mock
    private lateinit var queueRepository: QueueRepository

    @InjectMocks
    private lateinit var queueService: QueueService

    @BeforeEach
    fun setup(){
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `대기열 토큰을 생성할 수 있다`() {
        // given
        val userId = 1L
        val token = QueueToken(1, UUID.randomUUID())
        `when`(queueRepository.saveQueueToken(any())).thenReturn(token)
        `when`(queueRepository.countQueueByIdLessThanAndStatus(token.tokenId, TokenStatusType.PENDING)).thenReturn(0)

        // when - then
        Assertions.assertNotNull(queueService.createQueueToken(userId))

    }

    @Test
    fun `대기열 토큰을 확인 할 수 있다`() {
        // given
        val uuid = UUID.randomUUID()
        val token = QueueToken(1, uuid)
        `when`(queueRepository.getQueueByUuid(uuid)).thenReturn(token)
        `when`(queueRepository.countQueueByIdLessThanAndStatus(token.tokenId, TokenStatusType.PENDING)).thenReturn(0)

        // when
        Assertions.assertEquals(uuid, queueService.getQueueToken(uuid).uuid)
    }
}