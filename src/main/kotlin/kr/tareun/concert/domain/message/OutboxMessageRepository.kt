package kr.tareun.concert.domain.message

import kr.tareun.concert.domain.message.model.OutboxMessage

interface OutboxMessageRepository {
    fun saveOutboxMessage(outboxMessage: OutboxMessage<*>): OutboxMessage<String>
    fun saveOutboxMessageForString(outboxMessage: OutboxMessage<String>): OutboxMessage<String>
    fun getOutboxMessageById(id: Long): OutboxMessage<String>
    fun retrieveOutboxListToNeedRetry() : List<OutboxMessage<String>>
}