# 인덱스 추가 및 쿼리 개선 성능 비교
캐시 등의 예상치 못한 변수에 대비하여 인덱스 추가 전/후 각 두번씩 조회하여 비교. 

### seat 테이블 - locationId (외래키)
인덱스 적용 전(Location 1만, Seat 50만)
- 1차
![img.png](/docs/img/index/seat_before1.png)
- 2차
![img.png](/docs/img/index/seat_before2.png)


인덱스 적용 후
- 1차
![img.png](/docs/img/index/seat_after1.png)
- 2차
![img.png](/docs/img/index/seat_after2.png)

### point 테이블 - userId (외래키)
인덱스 적용 전(레코드 10만)
- 1차
![img.png](/docs/img/index/point_before1.png)
- 2차
![img.png](/docs/img/index/point_before2.png)

인덱스 적용 후
- 1차
![img.png](/docs/img/index/point_after1.png)
- 2차
![img.png](/docs/img/index/point_after2.png)

### reservation_item 테이블
#### 현재 사용 쿼리 목록 (select ~ from 절 생략)
```
외래키 조회 1 : WHERE reservationId = :n1
외래키 조회 2: WHERE concertScheduleId = :n1

이미 예약이 있는 좌석 리스트 조회 - 제약조건 1번 쿼리
  WHERE concertScheduleId = :n1 AND (reservationStatus = :s OR expiredAt > :date)

예약요청이 들어온 좌석이 예약 가능한지 확인 - 제약조건 2번 쿼리
  WHERE concertScheduleId = :n1 AND seatId IN :ids AND (reservationStatus = :s OR expiredAt > :date)
```

#### 분석 및 개선 사항
- 제약조건 1번 쿼리와 제약조건 2번 쿼리는 ConcertScheduleId 를 첫번째 정렬 조건으로 사용할 것이기 때문에
외래키 조회 2번 쿼리는 별도의 Index를 생성하지 않아도 된다.

- 제약조건 2번 쿼리의 조건 순서를 변경하면 제약조건 1번과 Index를 공유할 수 있다.

```
전: WHERE concertScheduleId = :n1 AND seatId IN :ids AND(reservationStatus = :s OR expiredAt > :date)
후: WHERE concertScheduleId = :n1 AND (reservationStatus = :s OR expiredAt > :date) AND seatId IN :ids
Index: (concertScheduleId, reservationStatus, expiredAt, seatId)
```

따라서 필요 인덱스는 총 두개. (reservationId), (concertScheduleId, reservationStatus, expiredAt, seatId)

#### 성능 개선 비교
외래키 조회 인덱스는 앞선 테스트와 비슷하니 생략.

이미 예약이 있는 좌석 리스트 조회 - 제약조건 1번 쿼리
인덱스 적용 전
- 1차
  ![img.png](/docs/img/index/reserve_list_before1.png)
- 2차
  ![img.png](/docs/img/index/reserve_list_before2.png)

적용 후
- 1차
  ![img.png](/docs/img/index/reserve_list_after1.png)
- 2차
  ![img.png](/docs/img/index/reserve_list_after1.png)


예약요청이 들어온 좌석이 예약 가능한지 확인 - 제약조건 2번 쿼리
인덱스 적용 전
- 1차
  ![img.png](/docs/img/index/reserve_seat_before1-1.png)
  ![img.png](/docs/img/index/reserve_seat_before1-2.png)
- 2차
  ![img.png](/docs/img/index/reserve_seat_before2-1.png)
  ![img.png](/docs/img/index/reserve_seat_before2-2.png)

적용 후
- 1차
  ![img.png](/docs/img/index/reserve_seat_after1-1.png)
  ![img.png](/docs/img/index/reserve_seat_after1-2.png)
- 2차
  ![img.png](/docs/img/index/reserve_seat_after2-1.png)
  ![img.png](/docs/img/index/reserve_seat_after2-2.png)


