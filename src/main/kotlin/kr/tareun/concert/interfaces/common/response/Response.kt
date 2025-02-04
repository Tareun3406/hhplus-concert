package kr.tareun.concert.interfaces.common.response

import kr.tareun.concert.common.enums.ResponseResultType

data class Response<T>(
    val result: ResponseResultType,
    val item: T? = null,
    val message: String? = null
)