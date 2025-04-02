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
public interface HubEventMapper {
    HubEventMapper INSTANCE = Mappers.getMapper(HubEventMapper.class);

    @Mapping(target = "payload", source = "event")
    HubEventAvro toHubEventAvro(CollectHubEventRequest event);

    @Mapping(target = "type", source = "deviceType")
    DeviceAddedEventAvro toDeviceAddedEventAvro(DeviceAddedEvent event);

    DeviceRemovedEventAvro toDeviceRemovedEventAvro(DeviceRemovedEvent event);

    ScenarioAddedEventAvro toScenarioAddedEventAvro(ScenarioAddedEvent event);

    ScenarioRemovedEventAvro toScenarioRemovedEventAvro(ScenarioRemovedEvent event);

    default Object toPayload(CollectHubEventRequest event) {
        return switch (event) {
            case DeviceAddedEvent dae -> toDeviceAddedEventAvro(dae);
            case DeviceRemovedEvent dre -> toDeviceRemovedEventAvro(dre);
            case ScenarioAddedEvent sae -> toScenarioAddedEventAvro(sae);
            case ScenarioRemovedEvent sre -> toScenarioRemovedEventAvro(sre);
            default -> null;
        };
    }

    default Instant toInstant(OffsetDateTime timestamp) {
        return timestamp.toInstant();
    }
}
