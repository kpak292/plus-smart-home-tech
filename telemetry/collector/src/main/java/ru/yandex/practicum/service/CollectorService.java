package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.CollectHubEventRequest;
import ru.yandex.practicum.dto.CollectSensorEventRequest;

public interface CollectorService {
    void createSensorEvent(CollectSensorEventRequest event);

    void createHubEvent(CollectHubEventRequest event);
}
