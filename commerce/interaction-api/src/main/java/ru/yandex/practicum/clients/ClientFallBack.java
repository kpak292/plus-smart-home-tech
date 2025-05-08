package ru.yandex.practicum.clients;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.cart.ShoppingCartDto;
import ru.yandex.practicum.dto.product.QuantityState;
import ru.yandex.practicum.dto.warehouse.BookedProductsDto;

import java.util.UUID;

@Component
public class ClientFallBack implements StoreClient, WarehouseClient {
    @Override
    public BookedProductsDto checkCart(ShoppingCartDto cart) {
        throw new RuntimeException("Warehouse is not available");
    }

    @Override
    public Boolean setQuantity(UUID productId, QuantityState quantityState) {
        throw new RuntimeException("Store is not available");
    }
}
