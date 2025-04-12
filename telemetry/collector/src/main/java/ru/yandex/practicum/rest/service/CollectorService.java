package ru.yandex.practicum.rest.service;

import ru.yandex.practicum.rest.dto.hubs.HubEvent;
import ru.yandex.practicum.rest.dto.sensors.SensorEvent;

public interface CollectorService {
    void createSensorEvent(SensorEvent event);

    void createHubEvent(HubEvent event);
}
