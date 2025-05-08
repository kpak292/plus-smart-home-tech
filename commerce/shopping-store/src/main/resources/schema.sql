create schema if not exists "shopping_store";
SET SCHEMA 'shopping_store';

create table if not exists products(
    product_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    product_name varchar,
    description varchar,
    image_src varchar,
    quantity_state varchar,
    product_state varchar,
    product_category varchar,
    price DOUBLE PRECISION
);

