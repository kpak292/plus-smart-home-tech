package ru.yandex.practicum.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;
import ru.yandex.practicum.service.SensorSnapshotService;

@Slf4j
@Component
@RequiredArgsConstructor
public class SensorSnapshotListener {
    private final SensorSnapshotService sensorSnapshotService;

    @KafkaListener(
            topics = "${kafka.topic.snapshots.v1}",
            groupId = "${spring.kafka.snapshot-consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactorySnapshots"
    )
    public void listenSnapshots(SensorsSnapshotAvro sensorsSnapshotAvro) {
        log.info("Received snapshot: {}", sensorsSnapshotAvro);
        sensorSnapshotService.processSnapshot(sensorsSnapshotAvro);
    }
}
