package ru.yandex.practicum.service;

import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

public interface SensorSnapshotService {
    void processSnapshot(SensorsSnapshotAvro sensorsSnapshotAvro);
}
