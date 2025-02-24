drop database hhplus_concert;
create database hhplus_concert;
use hhplus_concert;

DROP TABLE IF EXISTS user;
CREATE TABLE user
(
    id    BIGINT AUTO_INCREMENT NOT NULL,
    email VARCHAR(255)          NOT NULL,
    name  VARCHAR(255)          NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

DROP TABLE IF EXISTS reservation;
CREATE TABLE reservation
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    user_id      BIGINT                NOT NULL,
    price_amount INT                   NOT NULL,
    CONSTRAINT pk_reservation PRIMARY KEY (id)
);

DROP TABLE IF EXISTS reservation_item;
CREATE TABLE reservation_item
(
    id                  BIGINT AUTO_INCREMENT NOT NULL,
    reservation_id      BIGINT                NOT NULL,
    concert_schedule_id BIGINT                NOT NULL,
    seat_id             BIGINT                NOT NULL,
    reservation_status  VARCHAR(255)          NOT NULL,
    expired_at          TIMESTAMP             NOT NULL,
    CONSTRAINT pk_reservation_item PRIMARY KEY (id)
);
# CREATE INDEX idx_concert_schedule_id ON reservation_item (concert_schedule_id);

DROP TABLE IF EXISTS queue_token;
CREATE TABLE queue_token
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    token_uuid   varchar(255)          NOT NULL,
    status       varchar(255)          NOT NULL,
    expired_time datetime              NULL,
    CONSTRAINT pk_queue_token PRIMARY KEY (id)
);

DROP TABLE IF EXISTS payment_history_entity;
CREATE TABLE payment_history_entity
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    user_id        BIGINT                NOT NULL,
    reservation_id BIGINT                NOT NULL,
    paid_point     INT                   NOT NULL,
    CONSTRAINT pk_payment_history_entity PRIMARY KEY (id)
);

DROP TABLE IF EXISTS point;
CREATE TABLE point
(
    id      BIGINT AUTO_INCREMENT NOT NULL,
    user_id BIGINT                NOT NULL,
    point   INT                   NOT NULL,
    CONSTRAINT pk_point PRIMARY KEY (id)
);

DROP TABLE IF EXISTS concert;
CREATE TABLE concert
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    name      VARCHAR(255)          NOT NULL,
    performer VARCHAR(255)          NOT NULL,
    CONSTRAINT pk_concert PRIMARY KEY (id)
);

DROP TABLE IF EXISTS concert_schedule;
CREATE TABLE concert_schedule
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    concert_id     BIGINT                NOT NULL,
    ticket_price   INT                   NOT NULL,
    scheduled_time datetime              NOT NULL,
    location_id    BIGINT                NOT NULL,
    reserved_count INT                   NOT NULL,
    CONSTRAINT pk_concert_schedule PRIMARY KEY (id)
);

DROP TABLE IF EXISTS location;
CREATE TABLE location
(
    id                BIGINT AUTO_INCREMENT NOT NULL,
    name              VARCHAR(255)          NOT NULL,
    location_capacity INT                   NOT NULL,
    CONSTRAINT pk_location PRIMARY KEY (id)
);

DROP TABLE IF EXISTS seat;
CREATE TABLE seat
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    location_id BIGINT                NOT NULL,
    seat_number INT                   NOT NULL,
    CONSTRAINT pk_seat PRIMARY KEY (id)
);



## 데이터 넣기 대규모
SHOW VARIABLES LIKE 'max_recursive_iterations';
SET SESSION max_recursive_iterations = 1000000; -- 100만개

WITH RECURSIVE numbers AS (
    SELECT 1 AS n
    UNION ALL
    SELECT n +1 FROM  numbers WHERE n < 10000
)
select n from numbers;

## 콘서트 10000개
INSERT INTO concert (id, name, performer)
SELECT n, CONCAT('콘서트 ', n), CONCAT('가수 ', n)
FROM (
         WITH RECURSIVE numbers AS (
             SELECT 1 AS n
             UNION ALL
             SELECT n + 1 FROM numbers WHERE n < 10000
         )
         SELECT n FROM numbers
     ) AS t;

