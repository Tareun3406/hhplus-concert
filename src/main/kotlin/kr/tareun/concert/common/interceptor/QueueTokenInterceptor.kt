package kr.tareun.concert.common.interceptor

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.tareun.concert.application.queue.QueueService
import kr.tareun.concert.common.enums.ErrorCode
import kr.tareun.concert.common.enums.TokenStatusType
import kr.tareun.concert.common.exception.CommonException

import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import java.util.*

@Component
class QueueTokenInterceptor(
    private val queueService: QueueService,
): HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        try{
            val queueToken = request.getHeader("Queue-Token")
            val tokenUuid = UUID.fromString(queueToken)

            return queueService.getQueueToken(tokenUuid).status == TokenStatusType.ACTIVATED
        } catch (commonException: CommonException) {
            if (commonException.errorCode != ErrorCode.QUEUE_TOKEN_EXPIRED) { throw commonException }
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            return false
        } catch (e: IllegalArgumentException) {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            return false
        } catch (e: RuntimeException) {
            response.status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            return false
        }

    }
}