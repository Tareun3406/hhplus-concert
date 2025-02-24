package kr.tareun.concert.domain.message

import kr.tareun.concert.domain.message.model.OutboxMessage

interface MessagePublisher {
    fun publishMock(message: OutboxMessage<String>)
    fun publishKafka(message: OutboxMessage<String>)
}