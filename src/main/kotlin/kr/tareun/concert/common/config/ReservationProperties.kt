package kr.tareun.concert.common.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "app.reservation")
class ReservationProperties {
    val reservationRankingCacheCycleMinute = 10
}