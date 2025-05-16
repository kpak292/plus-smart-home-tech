package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.order.CreateNewOrderRequest;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.order.ProductReturnRequest;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    List<OrderDto> getOrders(String username);

    OrderDto createOrder(CreateNewOrderRequest request, String username);

    OrderDto returnProducts(ProductReturnRequest request);

    OrderDto payOrder(UUID orderId);

    OrderDto cancelPayOrder(UUID orderId);

    OrderDto deliverOrder(UUID orderId);

    OrderDto cancelDeliveryOrder(UUID orderId);

    OrderDto completeDelivery(UUID orderId);

    OrderDto calculateTotalPrice(UUID orderId);

    OrderDto calculateDeliveryPrice(UUID orderId);

    OrderDto orderAssembly(UUID orderId);

    OrderDto cancelOrderAssembly(UUID orderId);
}
