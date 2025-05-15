package ru.yandex.practicum.clients;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.payment.PaymentDto;

@FeignClient(name = "payment", fallback = ClientFallBack.class)
public interface PaymentClient {
    @PostMapping("/api/v1/payment/productCost")
    Double productCost(OrderDto orderDto);

    @PostMapping("/api/v1/payment/totalCost")
    Double getTotalCost(OrderDto orderDto);

    @PostMapping("/api/v1/payment")
    PaymentDto payment(@RequestBody @Valid OrderDto orderDto);
}
