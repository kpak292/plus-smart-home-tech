package ru.yandex.practicum.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class SetProductQuantityStateRequest {
    @NotNull
    @NotBlank
    UUID productId;
    @NotNull
    QuantityState quantityState;
}
