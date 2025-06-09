package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.clients.OrderClient;
import ru.yandex.practicum.dto.order.CreateNewOrderRequest;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.order.ProductReturnRequest;
import ru.yandex.practicum.service.OrderService;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController implements OrderClient {
    OrderService orderService;

    @GetMapping
    public List<OrderDto> findOrdersByUser(@RequestParam String username) {
        log.info("Get order for user: {}", username);
        return orderService.getOrders(username);
    }

    @PutMapping
    public OrderDto createOrder(@RequestBody @Valid CreateNewOrderRequest request,
                               @RequestParam String username) {
        log.info("New order: {}", request);
        return orderService.createOrder(request, username);
    }

    @PostMapping("/return")
    public OrderDto returnOrderProducts(@RequestBody @Valid ProductReturnRequest request) {
        log.info("Return products: {}", request);
        return orderService.returnProducts(request);
    }

    @PostMapping("/payment")
    public OrderDto paymentSuccess(@RequestBody UUID orderId) {
        log.info("Pay order: {}", orderId);
        return orderService.payOrder(orderId);
    }

    @PostMapping("/payment/failed")
    public OrderDto paymentFailed(@RequestBody UUID orderId) {
        log.info("Pay failed order: {}", orderId);
        return orderService.cancelPayOrder(orderId);
    }

    @PostMapping("/delivery")
    public OrderDto deliverOrder(@RequestBody UUID orderId) {
        log.info("Deliver order: {}", orderId);
        return orderService.deliverOrder(orderId);
    }

    @PostMapping("/delivery/failed")
    @Override
    public OrderDto cancelDelivery(@RequestBody UUID orderId) {
        log.info("Cancel confirmDelivery order: {}", orderId);
        return orderService.cancelDeliveryOrder(orderId);
    }

    @PostMapping("/delivery/completed")
    @Override
    public OrderDto confirmDelivery(@RequestBody UUID orderId) {
        log.info("Complete confirmDelivery order: {}", orderId);
        return orderService.completeDelivery(orderId);
    }

    @PostMapping("/calculate/total")
    public OrderDto calculateTotalPrice(@RequestBody UUID orderId) {
        log.info("Calculate total price for order: {}", orderId);
        return orderService.calculateTotalPrice(orderId);
    }

    @PostMapping("calculate/delivery")
    public OrderDto calculateDeliveryPrice(@RequestBody UUID orderId) {
        log.info("Calculate confirmDelivery price for order: {}", orderId);
        return orderService.calculateDeliveryPrice(orderId);
    }

    @PostMapping("/assembly")
    @Override
    public OrderDto orderAssembly(@RequestBody UUID orderId) {
        log.info("Order assembly for order: {}", orderId);
        return orderService.orderAssembly(orderId);
    }

    @PostMapping("/assembly/failed")
    public OrderDto cancelOrderAssembly(@RequestBody UUID orderId) {
        log.info("Cancel order assembly for order: {}", orderId);
        return orderService.cancelOrderAssembly(orderId);
    }
}
