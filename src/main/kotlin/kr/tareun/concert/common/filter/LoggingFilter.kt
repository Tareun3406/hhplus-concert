package kr.tareun.concert.common.filter

import jakarta.servlet.*
import jakarta.servlet.FilterConfig
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletRequestWrapper
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.util.StopWatch
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper

@Component
class LoggingFilter : Filter {

    private val logger = LoggerFactory.getLogger(LoggingFilter::class.java)

    override fun init(filterConfig: FilterConfig?) {
    }

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val wrappedRequest = ContentCachingRequestWrapper(request as HttpServletRequest)
        val wrappedResponse = ContentCachingResponseWrapper(response as HttpServletResponse)

        val stopWatch = StopWatch()
        stopWatch.start()
        chain.doFilter(wrappedRequest, wrappedResponse)
        stopWatch.stop()

        val requestBody = String(wrappedRequest.contentAsByteArray).replace(Regex("\\s+"), "")
        val responseBody = String(wrappedResponse.contentAsByteArray)
        logger.info("request: [${wrappedRequest.method}] ${wrappedRequest.requestURI}, Body: '$requestBody'" +
                "response: Status=${wrappedResponse.status}, Body='${responseBody}', Duration = ${stopWatch.totalTimeMillis}")

        wrappedResponse.copyBodyToResponse()
    }

    override fun destroy() {
    }
}