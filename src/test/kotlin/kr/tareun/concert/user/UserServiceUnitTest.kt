package kr.tareun.concert.user

import kr.tareun.concert.application.user.UserService
import kr.tareun.concert.domain.payment.PaymentRepository
import kr.tareun.concert.domain.payment.model.Point
import kr.tareun.concert.domain.user.UserRepository
import kr.tareun.concert.domain.user.model.User
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@Suppress("NonAsciiCharacters")
class UserServiceUnitTest {
    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var paymentRepository: PaymentRepository

    @InjectMocks
    private lateinit var userService: UserService

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `유저 정보를 조회할 수 있다`() {
        // given
        val userId = 1L
        val user = User(userId, "test@test.com", "이용자 1")
        val point = Point(1, userId, 100_000)
        `when`(userRepository.getUserByUserId(userId)).thenReturn(user)
        `when`(paymentRepository.getPointByUserId(userId)).thenReturn(point)

        // when - then
        Assertions.assertEquals(userId, userService.retrieveUserDetail(userId).userId)
        Assertions.assertEquals(point.point, userService.retrieveUserDetail(userId).point)
    }
}