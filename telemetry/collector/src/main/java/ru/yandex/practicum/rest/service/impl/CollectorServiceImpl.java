package ru.yandex.practicum.rest.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.rest.dto.hubs.HubEvent;
import ru.yandex.practicum.rest.dto.sensors.SensorEvent;
import ru.yandex.practicum.rest.mappers.HubEventMapper;
import ru.yandex.practicum.rest.mappers.SensorEventMapper;
import ru.yandex.practicum.rest.service.AvroSerializer;
import ru.yandex.practicum.rest.service.CollectorService;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CollectorServiceImpl implements CollectorService {
    private final KafkaTemplate<String, byte[]> kafkaSensorTemplate;
    private final KafkaTemplate<String, byte[]> kafkaHubTemplate;

    @Value("${collector.topic.telemetry.sensors.v1}")
    private String SENSOR_TOPIC;
    @Value("${collector.topic.telemetry.hubs.v1}")
    private String HUB_TOPIC;

    @Override
    public void createSensorEvent(SensorEvent event) {
        SensorEventAvro eventAvro = SensorEventMapper.INSTANCE.toSensorEventAvro(event);
        try {
            AvroSerializer<SensorEventAvro> serializer = new AvroSerializer<>();
            byte[] serializedData = serializer.serialize(eventAvro);
            kafkaSensorTemplate.send(SENSOR_TOPIC, serializedData);  // Send byte[] payload
        } catch (IOException e) {
            throw new RuntimeException("Failed to serialize SensorEvent", e);
        }

    }

    @Override
    public void createHubEvent(HubEvent event) {
        HubEventAvro eventAvro = HubEventMapper.INSTANCE.toHubEventAvro(event);
        try {
            AvroSerializer<HubEventAvro> serializer = new AvroSerializer<>();
            byte[] serializedData = serializer.serialize(eventAvro);
            kafkaHubTemplate.send(HUB_TOPIC, serializedData);  // Send byte[] payload
        } catch (IOException e) {
            throw new RuntimeException("Failed to serialize HubEvent", e);
        }

    }
}