## 장소 100000 개
INSERT INTO location (id, name, location_capacity)
SELECT n, CONCAT('장소 ', n), 50
FROM (
         WITH RECURSIVE numbers AS (
             SELECT 1 AS n
             UNION ALL
             SELECT n + 1 FROM numbers WHERE n < 100000
         )
         SELECT n FROM numbers
     ) AS t;

# 장소 1만개가 있다고 가정 및 각 장소마다 50개 좌석 (총 50만개 좌석)
INSERT INTO seat (id, location_id, seat_number)
SELECT ROW_NUMBER() OVER (), -- id 자동 증가
        n AS location_id,
       seat_num
FROM (
         WITH RECURSIVE numbers AS (
             SELECT 1 AS n
             UNION ALL
             SELECT n + 1 FROM numbers WHERE n < 10000
         ),
                        seat_numbers AS (
                            SELECT 1 AS seat_num
                            UNION ALL
                            SELECT seat_num + 1 FROM seat_numbers WHERE seat_num < 50
                        )
         SELECT n, seat_num FROM numbers CROSS JOIN seat_numbers
     ) AS t;
create index index_seat_location_id on seat (location_id);
drop index index_seat_location_id on seat;

SELECT * FROM seat WHERE location_id = 100;
SELECT * FROM seat WHERE location_id = 2000;
SELECT * FROM seat WHERE location_id = 6000;
SELECT * FROM seat WHERE location_id = 12200;
SELECT * FROM seat WHERE location_id = 25000;
SELECT * FROM seat WHERE location_id = 32110;
SELECT * FROM seat WHERE location_id = 55920;
SELECT * FROM seat WHERE location_id = 79210;
SELECT * FROM seat WHERE location_id = 81251;
SELECT * FROM seat WHERE location_id = 99999;


# point 조회
INSERT INTO point (id, user_id, point)
SELECT n, n, 100000
FROM (
         WITH RECURSIVE numbers AS (
             SELECT 1 AS n
             UNION ALL
             SELECT n + 1 FROM numbers WHERE n < 100000
         )
         SELECT n FROM numbers
     ) AS t;

create index index_point_user_id on point (user_id);
drop index index_point_user_id on point;

select * from point where user_id = 100;
select * from point where user_id = 1000;
select * from point where user_id = 10000;
select * from point where user_id = 20000;
select * from point where user_id = 30000;
select * from point where user_id = 40000;
select * from point where user_id = 50000;
select * from point where user_id = 60000;
select * from point where user_id = 70000;
select * from point where user_id = 99999;

INSERT INTO reservation_item (reservation_id, concert_schedule_id, seat_id, reservation_status, expired_at)
SELECT n, FLOOR(RAND() * 10), FLOOR(RAND() * 50), 'NON_PAID', '2000-01-01 00:00:00'
FROM (
         WITH RECURSIVE numbers AS (
             SELECT 1 AS n
             UNION ALL
             SELECT n +1 FROM numbers WHERE n < 100000
         )
         SELECT n from numbers
     ) AS t;

UPDATE reservation_item SET reservation_status = 'PAID', concert_schedule_id = 10000, seat_id = 10000 WHERE id = 10000;
UPDATE reservation_item SET reservation_status = 'PAID', concert_schedule_id = 25000, seat_id = 25000 WHERE id = 25000;
UPDATE reservation_item SET reservation_status = 'PAID', concert_schedule_id = 50100, seat_id = 50100 WHERE id = 50100;
UPDATE reservation_item SET reservation_status = 'PAID', concert_schedule_id = 72000, seat_id = 72000 WHERE id = 72000;
UPDATE reservation_item SET reservation_status = 'PAID', concert_schedule_id = 92000, seat_id = 92000 WHERE id = 92000;
UPDATE reservation_item SET expired_at = '2030-01-01 00:00:00', concert_schedule_id = 12000, seat_id = 12000 where id = 12000;
UPDATE reservation_item SET expired_at = '2030-01-01 00:00:00', concert_schedule_id = 28000, seat_id = 28000 where id = 28000;
UPDATE reservation_item SET expired_at = '2030-01-01 00:00:00', concert_schedule_id = 52000, seat_id = 52000 where id = 52000;
UPDATE reservation_item SET expired_at = '2030-01-01 00:00:00', concert_schedule_id = 79000, seat_id = 79000 where id = 79000;
UPDATE reservation_item SET expired_at = '2030-01-01 00:00:00', concert_schedule_id = 99900, seat_id = 99900 where id = 99900;

