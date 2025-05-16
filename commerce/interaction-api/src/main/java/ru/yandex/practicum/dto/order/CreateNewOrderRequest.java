package ru.yandex.practicum.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.dto.cart.ShoppingCartDto;
import ru.yandex.practicum.dto.warehouse.AddressDto;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CreateNewOrderRequest {
    @NotNull
    ShoppingCartDto shoppingCart;
    @NotNull
    AddressDto deliveryAddress;
}
