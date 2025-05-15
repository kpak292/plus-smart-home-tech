package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.clients.WarehouseClient;
import ru.yandex.practicum.dto.cart.ShoppingCartDto;
import ru.yandex.practicum.dto.warehouse.*;
import ru.yandex.practicum.service.WarehouseService;

@Slf4j
@RestController
@RequestMapping("/api/v1/warehouse")
@RequiredArgsConstructor
public class WarehouseController implements WarehouseClient {
    private final WarehouseService warehouseService;

    @PutMapping
    public void addProduct(@RequestBody @Valid NewProductInWarehouseRequest request) {
        log.info("Add product to warehouse: {}", request);
        warehouseService.add(request);
    }

    @Override
    @PostMapping("/check")
    public BookedProductsDto checkCart(@RequestBody @Valid ShoppingCartDto cart) {
        log.info("Check cart: {}", cart);
        return warehouseService.check(cart);
    }

    @Override
    public BookedProductsDto assemblyProductForOrderFromShoppingCart(AssemblyProductsForOrderRequest request) {
        log.info("Assembly product for order from shopping cart: {}", request);
        return warehouseService.assemblyProducts(request) ;
    }

    @Override
    public void shippedToDelivery(ShippedToDeliveryRequest request) {
        log.info("Shipped to confirmDelivery: {}", request);
        warehouseService.ShipProducts(request);
    }

    @PostMapping("/add")
    public void addProducts(@RequestBody @Valid AddProductToWarehouseRequest request) {
        log.info("Add products to warehouse: {}", request);
        warehouseService.addProducts(request);
    }

    @Override
    @GetMapping("/address")
    public AddressDto getWarehouseAddress() {
        log.info("Get address");
        return warehouseService.getAddress();
    }
}
