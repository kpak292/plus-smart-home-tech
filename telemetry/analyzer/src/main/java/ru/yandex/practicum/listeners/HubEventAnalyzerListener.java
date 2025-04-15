package ru.yandex.practicum.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.service.HubEventService;

@Slf4j
@Component
@RequiredArgsConstructor
public class HubEventAnalyzerListener {
    private final HubEventService hubEventService;

    private final KafkaTemplate<String, HubEventAvro> kafkaTemplate;

    @KafkaListener(topics = "${kafka.topic.hubs.v1}", groupId = "${spring.kafka.consumer.group-id}")
    public void listenSensors(HubEventAvro event) {
        log.debug("Received event: {}", event);
        hubEventService.processHubEvent(event);
    }
}
