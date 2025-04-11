package ru.yandex.practicum.service;

import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.rest.dto.hubs.HubEvent;
import ru.yandex.practicum.rest.dto.sensors.SensorEvent;

public interface CollectorService {
    void createSensorEvent(SensorEvent event);

    void createHubEvent(HubEvent event);

    void createSensorEvent(SensorEventProto event);

    void createHubEvent(HubEventProto event);
}
