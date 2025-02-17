package kr.tareun.concert.infrastructure.persistence.message.entity

import jakarta.persistence.*
import kr.tareun.concert.common.enums.MessageStatus
import kr.tareun.concert.domain.message.model.OutboxMessage
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Table(name = "outbox_message")
@Entity
class OutboxMessageEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column
    var topic: String,

    @Column
    var message: String,

    @Column
    @Enumerated(EnumType.STRING)
    var status: MessageStatus,

    @Column
    @CreationTimestamp
    var createdAt: LocalDateTime = LocalDateTime.now(),
) {
    companion object {
        fun fromStringOutboxMessage(outboxMessage: OutboxMessage<String>): OutboxMessageEntity {
            return OutboxMessageEntity(
                outboxMessage.id,
                outboxMessage.topic,
                outboxMessage.message,
                outboxMessage.status,
                outboxMessage.createdAt
            )
        }
    }

    fun toOutboxMessage(): OutboxMessage<String> {
        return OutboxMessage(
            id = id,
            topic = topic,
            message = message,
            _status = status,
            createdAt = createdAt
        )
    }
}