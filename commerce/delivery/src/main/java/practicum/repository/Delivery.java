package practicum.repository;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.dto.delivery.DeliveryState;
import ru.yandex.practicum.dto.warehouse.AddressDto;

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
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID deliveryId;
    String countryFrom;
    String cityFrom;
    String streetFrom;
    String houseFrom;
    String flatFrom;
    String countryTo;
    String cityTo;
    String streetTo;
    String houseTo;
    String flatTo;
    UUID orderId;
    DeliveryState deliveryState;

}
