package kr.tareun.concert.service.queue

import kr.tareun.concert.application.queue.QueueTokenScheduler
import kr.tareun.concert.domain.queue.QueueRepository
import kr.tareun.concert.domain.queue.model.QueueToken
import kr.tareun.concert.domain.queue.model.TokenStatusType
import kr.tareun.concert.infrastructure.config.QueueProperties
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.anyList
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import java.util.*

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
        val expiredTokens = listOf(QueueToken(1, 1, UUID.randomUUID()))
        val pendingTokens = listOf(QueueToken(1, 1, UUID.randomUUID()))
        `when`(queueRepository.getAllByStatusAndExpiredTimeLessThan(any(), any())).thenReturn(expiredTokens)
        `when`(queueRepository.countByStatus(TokenStatusType.ACTIVATED)).thenReturn(0)
        `when`(queueRepository.getAllByStatusOrderByIdAscWithLimit(any(), any())).thenReturn(pendingTokens)

        // when
        val result = queueTokenScheduler.scheduleActivateToken()

        // then
        Mockito.verify(queueRepository, Mockito.times(2)).saveAllQueueTokens(anyList())
    }
}