create index reservation_item_index on reservation_item (concert_schedule_id, reservation_status, expired_at, seat_id);
drop index reservation_item_index on reservation_item;

SELECT * FROM reservation_item WHERE concert_schedule_id = 10000
                                 AND (reservation_status = 'PAID' OR expired_at > '2025-01-01 00:00:00');
SELECT * FROM reservation_item WHERE concert_schedule_id = 12000
                                 AND (reservation_status = 'PAID' OR expired_at > '2025-01-01 00:00:00');
SELECT * FROM reservation_item WHERE concert_schedule_id = 25000
                                 AND (reservation_status = 'PAID' OR expired_at > '2025-01-01 00:00:00');
SELECT * FROM reservation_item WHERE concert_schedule_id = 28000
                                 AND (reservation_status = 'PAID' OR expired_at > '2025-01-01 00:00:00');
SELECT * FROM reservation_item WHERE concert_schedule_id = 50100
                                 AND (reservation_status = 'PAID' OR expired_at > '2025-01-01 00:00:00');
SELECT * FROM reservation_item WHERE concert_schedule_id = 52000
                                 AND (reservation_status = 'PAID' OR expired_at > '2025-01-01 00:00:00');
SELECT * FROM reservation_item WHERE concert_schedule_id = 72000
                                 AND (reservation_status = 'PAID' OR expired_at > '2025-01-01 00:00:00');
SELECT * FROM reservation_item WHERE concert_schedule_id = 79000
                                 AND (reservation_status = 'PAID' OR expired_at > '2025-01-01 00:00:00');
SELECT * FROM reservation_item WHERE concert_schedule_id = 92000
                                 AND (reservation_status = 'PAID' OR expired_at > '2025-01-01 00:00:00');
SELECT * FROM reservation_item WHERE concert_schedule_id = 99900
                                 AND (reservation_status = 'PAID' OR expired_at > '2025-01-01 00:00:00');

SELECT * FROM reservation_item
WHERE concert_schedule_id = 10000
  AND (reservation_status = 'PAID' OR expired_at > '2025-01-01 00:00:00')
  AND seat_Id IN (10000);
SELECT * FROM reservation_item
WHERE concert_schedule_id = 12000
  AND (reservation_status = 'PAID' OR expired_at > '2025-01-01 00:00:00')
  AND seat_Id IN (12000);
SELECT * FROM reservation_item
WHERE concert_schedule_id = 25000
  AND (reservation_status = 'PAID' OR expired_at > '2025-01-01 00:00:00')
  AND seat_Id IN (25000);
SELECT * FROM reservation_item
WHERE concert_schedule_id = 28000
  AND (reservation_status = 'PAID' OR expired_at > '2025-01-01 00:00:00')
  AND seat_Id IN (28000);
SELECT * FROM reservation_item
WHERE concert_schedule_id = 50100
  AND (reservation_status = 'PAID' OR expired_at > '2025-01-01 00:00:00')
  AND seat_Id IN (50100);

SELECT * FROM reservation_item
WHERE concert_schedule_id = 52000
  AND (reservation_status = 'PAID' OR expired_at > '2025-01-01 00:00:00')
  AND seat_Id IN (52000);
SELECT * FROM reservation_item
WHERE concert_schedule_id = 72000
  AND (reservation_status = 'PAID' OR expired_at > '2025-01-01 00:00:00')
  AND seat_Id IN (72000);
SELECT * FROM reservation_item
WHERE concert_schedule_id = 79000
  AND (reservation_status = 'PAID' OR expired_at > '2025-01-01 00:00:00')
  AND seat_Id IN (79000);
SELECT * FROM reservation_item
WHERE concert_schedule_id = 92000
  AND (reservation_status = 'PAID' OR expired_at > '2025-01-01 00:00:00')
  AND seat_Id IN (92000);
SELECT * FROM reservation_item
WHERE concert_schedule_id = 99900
  AND (reservation_status = 'PAID' OR expired_at > '2025-01-01 00:00:00')
  AND seat_Id IN (99900);


