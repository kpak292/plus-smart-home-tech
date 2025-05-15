package ru.yandex.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.clients.OrderClient;
import ru.yandex.practicum.clients.StoreClient;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.payment.PaymentDto;
import ru.yandex.practicum.dto.product.ProductDto;
import ru.yandex.practicum.exception.NoOrderFoundException;
import ru.yandex.practicum.exception.NotEnoughInfoInOrderToCalculateException;
import ru.yandex.practicum.mapper.PaymentMapper;
import ru.yandex.practicum.repository.Payment;
import ru.yandex.practicum.repository.PaymentRepository;
import ru.yandex.practicum.repository.PaymentState;
import ru.yandex.practicum.service.PaymentService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    PaymentRepository paymentRepository;
    OrderClient orderClient;
    StoreClient storeClient;

    @Override
    public PaymentDto payment(OrderDto order) {
        if (order.getTotalPrice() == null || order.getDeliveryPrice() == null || order.getProductPrice() == null) {
            throw new NotEnoughInfoInOrderToCalculateException("Total price, confirmDelivery price and product price must be set");
        }

        Payment payment = PaymentMapper.INSTANCE.newPayment(order);
        payment = paymentRepository.save(payment);

        return PaymentMapper.INSTANCE.toDto(payment);
    }

    @Override
    public Double getTotalCost(OrderDto order) {
        if (order.getDeliveryPrice() == null || order.getProductPrice() == null) {
            throw new NotEnoughInfoInOrderToCalculateException("Delivery price and product price must be set");
        }
        return order.getProductPrice() * 1.1 + order.getDeliveryPrice();
    }

    @Override
    public void paymentSuccess(UUID paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new NoOrderFoundException("Payment not found"));

        payment.setState(PaymentState.SUCCESS);
        paymentRepository.save(payment);

        orderClient.paymentSuccess(payment.getOrderId());
    }

    @Override
    public void paymentFailed(UUID paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new NoOrderFoundException("Payment not found"));

        payment.setState(PaymentState.FAILED);
        paymentRepository.save(payment);

        orderClient.paymentFailed(payment.getOrderId());
    }

    @Override
    public Double productCost(OrderDto order) {
        if (order.getProducts() == null || order.getProducts().isEmpty() ) {
            throw new NotEnoughInfoInOrderToCalculateException("Total price, confirmDelivery price and product price must be set");
        }
        return order.getProducts().entrySet().stream()
                .mapToDouble(entry ->{
                    ProductDto product = storeClient.getProduct(entry.getKey());
                    return product.getPrice() * entry.getValue();
                })
                .sum();
    }
}
