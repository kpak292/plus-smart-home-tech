create schema if not exists "payment";
SET SCHEMA 'payment';

create table if not exists payments(
    payment_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_id UUID,
    total_payment DOUBLE PRECISION,
    delivery_total DOUBLE PRECISION,
    fee_total DOUBLE PRECISION,
    state varchar
);