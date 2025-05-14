package ru.yandex.practicum.repository;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "order_booking", schema = "warehouse")
public class OrderBooking {
    @Id
    UUID orderBookingId;

    UUID orderId;
    UUID deliveryId;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "order_booking_products", schema = "warehouse",
            joinColumns = @JoinColumn(name = "order_booking_id"))
    @MapKeyColumn(name = "product_id")
    @Column(name = "quantity")
    Map<UUID, Long> products;
}
