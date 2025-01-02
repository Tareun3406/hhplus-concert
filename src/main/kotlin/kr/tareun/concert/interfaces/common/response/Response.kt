package kr.tareun.concert.interfaces.common.response

data class Response<T>(
    val result: ResponseResultType,
    val item: T? = null,
    val message: String? = null
)