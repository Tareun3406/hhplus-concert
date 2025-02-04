package kr.tareun.concert.infrastructure.persistence.concert

import kr.tareun.concert.domain.concert.ConcertRepository
import kr.tareun.concert.domain.concert.model.Concert
import kr.tareun.concert.domain.concert.model.ConcertSchedule
import kr.tareun.concert.domain.concert.model.ConcertSeat
import kr.tareun.concert.common.config.ConcertProperties
import kr.tareun.concert.infrastructure.persistence.concert.entity.ConcertScheduleEntity
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository

@Repository
class ConcertRepositoryImpl(
    private val concertJpaRepository: ConcertJpaRepository,
    private val concertScheduleJpaRepository: ConcertScheduleJpaRepository,
    private val locationJpaRepository: LocationJpaRepository,
    private val seatJpaRepository: SeatJpaRepository,

    private val concertProperties: ConcertProperties
): ConcertRepository {
    override fun getConcertById(id: Long): Concert {
        return concertJpaRepository.getReferenceById(id).toConcert()
    }

    override fun getConcertList(pageNumber: Int): List<Concert> {
        return concertJpaRepository.findAll(PageRequest.of(pageNumber, concertProperties.concertListPageSize)).get().map { it.toConcert() }.toList()
    }

    override fun saveConcertSchedule(schedule: ConcertSchedule): ConcertSchedule {
        val scheduleEntity = ConcertScheduleEntity.from(schedule)
        val location = locationJpaRepository.getReferenceById(schedule.locationId)
        return concertScheduleJpaRepository.save(scheduleEntity).toConcertSchedule(location)
    }

    override fun getConcertScheduleListByConcertId(concertId: Long): List<ConcertSchedule> {
        return concertScheduleJpaRepository.findAllSchedule()
    }

    override fun getScheduleByScheduleId(scheduleId: Long): ConcertSchedule {
        val schedule = concertScheduleJpaRepository.getReferenceById(scheduleId)
        val location = locationJpaRepository.getReferenceById(schedule.locationId)
        return schedule.toConcertSchedule(location)
    }

    override fun getConcertSeatListByLocationId(locationId: Long): List<ConcertSeat> {
        return seatJpaRepository.findAllByLocationId(locationId).map { it.toConcertSeat() }
    }
}