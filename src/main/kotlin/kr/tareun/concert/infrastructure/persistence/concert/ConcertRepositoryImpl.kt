package kr.tareun.concert.infrastructure.persistence.concert

import kr.tareun.concert.domain.concert.ConcertRepository
import kr.tareun.concert.domain.concert.model.Concert
import kr.tareun.concert.domain.concert.model.ConcertSchedule
import kr.tareun.concert.domain.concert.model.ConcertSeat
import org.springframework.stereotype.Repository

@Repository
class ConcertRepositoryImpl: ConcertRepository {
    override fun getConcertList(pageNumber: Int): List<Concert> {
        TODO("Not yet implemented")
    }

    override fun getConcertScheduleListByConcertId(concertId: Long): List<ConcertSchedule> {
        TODO("Not yet implemented")
    }

    override fun getScheduleByScheduleId(scheduleId: Long): ConcertSchedule {
        TODO("Not yet implemented")
    }

    override fun getConcertSeatListByLocationId(locationId: Long): List<ConcertSeat> {
        TODO("Not yet implemented")
    }
}