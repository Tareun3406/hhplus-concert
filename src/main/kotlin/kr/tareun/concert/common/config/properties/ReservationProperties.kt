package kr.tareun.concert.common.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "app.reservation")
class ReservationProperties {
    val reservationRankingCacheCycleMinute = 10
}