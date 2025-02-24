package kr.tareun.concert.infrastructure.messaging

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class MockMessageApi {
    private val logger  = LoggerFactory.getLogger(MockMessageApi::class.java)

    fun send(key: String, message: String) {
        logger.info("Sending message $key to $message")
    }
}