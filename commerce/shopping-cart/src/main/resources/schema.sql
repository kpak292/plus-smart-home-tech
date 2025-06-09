create schema if not exists "shopping_cart";
SET SCHEMA 'shopping_cart';

create table if not exists cart
(
    shopping_cart_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_name        varchar,
    active           BOOL
);

create table if not exists cart_products
(
    cart_id    UUID,
    product_id UUID,
    quantity   INTEGER,

    PRIMARY KEY (cart_id, product_id),
    FOREIGN KEY (cart_id) REFERENCES cart (shopping_cart_id)
);

