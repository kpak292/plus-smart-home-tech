package ru.yandex.practicum.dto.hubs;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class ScenarioCondition {
    String sensorId;
    ConditionType type;
    ConditionOperation operation;
    Integer value;
}
