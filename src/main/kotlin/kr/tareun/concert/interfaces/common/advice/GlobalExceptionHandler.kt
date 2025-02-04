package kr.tareun.concert.interfaces.common.advice

import kr.tareun.concert.common.exception.CommonException
import kr.tareun.concert.interfaces.common.response.Response
import kr.tareun.concert.common.enums.ResponseResultType
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(CommonException::class)
    fun handleCommonException(commonException: CommonException): Response<String> {
        logger.error("error: ${commonException.errorCode.code} - ${commonException.message}", commonException)

        return Response(
            result = ResponseResultType.ERROR,
            item = commonException.errorCode.code,
            message = commonException.message,
        )
    }
}