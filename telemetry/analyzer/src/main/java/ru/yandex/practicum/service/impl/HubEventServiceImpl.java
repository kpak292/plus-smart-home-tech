package ru.yandex.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.kafka.telemetry.event.*;
import ru.yandex.practicum.mappers.HubEventMapper;
import ru.yandex.practicum.repository.ScenarioRepository;
import ru.yandex.practicum.repository.SensorRepository;
import ru.yandex.practicum.service.HubEventService;

@Slf4j
@Service
@RequiredArgsConstructor
public class HubEventServiceImpl implements HubEventService {
    private final SensorRepository sensorRepository;
    private final ScenarioRepository scenarioRepository;

    @Override
    public void processHubEvent(HubEventAvro event) {
        switch (event.getPayload()) {
            case DeviceAddedEventAvro dae -> addDevice(dae, event.getHubId());
            case DeviceRemovedEventAvro dre -> removeDevice(dre, event.getHubId());
            case ScenarioAddedEventAvro sae -> addScenario(sae, event.getHubId());
            case ScenarioRemovedEventAvro sre -> scenarioRepository.deleteByName(sre.getName());
            default -> System.out.println("Unknown event: " + event.getPayload());
        }
    }

    void addDevice(DeviceAddedEventAvro event, String hubId) {
        if (sensorRepository.findByIdAndHubId(event.getId(), hubId).isPresent()) {
            log.debug("Device {} in Hub {} already exists", event.getId(), hubId);
        } else {
            sensorRepository.save(HubEventMapper.INSTANCE.toSensor(event, hubId));
        }
    }

    void removeDevice(DeviceRemovedEventAvro event, String hubId) {
        sensorRepository.findByIdAndHubId(event.getId(), hubId)
                .ifPresentOrElse(
                        sensorRepository::delete,
                        () -> log.debug("Device {} in Hub {} not found for delete", event.getId(), hubId)
                );
    }

    void addScenario(ScenarioAddedEventAvro scenarioAddedEventAvro, String hubId) {
        scenarioRepository.save(HubEventMapper.INSTANCE.toScenario(scenarioAddedEventAvro,hubId));
    }
}
