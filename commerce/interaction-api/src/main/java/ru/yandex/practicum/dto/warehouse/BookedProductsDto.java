package ru.yandex.practicum.dto.warehouse;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class BookedProductsDto {
    @NotNull
    Double deliveryWeight;
    @NotNull
    Double deliveryVolume;
    @NotNull
    Boolean fragile;
}
