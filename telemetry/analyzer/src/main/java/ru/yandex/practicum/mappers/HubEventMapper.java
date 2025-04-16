package ru.yandex.practicum.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.yandex.practicum.kafka.telemetry.event.DeviceActionAvro;
import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioConditionAvro;
import ru.yandex.practicum.model.Action;
import ru.yandex.practicum.model.Condition;
import ru.yandex.practicum.model.Scenario;
import ru.yandex.practicum.model.Sensor;

import java.util.List;

@Mapper
public interface HubEventMapper {
    HubEventMapper INSTANCE = Mappers.getMapper(HubEventMapper.class);

    Sensor toSensor(DeviceAddedEventAvro deviceAddedEventAvro, String hubId);

    Scenario toScenario(ScenarioAddedEventAvro scenarioAddedEventAvro, String hubId);

    default Action toAction(DeviceActionAvro deviceActionAvro) {
        Integer value = null;
        if (deviceActionAvro.getValue() instanceof Integer intValue) {
            value = intValue;
        }

        return Action.builder()
                .sensor(toSensor(deviceActionAvro.getSensorId()))
                .value(value)
                .type(deviceActionAvro.getType())
                .build();
    }

    default Condition toCondition(ScenarioConditionAvro scenarioConditionAvro) {
        int value = 0;
        if (scenarioConditionAvro.getValue() instanceof Integer intValue) {
            value = intValue;
        }
        if (scenarioConditionAvro.getValue() instanceof Boolean boolValue) {
            value = boolValue ? 1 : 0;
        }

        return Condition.builder()
                .sensor(toSensor(scenarioConditionAvro.getSensorId()))
                .value(value)
                .type(scenarioConditionAvro.getType())
                .operation(scenarioConditionAvro.getOperation())
                .build();
    }

    Sensor toSensor(String id);
}
