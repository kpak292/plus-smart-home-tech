package ru.yandex.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.clients.WarehouseClient;
import ru.yandex.practicum.dto.cart.ChangeProductQuantityRequest;
import ru.yandex.practicum.dto.cart.ShoppingCartDto;
import ru.yandex.practicum.exception.NoProductsInShoppingCartException;
import ru.yandex.practicum.exception.NotAuthorizedUserException;
import ru.yandex.practicum.mapper.CartMapper;
import ru.yandex.practicum.repository.Cart;
import ru.yandex.practicum.repository.CartRepository;
import ru.yandex.practicum.service.CartService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final WarehouseClient warehouseClient;

    @Override
    public ShoppingCartDto getCart(String username) {
        return CartMapper.INSTANCE.toDto(getCartEntity(username));
    }

    @Override
    public Cart getCartEntity(String username) {
        if(username == null || username.isEmpty()) {
            throw new NotAuthorizedUserException("Username is empty");
        }

        Optional<Cart> cart = cartRepository.findByUserNameAndActive(username, true);
        if (cart.isEmpty()) {
            Cart newCart = new Cart();
            newCart.setUserName(username);
            newCart.setActive(true);
            cart = Optional.of(cartRepository.save(newCart));
        }

        return cart.get();
    }

    @Override
    public ShoppingCartDto addToCart(String username, Map<UUID, Long> products) {
        Cart cart = getCartEntity(username);
        cart.getProducts().putAll(products);

        warehouseClient.checkCart(CartMapper.INSTANCE.toDto(cart));

        cart = cartRepository.save(cart);
        return CartMapper.INSTANCE.toDto(cart);
    }

    @Override
    public void deleteCart(String username) {
        Cart cart = getCartEntity(username);
        cart.setActive(false);
        cartRepository.save(cart);
    }

    @Override
    public ShoppingCartDto removeProductFromCart(String username, List<UUID> products) {
        Cart cart = getCartEntity(username);

        if (!cart.getProducts().keySet().containsAll(products)){
            throw new NoProductsInShoppingCartException("Products not found in cart");
        }

        products.forEach(cart.getProducts()::remove);

        cart = cartRepository.save(cart);

        return CartMapper.INSTANCE.toDto(cart);
    }

    @Override
    public ShoppingCartDto changeProductQuantity(String username, ChangeProductQuantityRequest request) {
        Cart cart = getCartEntity(username);

        if (!cart.getProducts().containsKey(request.getProductId())){
            throw new NoProductsInShoppingCartException("Product not found in cart");
        }

        cart.getProducts().put(request.getProductId(), request.getNewQuantity());

        cart = cartRepository.save(cart);

        return CartMapper.INSTANCE.toDto(cart);
    }
}
