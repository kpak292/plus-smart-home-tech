package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.hubs.HubEvent;
import ru.yandex.practicum.dto.sensors.SensorEvent;

public interface CollectorService {
    void createSensorEvent(SensorEvent event);

    void createHubEvent(HubEvent event);
}
