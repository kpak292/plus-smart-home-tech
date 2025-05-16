package ru.yandex.practicum.repository;

import jakarta.persistence.*;
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
@Entity
@Table(name = "payments", schema = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID paymentId;
    UUID orderId;
    Double totalPayment;
    Double deliveryTotal;
    Double feeTotal;
    PaymentState state;
}
