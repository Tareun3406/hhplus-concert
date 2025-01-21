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
CREATE INDEX idx_concert_schedule_id ON reservation_item (concert_schedule_id);

DROP TABLE IF EXISTS queue_token;
CREATE TABLE queue_token
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    user_id      BIGINT                NOT NULL,
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


insert into user(id, email, name) value (1, 'abc@test.com', 'name');
insert into point(id, user_id, point) value(1, 1, 10000);
insert into concert value (1, '1번 콘서트', '가수 1');
insert into concert_schedule value (1, 1, 1000, TIMESTAMP('20990201'), 1, 1);
insert into location value (1, '장소 1', 4);
insert into seat values (1, 1, 1),(2, 1, 2),(3, 1, 3),(4, 1, 4);
insert into reservation value (1, 1, 1);
insert into reservation_item value (1, 1, 1, 1, 'NON_PAID', NOW());

insert into queue_token value (1, 1, '9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d', 'PENDING', null);