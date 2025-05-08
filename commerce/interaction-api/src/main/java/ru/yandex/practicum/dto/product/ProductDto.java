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
@EqualsAndHashCode
@ToString
public class ProductDto {
    UUID productId;
    @NotNull
    @NotBlank
    String productName;
    @NotNull
    @NotBlank
    String description;
    String imageSrc;
    @NotNull
    QuantityState quantityState;
    @NotNull
    ProductState productState;
    ProductCategory productCategory;
    Double price;
}
