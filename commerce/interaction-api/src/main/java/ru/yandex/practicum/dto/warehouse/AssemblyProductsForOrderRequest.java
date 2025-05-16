package ru.yandex.practicum.dto.warehouse;

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
public class AssemblyProductsForOrderRequest {
    @NotNull
    Map<UUID,Long> products;
    @NotNull
    UUID orderId;
}
