package ru.yandex.practicum.repository;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.dto.product.ProductCategory;
import ru.yandex.practicum.dto.product.ProductState;
import ru.yandex.practicum.dto.product.QuantityState;

import java.util.UUID;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "products", schema = "shopping_store")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID productId;
    String productName;
    String description;
    String imageSrc;
    @Enumerated(jakarta.persistence.EnumType.STRING)
    QuantityState quantityState;
    @Enumerated(jakarta.persistence.EnumType.STRING)
    ProductState productState;
    @Enumerated(jakarta.persistence.EnumType.STRING)
    ProductCategory productCategory;
    Double price;
}
