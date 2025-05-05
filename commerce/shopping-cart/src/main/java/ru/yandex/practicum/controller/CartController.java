package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.cart.ChangeProductQuantityRequest;
import ru.yandex.practicum.dto.cart.ShoppingCartDto;
import ru.yandex.practicum.service.CartService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/shopping-cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public ShoppingCartDto getCart(@RequestParam String username) {
        log.info("Get cart for user: {}", username);
        return cartService.getCart(username);
    }

    @PutMapping
    public ShoppingCartDto addProductToCart(@RequestParam String username,
                                            @RequestBody Map<UUID, Long> products) {
        log.info("Add products to cart for user: {}", username);
        return cartService.addToCart(username, products);
    }

    @DeleteMapping
    public void deleteCart(@RequestParam String username) {
        log.info("Delete cart for user: {}", username);
        cartService.deleteCart(username);
    }

    @PostMapping("/remove")
    public ShoppingCartDto removeProductFromCart(@RequestParam String username,
                                                 @RequestBody List<UUID> products) {
        log.info("Remove products from cart for user: {}", username);
        return cartService.removeProductFromCart(username, products);
    }

    @PostMapping("/change-quantity")
    public ShoppingCartDto changeProductQuantity(@RequestParam String username,
                                                 @RequestBody @Valid ChangeProductQuantityRequest request) {
        log.info("Change product quantity for user: {}", username);
        return cartService.changeProductQuantity(username, request);
    }
}
