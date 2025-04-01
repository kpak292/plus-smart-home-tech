package ru.yandex.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.dto.hubs.HubEvent;
import ru.yandex.practicum.dto.sensors.SensorEvent;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.mappers.HubEventMapper;
import ru.yandex.practicum.mappers.SensorEventMapper;
import ru.yandex.practicum.service.CollectorService;

@Service
@RequiredArgsConstructor
public class CollectorServiceImpl implements CollectorService {
    private final KafkaTemplate<String, SensorEventAvro> kafkaSensorTemplate;
    private final KafkaTemplate<String, HubEventAvro> kafkaHubTemplate;

    @Value("${collector.topic.telemetry.sensors.v1}")
    private String SENSOR_TOPIC;
    @Value("${collector.topic.telemetry.hubs.v1}")
    private String HUB_TOPIC;

    @Override
    public void createSensorEvent(SensorEvent event) {
        SensorEventAvro eventAvro = SensorEventMapper.INSTANCE.toSensorEventAvro(event);
        kafkaSensorTemplate.send(SENSOR_TOPIC, eventAvro);
    }

    @Override
    public void createHubEvent(HubEvent event) {
        HubEventAvro eventAvro = HubEventMapper.INSTANCE.toHubEventAvro(event);
        kafkaHubTemplate.send(HUB_TOPIC, eventAvro);
    }
}
