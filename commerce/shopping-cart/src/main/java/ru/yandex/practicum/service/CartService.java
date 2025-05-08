package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.cart.ChangeProductQuantityRequest;
import ru.yandex.practicum.dto.cart.ShoppingCartDto;
import ru.yandex.practicum.repository.Cart;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CartService {
    ShoppingCartDto getCart(String username);

    Cart getCartEntity(String username);

    ShoppingCartDto addToCart(String username, Map<UUID, Long> products);

    void deleteCart(String username);

    ShoppingCartDto removeProductFromCart(String username, List<UUID> products);

    ShoppingCartDto changeProductQuantity(String username, ChangeProductQuantityRequest request);
}
