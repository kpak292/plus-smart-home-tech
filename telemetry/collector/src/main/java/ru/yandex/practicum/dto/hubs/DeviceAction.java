package ru.yandex.practicum.dto.hubs;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class DeviceAction {
    @NotBlank
    String sensorId;
    ActionType type;
    Integer value;
}
