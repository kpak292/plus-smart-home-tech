package ru.yandex.practicum.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ValueMapping;
import org.mapstruct.factory.Mappers;
import ru.yandex.practicum.dto.*;
import ru.yandex.practicum.kafka.telemetry.event.*;

import java.time.Instant;
import java.time.OffsetDateTime;

@Mapper
public interface SensorEventMapper {
    SensorEventMapper INSTANCE = Mappers.getMapper(SensorEventMapper.class);

    @Mapping(target = "payload", source = "event")
    SensorEventAvro toSensorEventAvro(CollectSensorEventRequest event);

    LightSensorAvro toLightSensorAvro(LightSensorEvent event);

    ClimateSensorAvro toClimateSensorAvro(ClimateSensorEvent event);

    MotionSensorAvro toMotionSensorAvro(MotionSensorEvent event);

    SwitchSensorAvro toSwitchSensorAvro(SwitchSensorEvent event);

    TemperatureSensorAvro toTemperatureSensorAvro(TemperatureSensorEvent event);

    default Object toPayload(CollectSensorEventRequest event) {
        return switch (event) {
            case LightSensorEvent lse -> toLightSensorAvro(lse);
            case ClimateSensorEvent cse -> toClimateSensorAvro(cse);
            case MotionSensorEvent mse -> toMotionSensorAvro(mse);
            case SwitchSensorEvent sse -> toSwitchSensorAvro(sse);
            case TemperatureSensorEvent tse -> toTemperatureSensorAvro(tse);
            default -> null;
        };
    }

    default Instant toInstant(OffsetDateTime timestamp) {
        return timestamp.toInstant();
    }
}
