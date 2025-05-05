package ru.yandex.practicum.repository;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "warehouse_product", schema = "warehouse")
public class WarehouseProduct {
    @Id
    UUID productId;
    Boolean fragile;
    Double dimensionWidth;
    Double dimensionHeight;
    Double dimensionDepth;
    Double weight;
    Long quantity;
}
