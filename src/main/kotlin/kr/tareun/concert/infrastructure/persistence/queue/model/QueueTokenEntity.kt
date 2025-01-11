package kr.tareun.concert.infrastructure.persistence.queue.model

import jakarta.persistence.*
import kr.tareun.concert.domain.queue.model.QueueToken
import kr.tareun.concert.domain.queue.model.TokenStatusType
import java.time.LocalDateTime
import java.util.UUID

@Table(name = "queue_token")
@Entity
class QueueTokenEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(nullable = false)
    val userId: Long,

    @Column(nullable = false)
    val tokenUuid: UUID,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val status: TokenStatusType,

    @Column
    val expiredTime: LocalDateTime? = null,
) {
    companion object {
        fun from(queueToken: QueueToken): QueueTokenEntity {
            return QueueTokenEntity(
                id = queueToken.tokenId,
                userId = queueToken.userId,
                tokenUuid = queueToken.uuid,
                expiredTime = queueToken.expiredTime,
                status = queueToken.status,
            )
        }
    }
    fun toQueueToken(): QueueToken {
        return QueueToken(tokenId = id, userId = userId, uuid = tokenUuid, status = status, expiredTime = expiredTime)
    }
}