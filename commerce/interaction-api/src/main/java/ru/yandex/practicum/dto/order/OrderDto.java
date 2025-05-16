package ru.yandex.practicum.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class OrderDto {
    @NotNull
    UUID orderId;
    UUID shoppingCartId;
    @NotNull
    Map<UUID, Long> products;
    UUID paymentId;
    UUID deliveryId;
    OrderState state;
    Double deliveryWeight;
    Double deliveryVolume;
    Boolean fragile;
    Double totalPrice;
    Double deliveryPrice;
    Double productPrice;
}
