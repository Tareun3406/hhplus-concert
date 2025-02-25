import http from 'k6/http';
import { sleep } from 'k6';

export let options = {
    vus: 100,
    duration: '1m'
}

export default function () {
    const api = "http://hhconcert.tareun.kr"

    const moveMaxPage = Math.floor(Math.random() * 3) // 몇번째 페이지 까지 조회할지 랜덤 선택
    let concertList;
    for (let i = 0; i <= moveMaxPage; i ++) {
        concertList = http.get(`${api}/concerts?pageNumber=${i}`);
        sleep(5)
    }
}