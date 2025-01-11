package kr.tareun.concert.infrastructure.persistence.user

import kr.tareun.concert.domain.user.UserRepository
import kr.tareun.concert.domain.user.model.User
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    private val jpaRepository: UserJpaRepository
): UserRepository {
    override fun getUserByUserId(userId: Long): User {
        return jpaRepository.getReferenceById(userId).toUser()
    }
}