package kr.tareun.concert.domain.message

import kr.tareun.concert.domain.message.model.OutboxMessage

interface MessagePublisher {
    fun publish(message: OutboxMessage<String>)
}