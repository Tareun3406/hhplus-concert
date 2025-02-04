package kr.tareun.concert.infrastructure.persistence.user

import kr.tareun.concert.infrastructure.persistence.user.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserJpaRepository: JpaRepository<UserEntity, Long>