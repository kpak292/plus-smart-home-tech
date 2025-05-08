package ru.yandex.practicum.dto.warehouse;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class AddressDto {
    String country;
    String city;
    String street;
    String house;
    String flat;
}
