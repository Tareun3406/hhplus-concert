package kr.tareun.concert.common.util

import java.time.LocalDateTime

class DateCalculator {
    companion object {
        fun truncatedDateTime(date: LocalDateTime, truncateMinute: Int): LocalDateTime {
            val truncatedMinutes = (date.minute / truncateMinute * truncateMinute) // ex) 수집 주기가 10분일 경우 10 이하의 단위 버림
            return date.withMinute(truncatedMinutes).withSecond(0).withNano(0)
        }
    }
}