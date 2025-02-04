package kr.tareun.concert.common.interceptor

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.tareun.concert.domain.queue.QueueRepository
import kr.tareun.concert.common.enums.TokenStatusType
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import java.util.*

@Component
class QueueTokenInterceptor(
    private val queueRepository: QueueRepository
): HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        try{
            val queueToken = request.getHeader("Queue-Token")
            val tokenUuid = UUID.fromString(queueToken)

            return queueRepository.getQueueByUuid(tokenUuid).status == TokenStatusType.ACTIVATED
        } catch (e: IllegalArgumentException) {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            return false
        } catch (e: RuntimeException) {
            response.status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            return false
        }

    }
}