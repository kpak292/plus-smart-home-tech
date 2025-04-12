package ru.yandex.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.mappers.HubEventMapper;
import ru.yandex.practicum.mappers.SensorEventMapper;
import ru.yandex.practicum.rest.dto.hubs.HubEvent;
import ru.yandex.practicum.rest.dto.sensors.SensorEvent;
import ru.yandex.practicum.service.AvroSerializer;
import ru.yandex.practicum.service.CollectorService;

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
        sendSensorEvent(SensorEventMapper.INSTANCE.toSensorEventAvro(event));
    }

    @Override
    public void createHubEvent(HubEvent event) {
        sendHubEvent(HubEventMapper.INSTANCE.toHubEventAvro(event));

    }

    @Override
    public void createSensorEvent(SensorEventProto event) {
        sendSensorEvent(SensorEventMapper.INSTANCE.toSensorEventAvro(event));
    }

    @Override
    public void createHubEvent(HubEventProto event) {
        sendHubEvent(HubEventMapper.INSTANCE.toHubEventAvro(event));
    }


    private void sendHubEvent(HubEventAvro hubEventAvro) {
        try {
            AvroSerializer<HubEventAvro> serializer = new AvroSerializer<>();
            byte[] serializedData = serializer.serialize(hubEventAvro);
            kafkaHubTemplate.send(HUB_TOPIC, serializedData);  // Send byte[] payload
        } catch (IOException e) {
            throw new RuntimeException("Failed to serialize HubEvent", e);
        }
    }


    private void sendSensorEvent(SensorEventAvro sensorEventAvro) {
        try {
            AvroSerializer<SensorEventAvro> serializer = new AvroSerializer<>();
            byte[] serializedData = serializer.serialize(sensorEventAvro);
            kafkaSensorTemplate.send(SENSOR_TOPIC, serializedData);  // Send byte[] payload
        } catch (IOException e) {
            throw new RuntimeException("Failed to serialize SensorEvent", e);
        }
    }

}
