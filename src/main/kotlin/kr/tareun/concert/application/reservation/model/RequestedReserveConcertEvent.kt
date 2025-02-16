package kr.tareun.concert.application.reservation.model

import kr.tareun.concert.domain.concert.model.Concert

data class RequestedReserveConcertEvent(
    val concert: Concert
)
