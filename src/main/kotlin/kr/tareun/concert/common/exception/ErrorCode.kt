package kr.tareun.concert.common.exception

enum class ErrorCode(val code: String, val message: String) {
    // concert 관련 (prefix: CC)
    CONCERT_SCHEDULE_CAPACITY_EXCEEDED("CC01", "해당 일정의 예약 가능 인원 수를 초과하였습니다."),

    // payment 관련 (prefix: PM)
    PAYMENT_NOT_ENOUGH_POINT("PM01", "잔액이 부족합니다."),

    // reservation 관련 (prefix: RS)
    RESERVATION_SEAT_ALREADY_TAKEN("RS01", "이미 예약된 좌석이 있습니다."),
    RESERVATION_ALREADY_PAID("RS02", "이미 결제 완료된 예약입니다."),

    // queue 관련 (prefix: QU)
    QUEUE_TOKEN_EXPIRED("QU01", "만료된 토큰입니다.")
}