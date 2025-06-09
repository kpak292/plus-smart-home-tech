package ru.yandex.practicum.repository;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.dto.order.OrderState;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "orders", schema = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID orderId;
    String username;
    UUID shoppingCartId;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "order_products", schema = "order",
            joinColumns = @JoinColumn(name = "order_id"))
    @MapKeyColumn(name = "product_id")
    @Column(name = "quantity")
    Map<UUID, Long> products;
    UUID paymentId;
    UUID deliveryId;
    OrderState state;
    Double deliveryWeight;
    Double deliveryVolume;
    Boolean fragile;
    Double totalPrice;
    Double deliveryPrice;
    Double productPrice;
    String deliveryCountry;
    String deliveryCity;
    String deliveryStreet;
    String deliveryHouse;
    String deliveryFlat;

}
