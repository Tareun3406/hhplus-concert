package kr.tareun.concert.infrastructure.persistence.user

import kr.tareun.concert.domain.user.UserRepository
import kr.tareun.concert.domain.user.model.User
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl: UserRepository {
    override fun getUserByUserId(userId: Long): User {
        TODO("Not yet implemented")
    }
}