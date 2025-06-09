package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.clients.PaymentClient;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.payment.PaymentDto;
import ru.yandex.practicum.service.PaymentService;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController implements PaymentClient {
    PaymentService paymentService;

    @Override
    @PostMapping
    public PaymentDto payment(@RequestBody @Valid OrderDto orderDto){
        log.info("Payment for order: {}", orderDto);
        return paymentService.payment(orderDto);
    }

    @Override
    @PostMapping("/totalCost")
    public Double getTotalCost(OrderDto orderDto) {
        log.info("Calculate total cost for order: {}", orderDto);
        return paymentService.getTotalCost(orderDto);
    }

    @PostMapping("/refund")
    public void paymentSuccess(@RequestParam UUID paymentId){
        log.info("Payment success for payment: {}", paymentId);
        paymentService.paymentSuccess(paymentId);
    }

    @PostMapping("/failed")
    public void paymentFailed(@RequestParam UUID paymentId){
        log.info("Payment failed for payment: {}", paymentId);
        paymentService.paymentFailed(paymentId);
    }

    @Override
    @PostMapping("/productCost")
    public Double productCost(OrderDto orderDto) {
        log.info("Calculate product cost for order: {}", orderDto);
        return paymentService.productCost(orderDto);
    }
}
