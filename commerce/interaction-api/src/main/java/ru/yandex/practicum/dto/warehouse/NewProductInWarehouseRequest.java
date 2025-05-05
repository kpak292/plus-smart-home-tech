package ru.yandex.practicum.dto.warehouse;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class NewProductInWarehouseRequest {
    @NotNull
    UUID productId;
    Boolean fragile;
    @NotNull
    DimensionDto dimension;
    @NotNull
    Double weight;
}
