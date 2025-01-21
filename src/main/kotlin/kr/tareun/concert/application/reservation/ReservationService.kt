package kr.tareun.concert.application.reservation

import kr.tareun.concert.application.payment.model.PayCommand
import kr.tareun.concert.application.payment.model.PaymentHistoryResult
import kr.tareun.concert.application.reservation.model.ReserveCommand
import kr.tareun.concert.application.reservation.model.ReservationResult
import kr.tareun.concert.common.exception.CommonException
import kr.tareun.concert.common.exception.ErrorCode
import kr.tareun.concert.domain.concert.ConcertRepository
import kr.tareun.concert.domain.payment.PaymentRepository
import kr.tareun.concert.domain.payment.model.PaymentHistory
import kr.tareun.concert.domain.queue.QueueRepository
import kr.tareun.concert.domain.reservation.ReservationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReservationService(
    private val reservationRepository: ReservationRepository,
    private val concertRepository: ConcertRepository,
    private val paymentRepository: PaymentRepository,
    private val queueRepository: QueueRepository
) {
    @Transactional
    fun reserveConcert(reserveCommand: ReserveCommand): ReservationResult {
        // scheduleId 컬럼 기준 Gap Lock 설정
        reservationRepository.acquireLockByScheduleId(reserveCommand.concertScheduleId)

        // 중복 예약 체크
        val existReservedList = reservationRepository.getAllReservationItemByScheduleIdAndSeatId(reserveCommand.concertScheduleId, reserveCommand.seatIdList)
        if (existReservedList.isNotEmpty()) {
            throw CommonException(ErrorCode.RESERVATION_SEAT_ALREADY_TAKEN)
        }

        val schedule = concertRepository.getScheduleByScheduleId(reserveCommand.concertScheduleId)
        schedule.addReservedCount(reserveCommand.seatIdList.size)
        concertRepository.saveConcertSchedule(schedule)

        val newReservation = reserveCommand.toReservation(schedule)
        return ReservationResult.from(reservationRepository.saveReservation(newReservation))
    }

    @Transactional
    fun payReservation(payCommand: PayCommand): PaymentHistoryResult {
        val point = paymentRepository.getPointByUserIdForUpdate(payCommand.userId)
        val reservation = reservationRepository.getReservationByIdForUpdate(payCommand.reservationId)
        point.payPoint(reservation.priceAmount)

        val paymentHistory = PaymentHistory(
            userId = payCommand.userId,
            reservationId = reservation.reservationId,
            paidPoint = reservation.priceAmount
        )
        paymentRepository.savePoint(point)

        reservation.markedAsPaid()
        reservationRepository.saveReservation(reservation)

        val queueToken = queueRepository.getQueueByUuid(payCommand.tokenUuid)
        queueToken.markedAsExpired()
        queueRepository.saveQueueToken(queueToken)

        return PaymentHistoryResult.from(paymentRepository.savePaymentHistory(paymentHistory))
    }
}