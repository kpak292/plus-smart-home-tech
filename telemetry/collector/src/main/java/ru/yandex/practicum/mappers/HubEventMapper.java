package ru.yandex.practicum.mappers;

import com.google.protobuf.Timestamp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;
import org.mapstruct.factory.Mappers;
import ru.yandex.practicum.grpc.telemetry.event.*;
import ru.yandex.practicum.kafka.telemetry.event.*;
import ru.yandex.practicum.rest.dto.hubs.*;
import ru.yandex.practicum.rest.dto.hubs.HubEvent;

import java.time.Instant;

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

    @Mapping(target = "payload", expression = "java( toPayload( event ) )")
    HubEventAvro toHubEventAvro(HubEventProto event);

    default Object toPayload(HubEventProto event) {
        if (event.hasDeviceAdded()) {
            return toDeviceAddedEventAvro(event.getDeviceAdded());
        } else if (event.hasDeviceRemoved()) {
            return toDeviceRemovedEventAvro(event.getDeviceRemoved());
        } else if (event.hasScenarioAdded()) {
            return toScenarioAddedEventAvro(event.getScenarioAdded());
        } else if (event.hasScenarioRemoved()) {
            return toScenarioRemovedEventAvro(event.getScenarioRemoved());
        } else {
            return null;
        }
    }

    default Instant toInstant(Timestamp timestamp) {
        return Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos());
    }

    @ValueMapping(source = "UNRECOGNIZED", target = MappingConstants.NULL)
    DeviceAddedEventAvro toDeviceAddedEventAvro(DeviceAddedEventProto event);

    DeviceRemovedEventAvro toDeviceRemovedEventAvro(DeviceRemovedEventProto event);

    @Mapping(target = "conditions", source = "conditionList")
    @Mapping(target = "actions", source = "actionList")
    @ValueMapping(source = "UNRECOGNIZED", target = MappingConstants.NULL)
    ScenarioAddedEventAvro toScenarioAddedEventAvro(ScenarioAddedEventProto event);

    ScenarioRemovedEventAvro toScenarioRemovedEventAvro(ScenarioRemovedEventProto event);

    default ScenarioConditionAvro scenarioConditionProtoToScenarioConditionAvro(ScenarioConditionProto scenarioConditionProto) {
        if (scenarioConditionProto == null) {
            return null;
        }

        ScenarioConditionAvro.Builder scenarioConditionAvro = ScenarioConditionAvro.newBuilder();

        scenarioConditionAvro.setSensorId(scenarioConditionProto.getSensorId());
        scenarioConditionAvro.setType(ConditionTypeAvro.valueOf(scenarioConditionProto.getType().name()));
        scenarioConditionAvro.setOperation(ConditionOperationAvro.valueOf(scenarioConditionProto.getOperation().name()));

        if (scenarioConditionProto.hasIntValue()) {
            scenarioConditionAvro.setValue(scenarioConditionProto.getIntValue());
        }

        if (scenarioConditionProto.hasBoolValue()) {
            scenarioConditionAvro.setValue(scenarioConditionProto.getBoolValue());
        }

        return scenarioConditionAvro.build();
    }
}
