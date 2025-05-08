create schema if not exists "warehouse";
SET SCHEMA 'warehouse';

create table if not exists warehouse_product(
    product_id UUID PRIMARY KEY,
    fragile BOOL,
    dimension_width DOUBLE PRECISION,
    dimension_height DOUBLE PRECISION,
    dimension_depth DOUBLE PRECISION,
    weight DOUBLE PRECISION,
    quantity BIGINT
);

