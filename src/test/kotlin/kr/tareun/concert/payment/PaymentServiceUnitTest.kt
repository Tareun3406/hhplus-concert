package kr.tareun.concert.payment

import kr.tareun.concert.application.payment.PaymentService
import kr.tareun.concert.application.payment.model.ChargeCommand
import kr.tareun.concert.domain.payment.PaymentRepository
import kr.tareun.concert.domain.payment.model.Point
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.context.ApplicationEventPublisher

@Suppress("NonAsciiCharacters")
class PaymentServiceUnitTest {
    @Mock
    private lateinit var paymentRepository: PaymentRepository

    @Mock
    private lateinit var applicationEventPublisher: ApplicationEventPublisher

    @InjectMocks
    private lateinit var paymentService: PaymentService

    @BeforeEach
    fun setup(){
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `남은 잔액을 조회할 수 있다`() {
        // given
        val userId = 1L
        val point = Point(1, userId, 100_000)
        `when`(paymentRepository.getPointByUserId(userId)).thenReturn(point)

        // when - then
        Assertions.assertEquals(userId, paymentService.retrievePoint(userId).userId)
    }

    @Test
    fun `잔액을 충전할 수 있다`() {
        // given
        val userId = 1L
        val chargeAmount = 50_000
        val chargeRequest = ChargeCommand(userId, chargeAmount)
        val basePoint = 100_000
        val point = Point(1, userId, basePoint)
        `when`(paymentRepository.getPointByUserIdForUpdate(userId)).thenReturn(point)
        `when`(paymentRepository.savePoint(point)).thenReturn(point)

        // when
        val result = paymentService.chargePoint(chargeRequest)

        // then
        Assertions.assertEquals(userId, result.userId)
        Assertions.assertEquals(basePoint + chargeAmount, result.point)
    }
}