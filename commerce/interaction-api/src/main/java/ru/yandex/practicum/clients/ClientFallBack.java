package ru.yandex.practicum.clients;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.cart.ShoppingCartDto;
import ru.yandex.practicum.dto.delivery.DeliveryDto;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.product.ProductDto;
import ru.yandex.practicum.dto.product.QuantityState;
import ru.yandex.practicum.dto.warehouse.BookedProductsDto;

import java.util.UUID;

@Component
public abstract class ClientFallBack implements StoreClient, WarehouseClient, OrderClient ,DeliveryClient{
    @Override
    public BookedProductsDto checkCart(ShoppingCartDto cart) {
        throw new RuntimeException("Warehouse is not available");
    }

    @Override
    public Boolean setQuantity(UUID productId, QuantityState quantityState) {
        throw new RuntimeException("Store is not available");
    }

    @Override
    public ProductDto getProduct(UUID productId) {
        throw new RuntimeException("Store is not available");
    }

    @Override
    public OrderDto deliveryFailed(String orderId) {
        throw new RuntimeException("Order is not available");
    }

    @Override
    public OrderDto delivery(String orderId) {
        throw new RuntimeException("Order is not available");
    }

    @Override
    public OrderDto paymentSuccess(String orderId) {
        throw new RuntimeException("Order is not available");
    }

    @Override
    public OrderDto paymentFailed(String orderId) {
        throw new RuntimeException("Order is not available");
    }

    @Override
    public DeliveryDto planDelivery(DeliveryDto delivery) {
        throw new RuntimeException("Delivery is not available");
    }

    @Override
    public Double deliveryCost(OrderDto orderDto) {
        throw new RuntimeException("Delivery is not available");
    }

    @Override
    public void deliveryFailed(UUID orderId) {
        throw new RuntimeException("Delivery is not available");
    }

    @Override
    public void delivery(UUID orderId) {
        throw new RuntimeException("Delivery is not available");
    }
}
