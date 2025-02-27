import http from 'k6/http';
import { sleep } from 'k6';

export let options = {
    vus: 500,
    // Iterations: 1
    duration: '5m'
}

export default function () {
    const api = "http://hhconcert.tareun.kr"
    const userId = Math.floor(Math.random()* 99999)+1 // 1 ~ 10만

    // 콘서트 목록 조회
    const concertList =  http.get(`${api}/concerts?pageNumber=${i}`).json(Math.floor(Math.random() * 100));

    // 콘서트 선택(스케줄 목록 조회)
    const selectConcert = concertList.item[Math.floor(Math.random() * concertList.item.length)];
    const scheduleList = http.get(`${api}/concerts/${selectConcert.concertId}/schedules`).json().item.filter((schedule) => schedule.canReserve);
    sleep(5);

    // 스케줄 선택(좌석 목록 조회)
    const selectSchedule = scheduleList[Math.floor(Math.random() * scheduleList.length)];
    const seatList = http.get(`${api}/concerts/${selectSchedule.concertId}/schedules/${selectSchedule.scheduleId}`).json().item.filter((seat) => seat.canReserve);
    sleep(10);

    // 예약 (좌석 선택)
    const selectSeat = seatList[Math.floor((Math.random() * seatList.length))];
    const reservationBody = {
        "concertScheduleId": selectSchedule.scheduleId,
        "userId": userId,
        "seatIdList": [
            selectSeat.seatId
        ]
    }
    http.post(`${api}/reservation/concerts`, JSON.stringify(reservationBody), {
        headers: { 'Content-Type': 'application/json' },
    });
}