create schema if not exists "delivery";
SET SCHEMA 'delivery';

create table if not exists payments
(
    payment_id     UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    delivery_id    UUID,
    country_from   varchar,
    city_from      varchar,
    street_from    varchar,
    house_from     varchar,
    flat_from      varchar,
    country_to     varchar,
    city_to        varchar,
    street_to      varchar,
    house_to       varchar,
    flat_to        varchar,
    order_id       UUID,
    delivery_state varchar
);