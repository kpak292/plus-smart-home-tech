package ru.yandex.practicum.repository;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "cart", schema = "shopping_cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID shoppingCartId;
    String userName;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "cart_products", schema = "shopping_cart",
            joinColumns = @JoinColumn(name = "cart_id"))
    @MapKeyColumn(name = "product_id")
    @Column(name = "quantity")
    Map<UUID, Long> products = new HashMap<>();
    Boolean active;
}
