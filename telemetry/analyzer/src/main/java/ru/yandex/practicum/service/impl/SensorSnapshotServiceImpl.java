package ru.yandex.practicum.service.impl;

import com.google.protobuf.Timestamp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.yandex.practicum.grpc.telemetry.event.ActionTypeProto;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionProto;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionRequest;
import ru.yandex.practicum.grpc.telemetry.hubrouter.HubRouterControllerGrpc;
import ru.yandex.practicum.kafka.telemetry.event.*;
import ru.yandex.practicum.model.Action;
import ru.yandex.practicum.model.Condition;
import ru.yandex.practicum.model.Scenario;
import ru.yandex.practicum.repository.ScenarioRepository;
import ru.yandex.practicum.service.SensorSnapshotService;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SensorSnapshotServiceImpl implements SensorSnapshotService {
    @GrpcClient("hub-router")
    private HubRouterControllerGrpc.HubRouterControllerBlockingStub hubRouterClient;

    private final ScenarioRepository scenarioRepository;

    @Override
    public void processSnapshot(SensorsSnapshotAvro sensorsSnapshotAvro) {
        List<Scenario> scenarios = scenarioRepository.findByHubId(sensorsSnapshotAvro.getHubId());

        if (CollectionUtils.isEmpty(scenarios)) {
            log.debug("No scenarios found for hub {}", sensorsSnapshotAvro.getHubId());
        } else {
            scenarios.forEach(scenario -> executeScenario(scenario, sensorsSnapshotAvro));
        }
    }

    private void executeScenario(Scenario scenario, SensorsSnapshotAvro sensorsSnapshotAvro) {
        if (scenario.getConditions().stream()
                .allMatch(condition -> checkCondition(condition, sensorsSnapshotAvro))) {
            scenario.getActions().forEach(action -> executeAction(action, sensorsSnapshotAvro.getHubId(), scenario.getName()));
        }
    }

    private boolean checkCondition(Condition condition, SensorsSnapshotAvro snapshot) {
        SensorStateAvro state = snapshot.getSensorsState().get(condition.getSensor().getId());

        return state != null && isInCondition(state, condition);
    }

    private boolean isInCondition(SensorStateAvro state, Condition condition) {
        Object data = state.getData();

        int currentValue = switch (condition.getType()) {
            case MOTION -> ((MotionSensorAvro) data).getMotion() ? 1 : 0;
            case LUMINOSITY -> ((LightSensorAvro) data).getLuminosity();
            case SWITCH -> ((SwitchSensorAvro) data).getState() ? 1 : 0;
            case TEMPERATURE -> ((ClimateSensorAvro) data).getTemperatureC();
            case CO2LEVEL -> ((ClimateSensorAvro) data).getCo2Level();
            case HUMIDITY -> ((ClimateSensorAvro) data).getHumidity();
        };

        return switch (condition.getOperation()) {
            case EQUALS -> currentValue == condition.getValue();
            case GREATER_THAN -> currentValue > condition.getValue();
            case LOWER_THAN -> currentValue < condition.getValue();
        };
    }

    private void executeAction(Action action, String hubId, String scenarioName) {
        DeviceActionRequest request = toDeviceActionRequest(action, hubId, scenarioName);
        if (request != null) {
            hubRouterClient.handleDeviceAction(request);
        }
    }

    private DeviceActionRequest toDeviceActionRequest(Action action, String hubId, String scenarioName) {
        if (hubId == null || scenarioName == null || action == null || action.getSensor() == null){
            log.error("Invalid action: {}", action);
            return null;
        }

        Instant now = Instant.now();
        DeviceActionRequest.Builder builder = DeviceActionRequest.newBuilder();
        builder.setHubId(hubId);
        builder.setScenarioName(scenarioName);
        builder.setAction(DeviceActionProto.newBuilder()
                .setSensorId(action.getSensor().getId())
                .setType(ActionTypeProto.valueOf(action.getType().name()))
                .setValue(action.getValue() == null ? 0 : action.getValue())
                .build());
        builder.setTimestamp(Timestamp.newBuilder()
                .setSeconds(now.getEpochSecond())
                .setNanos(now.getNano())
                .build());

        return builder.build();
    }
}
