package ru.yandex.practicum.clients;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.dto.cart.ShoppingCartDto;
import ru.yandex.practicum.dto.warehouse.AssemblyProductsForOrderRequest;
import ru.yandex.practicum.dto.warehouse.AddressDto;
import ru.yandex.practicum.dto.warehouse.BookedProductsDto;
import ru.yandex.practicum.dto.warehouse.ShippedToDeliveryRequest;

@FeignClient(name = "warehouse", fallback = ClientFallBack.class)
public interface WarehouseClient {
    @PostMapping("/api/v1/warehouse/check")
    BookedProductsDto checkCart(@RequestBody @Valid ShoppingCartDto cart);

    @PostMapping("/api/v1/warehouse/assembly")
    BookedProductsDto assemblyProductForOrderFromShoppingCart(@RequestBody AssemblyProductsForOrderRequest request);

    @GetMapping("/api/v1/warehouse/address")
    AddressDto getWarehouseAddress();

    @PostMapping("/api/v1/warehouse")
    void shippedToDelivery(@RequestBody @Valid ShippedToDeliveryRequest request);
}
