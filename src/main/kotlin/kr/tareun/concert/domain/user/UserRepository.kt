package kr.tareun.concert.domain.user

import kr.tareun.concert.domain.user.model.User

interface UserRepository {
    fun getUserByUserId(userId: Long): User
}