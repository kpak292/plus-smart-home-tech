package ru.yandex.practicum.clients;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.dto.cart.ShoppingCartDto;
import ru.yandex.practicum.dto.warehouse.BookedProductsDto;

@FeignClient(name = "warehouse", fallback = ClientFallBack.class)
public interface WarehouseClient {
    @PostMapping("/api/v1/warehouse/check")
    BookedProductsDto checkCart(@RequestBody @Valid ShoppingCartDto cart);
}
