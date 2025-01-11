package kr.tareun.concert.infrastructure.persistence.user.entity

import jakarta.persistence.*
import kr.tareun.concert.domain.user.model.User

@Table(name = "user")
@Entity
class UserEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(unique = true, nullable = false)
    val email: String,

    @Column(nullable = false)
    val name: String,
) {
    fun toUser(): User {
        return User(userId = id, email = email, name = name)
    }
}