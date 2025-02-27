# create database concert;
use concert;
SET SESSION max_recursive_iterations = 1000000; -- 100만개

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


create index index_seat_location_id on seat (location_id);
create index index_point_user_id on point (user_id);
create index reservation_item_index on reservation_item (concert_schedule_id, reservation_status, expired_at, seat_id);


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

INSERT INTO concert_schedule (concert_id, ticket_price, scheduled_time, location_id, reserved_count)
SELECT n, 10000, TIMESTAMP('20990201'), n, 0
FROM (
         WITH RECURSIVE numbers AS (
             SELECT 1 AS n
             UNION ALL
             SELECT n + 1 FROM numbers WHERE n < 10000
         ),
                        schedule_numbers AS (
                            SELECT 1 AS schedule_num
                            UNION ALL
                            SELECT schedule_num + 1 FROM schedule_numbers WHERE schedule_num < 10
                        )
         SELECT n, schedule_num FROM numbers CROSS JOIN schedule_numbers
     ) AS t;

## 장소 10000 개
INSERT INTO location (id, name, location_capacity)
SELECT n, CONCAT('장소 ', n), 50
FROM (
         WITH RECURSIVE numbers AS (
             SELECT 1 AS n
             UNION ALL
             SELECT n + 1 FROM numbers WHERE n < 10000
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
