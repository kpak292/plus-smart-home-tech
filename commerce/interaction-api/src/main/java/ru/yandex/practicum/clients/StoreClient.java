package ru.yandex.practicum.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.yandex.practicum.dto.product.QuantityState;

import java.util.UUID;

@FeignClient(name = "shopping-store", fallback = ClientFallBack.class)
public interface StoreClient {
    @PostMapping("/api/v1/shopping-store/quantityState")
    Boolean setQuantity(@RequestParam UUID productId, @RequestParam QuantityState quantityState);
}
