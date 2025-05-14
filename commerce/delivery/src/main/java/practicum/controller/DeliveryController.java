package practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practicum.service.DeliveryService;
import ru.yandex.practicum.clients.DeliveryClient;
import ru.yandex.practicum.dto.delivery.DeliveryDto;
import ru.yandex.practicum.dto.order.OrderDto;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/delivery")
@RequiredArgsConstructor
public class DeliveryController implements DeliveryClient {
    DeliveryService deliveryService;

    @Override
    @PutMapping
    public DeliveryDto planDelivery(DeliveryDto delivery) {
        log.info("Plan delivery: {}", delivery);
        return deliveryService.planDelivery(delivery);
    }

    @Override
    @PostMapping
    public Double deliveryCost(OrderDto orderDto) {
        log.info("Calculate delivery cost for order: {}", orderDto);
        return deliveryService.deliveryCost(orderDto);
    }

    @Override
    @PostMapping("/failed")
    public void deliveryFailed(UUID orderId) {
        log.info("Delivery failed for order: {}", orderId);
        deliveryService.deliveryFailed(orderId);
    }

    @Override
    @PostMapping("/successful")
    public void delivery(UUID orderId) {
        log.info("Delivery successful for order: {}", orderId);
        deliveryService.deliverySuccessful(orderId);
    }

    @PostMapping("/picked")
    public void pick(UUID orderId) {
        log.info("Order picked for order: {}", orderId);
        deliveryService.pick(orderId);
    }
}
