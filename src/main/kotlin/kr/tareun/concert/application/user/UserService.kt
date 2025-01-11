package kr.tareun.concert.application.user

import kr.tareun.concert.application.user.model.UserResult
import kr.tareun.concert.domain.payment.PaymentRepository
import kr.tareun.concert.domain.user.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val paymentRepository: PaymentRepository,
) {
    @Transactional(readOnly = true)
    fun retrieveUserDetail(userId: Long): UserResult{
        val user = userRepository.getUserByUserId(userId)
        val point = paymentRepository.getPointByUserId(userId)
        return UserResult.from(user, point)
    }
}