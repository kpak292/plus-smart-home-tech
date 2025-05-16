package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.cart.ShoppingCartDto;
import ru.yandex.practicum.dto.warehouse.*;

public interface WarehouseService {
    void add(NewProductInWarehouseRequest request);

    BookedProductsDto check(ShoppingCartDto cart);

    void addProducts(AddProductToWarehouseRequest request);

    AddressDto getAddress();

    void ShipProducts(ShippedToDeliveryRequest request);

    BookedProductsDto assemblyProducts(AssemblyProductsForOrderRequest request);
}
