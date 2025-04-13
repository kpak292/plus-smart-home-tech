package ru.yandex.practicum;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AggregationStarter {

    private final KafkaTemplate<String, byte[]> kafkaTemplate;
    private final AggregatorService aggregatorService;

    @Value("${kafka.topic.sensor}")
    private String sensorTopic;

    @Value("${kafka.topic.snapshot}")
    private String snapshotTopic;

    @KafkaListener(topics = "${kafka.topic.sensor}", groupId = "${kafka.group.id}")
    public void listenSensors(SensorEventAvro event) {
        Optional<SensorsSnapshotAvro> snapshot = aggregatorService.updateState(event);
        snapshot.ifPresent(snap -> {
            try {
                AvroSerializer<SensorsSnapshotAvro> serializer = new AvroSerializer<>();
                byte[] serializedData = serializer.serialize(snap);
                kafkaTemplate.send(snapshotTopic, serializedData);
                log.info("Отправили снимок состояния в Kafka для hubId={}", snap.getHubId());
            } catch (IOException e) {
                throw new RuntimeException("Failed serialize snapshot", e);
            }
        });

    }
}

