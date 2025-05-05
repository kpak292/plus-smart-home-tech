package ru.yandex.practicum.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import ru.yandex.practicum.dto.product.SetProductQuantityStateRequest;

@FeignClient(name = "shopping-store")
public interface StoreClient {

    @PostMapping("/api/v1/shopping-store/quantityState")
    Boolean setQuantity(SetProductQuantityStateRequest request);
}
