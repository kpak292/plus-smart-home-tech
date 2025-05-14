create schema if not exists "order";
SET SCHEMA 'order';

create table if not exists orders(
    order_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username varchar,
    shopping_cart_id UUID,
    payment_id UUID,
    delivery_id UUID,
    state varchar,
    delivery_weight DOUBLE PRECISION,
    delivery_volume DOUBLE PRECISION,
    fragile bool,
    total_price DOUBLE PRECISION,
    delivery_price DOUBLE PRECISION,
    product_price DOUBLE PRECISION,
    delivery_country varchar,
    delivery_city varchar,
    delivery_street varchar,
    delivery_house varchar,
    delivery_flat varchar
);


create table if not exists order_products
(
    order_id    UUID,
    product_id UUID,
    quantity   INTEGER,

    PRIMARY KEY (order_id, product_id),
    FOREIGN KEY (order_id) REFERENCES orders (order_id)
);