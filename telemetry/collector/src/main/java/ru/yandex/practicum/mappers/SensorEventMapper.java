package ru.yandex.practicum.mappers;

import com.google.protobuf.Timestamp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.yandex.practicum.grpc.telemetry.event.*;
import ru.yandex.practicum.kafka.telemetry.event.*;
import ru.yandex.practicum.rest.dto.sensors.*;
import ru.yandex.practicum.rest.dto.sensors.SensorEvent;

import java.time.Instant;

@Mapper
public interface SensorEventMapper {
    SensorEventMapper INSTANCE = Mappers.getMapper(SensorEventMapper.class);

    @Mapping(target = "payload", source = "event")
    SensorEventAvro toSensorEventAvro(SensorEvent event);

    LightSensorAvro toLightSensorAvro(LightSensorEvent event);

    ClimateSensorAvro toClimateSensorAvro(ClimateSensorEvent event);

    MotionSensorAvro toMotionSensorAvro(MotionSensorEvent event);

    SwitchSensorAvro toSwitchSensorAvro(SwitchSensorEvent event);

    TemperatureSensorAvro toTemperatureSensorAvro(TemperatureSensorEvent event);

    default Object toPayload(SensorEvent event) {
        return switch (event) {
            case LightSensorEvent lse -> toLightSensorAvro(lse);
            case ClimateSensorEvent cse -> toClimateSensorAvro(cse);
            case MotionSensorEvent mse -> toMotionSensorAvro(mse);
            case SwitchSensorEvent sse -> toSwitchSensorAvro(sse);
            case TemperatureSensorEvent tse -> toTemperatureSensorAvro(tse);
            default -> null;
        };
    }

    @Mapping(target = "payload", expression = "java( toPayload( event ) )")
    SensorEventAvro toSensorEventAvro(SensorEventProto event);

    default Instant toInstant(Timestamp timestamp) {
        return Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos());
    }

    default Object toPayload(SensorEventProto event) {
        if (event.hasLightSensorEvent()) {
            return toLightSensorAvro(event.getLightSensorEvent());
        } else if (event.hasClimateSensorEvent()) {
            return toClimateSensorAvro(event.getClimateSensorEvent());
        } else if (event.hasMotionSensorEvent()) {
            return toMotionSensorAvro(event.getMotionSensorEvent());
        } else if (event.hasSwitchSensorEvent()) {
            return toSwitchSensorAvro(event.getSwitchSensorEvent());
        } else if (event.hasTemperatureSensorEvent()) {
            return toTemperatureSensorAvro(event.getTemperatureSensorEvent());
        }

        return null;
    }

    LightSensorAvro toLightSensorAvro(LightSensorProto event);

    ClimateSensorAvro toClimateSensorAvro(ClimateSensorProto event);

    MotionSensorAvro toMotionSensorAvro(MotionSensorProto event);

    SwitchSensorAvro toSwitchSensorAvro(SwitchSensorProto event);

    TemperatureSensorAvro toTemperatureSensorAvro(TemperatureSensorProto event);
}
