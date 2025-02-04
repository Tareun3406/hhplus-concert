package kr.tareun.concert.common.exception

import kr.tareun.concert.common.enums.ErrorCode

class CommonException(
    val errorCode: ErrorCode,
    cause: Throwable? = null
): RuntimeException(errorCode.message, cause)