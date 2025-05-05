package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.clients.WarehouseClient;
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
    public ShoppingCartDto getCart(@RequestParam String userName) {
        log.info("Get cart for user: {}", userName);
        return cartService.getCart(userName);
    }

    @PutMapping
    public ShoppingCartDto addProductToCart(@RequestParam String userName,
                                            @RequestBody Map<UUID, Long> products) {
        log.info("Add products to cart for user: {}", userName);
        return cartService.addToCart(userName, products);
    }

    @DeleteMapping
    public void deleteCart(@RequestParam String userName) {
        log.info("Delete cart for user: {}", userName);
        cartService.deleteCart(userName);
    }

    @PostMapping("/remove")
    public ShoppingCartDto removeProductFromCart(@RequestParam String userName,
                                                 @RequestBody List<UUID> products) {
        log.info("Remove products from cart for user: {}", userName);
        return cartService.removeProductFromCart(userName, products);
    }

    @PostMapping("/change-quantity")
    public ShoppingCartDto changeProductQuantity(@RequestParam String userName,
                                                 @RequestBody @Valid ChangeProductQuantityRequest request) {
        log.info("Change product quantity for user: {}", userName);
        return cartService.changeProductQuantity(userName, request);
    }
}
