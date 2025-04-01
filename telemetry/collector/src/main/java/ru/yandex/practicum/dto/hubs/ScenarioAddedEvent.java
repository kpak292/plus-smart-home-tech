package ru.yandex.practicum.dto.hubs;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class ScenarioAddedEvent extends HubEvent {
    @Min(value = 3)
    @Max(value = 2147483647)
    String name;
    List<ScenarioCondition> conditions;
    List<DeviceAction> actions;

    @Override
    public HubEventType getType() {
        return HubEventType.SCENARIO_ADDED;
    }
}
