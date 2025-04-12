package ru.yandex.practicum.rest.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.yandex.practicum.kafka.telemetry.event.*;
import ru.yandex.practicum.rest.dto.hubs.*;

@Mapper
public interface HubEventMapper {
    HubEventMapper INSTANCE = Mappers.getMapper(HubEventMapper.class);

    @Mapping(target = "payload", source = "event")
    HubEventAvro toHubEventAvro(HubEvent event);

    @Mapping(target = "type", source = "deviceType")
    DeviceAddedEventAvro toDeviceAddedEventAvro(DeviceAddedEvent event);

    DeviceRemovedEventAvro toDeviceRemovedEventAvro(DeviceRemovedEvent event);

    ScenarioAddedEventAvro toScenarioAddedEventAvro(ScenarioAddedEvent event);

    ScenarioRemovedEventAvro toScenarioRemovedEventAvro(ScenarioRemovedEvent event);

    default Object toPayload(HubEvent event) {
        return switch (event) {
            case DeviceAddedEvent dae -> toDeviceAddedEventAvro(dae);
            case DeviceRemovedEvent dre -> toDeviceRemovedEventAvro(dre);
            case ScenarioAddedEvent sae -> toScenarioAddedEventAvro(sae);
            case ScenarioRemovedEvent sre -> toScenarioRemovedEventAvro(sre);
            default -> null;
        };
    }
}
