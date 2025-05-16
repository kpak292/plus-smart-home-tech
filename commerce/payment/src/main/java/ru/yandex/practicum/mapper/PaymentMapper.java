package ru.yandex.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.payment.PaymentDto;
import ru.yandex.practicum.repository.Payment;
import ru.yandex.practicum.repository.PaymentState;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    default Payment newPayment(OrderDto orderDto) {
        return Payment.builder()
                .orderId(orderDto.getOrderId())
                .deliveryTotal(orderDto.getDeliveryPrice())
                .feeTotal(orderDto.getProductPrice())
                .totalPayment(orderDto.getTotalPrice())
                .state(PaymentState.PENDING)
                .build();
    }

    PaymentDto toDto(Payment payment);

}
