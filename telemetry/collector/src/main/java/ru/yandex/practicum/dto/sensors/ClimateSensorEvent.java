package ru.yandex.practicum.dto.sensors;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter @Setter @ToString(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class ClimateSensorEvent extends SensorEvent {
    int temperatureC;
    int humidity;
    int co2Level;

    @Override
    public SensorEventType getType() {
        return SensorEventType.CLIMATE_SENSOR_EVENT;
    }
}
