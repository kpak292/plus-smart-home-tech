package ru.yandex.practicum.rest.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.rest.dto.hubs.HubEvent;
import ru.yandex.practicum.rest.dto.sensors.SensorEvent;
import ru.yandex.practicum.rest.service.CollectorService;

@Slf4j
@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class CollectorController {
    private final CollectorService collectorService;

    @PostMapping(path = "/sensors")
    @ResponseStatus(HttpStatus.OK)
    public void createSensorEvent(@RequestBody @Valid SensorEvent sensorEvent) {
        log.debug("Event Sensor: {}", sensorEvent);
        collectorService.createSensorEvent(sensorEvent);
    }

    @PostMapping(path = "/hubs")
    @ResponseStatus(HttpStatus.OK)
    public void createHubEvent(@RequestBody @Valid HubEvent hubEvent) {
        log.debug("Event Hub: {}", hubEvent);
        collectorService.createHubEvent(hubEvent);
    }
}
