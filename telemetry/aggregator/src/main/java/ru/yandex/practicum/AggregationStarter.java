package ru.yandex.practicum;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AggregationStarter {

    private final KafkaTemplate<String, SensorsSnapshotAvro> kafkaTemplate;
    private final AggregatorService aggregatorService;

    @Value("${kafka.topic.sensors.v1}")
    private String sensorTopic;

    @Value("${kafka.topic.snapshots.v1}")
    private String snapshotTopic;

    @KafkaListener(topics = "${kafka.topic.sensors.v1}", groupId = "${spring.kafka.consumer.group-id}")
    public void listenSensors(SensorEventAvro event) {
        Optional<SensorsSnapshotAvro> snapshot = aggregatorService.updateState(event);
        snapshot.ifPresent(snap -> {
            kafkaTemplate.send(snapshotTopic, snap);
            log.info("Отправили снимок состояния в Kafka для hubId={}", snap.getHubId());
        });

    }
}

