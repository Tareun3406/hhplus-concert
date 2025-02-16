package kr.tareun.concert.queue

import kr.tareun.concert.application.queue.QueueTokenScheduler
import kr.tareun.concert.domain.queue.QueueRepository
import kr.tareun.concert.common.config.properties.QueueProperties
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.verify

@Suppress("NonAsciiCharacters")
class QueueSchedulerUnitTest {

    @Mock
    private lateinit var queueRepository: QueueRepository
    private lateinit var queueProperties: QueueProperties
    private lateinit var queueTokenScheduler: QueueTokenScheduler

    @BeforeEach
    fun setup(){
        MockitoAnnotations.openMocks(this)
        queueProperties = QueueProperties()
        queueTokenScheduler = QueueTokenScheduler(queueRepository, queueProperties)
    }

    @Test
    fun `주기적으로 토큰을 활성화 하고 만료시킬 수 있다`() {
        //given
        `when`(queueRepository.countActivatedToken()).thenReturn(1)

        // when
        val result = queueTokenScheduler.scheduleActivateToken()

        // then
        verify(queueRepository).removeExpiredTokens(any())
        verify(queueRepository).activateToken(any(), any())
    }
}