package kr.tareun.concert.domain.concert

import kr.tareun.concert.domain.concert.model.Concert
import kr.tareun.concert.domain.concert.model.ConcertSchedule
import kr.tareun.concert.domain.concert.model.ConcertSeat

interface ConcertRepository {
    fun getConcertList(pageNumber: Int): List<Concert>
    fun saveConcertSchedule(schedule: ConcertSchedule): ConcertSchedule
    fun getConcertScheduleListByConcertId(concertId: Long): List<ConcertSchedule>
    fun getScheduleByScheduleId(scheduleId: Long): ConcertSchedule
    fun getConcertSeatListByLocationId(locationId: Long): List<ConcertSeat>
}