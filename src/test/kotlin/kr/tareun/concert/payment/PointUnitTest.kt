package kr.tareun.concert.payment

import kr.tareun.concert.common.enums.ErrorCode
import kr.tareun.concert.common.exception.CommonException
import kr.tareun.concert.domain.payment.model.Point
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

@Suppress("NonAsciiCharacters")
class PointUnitTest {

    @Test
    fun `잔액을 충전 할 수 있다`() {
        // when
        val point = Point(1, 1, 10_000)

        // when
        point.chargePoint(10_000)

        // then
        Assertions.assertEquals(20_000, point.point)
    }

    @Test
    fun `잔액을 사용할 수 있다`() {
        // given
        val point = Point(1, 1, 10_000)

        // when
        point.payPoint(10_000)

        // then
        Assertions.assertEquals(0, point.point)
    }

    @Test
    fun `잔액이 부족할 경우 에러코드 PAYMENT_NOT_ENOUGH_POINT 를 던진다`() {
        // given
        val point = Point(1, 1, 10_000)

        // when - then
        val thrown = Assertions.assertThrows(CommonException::class.java) {
            point.payPoint(10_001)
        }
        Assertions.assertEquals(ErrorCode.PAYMENT_NOT_ENOUGH_POINT, thrown.errorCode)

    }
}