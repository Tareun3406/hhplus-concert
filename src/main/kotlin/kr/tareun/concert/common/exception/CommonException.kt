package kr.tareun.concert.common.exception

class CommonException(
    val errorCode: ErrorCode,
    cause: Throwable? = null
): RuntimeException(errorCode.message, cause)