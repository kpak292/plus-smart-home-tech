package ru.yandex.practicum.dto.cart;

import jakarta.validation.constraints.NotBlank;
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
public class ShoppingCartDto {
    @NotNull
    @NotBlank
    UUID shoppingCartId;
    @NotNull
    Map<UUID, Long> products;
}
