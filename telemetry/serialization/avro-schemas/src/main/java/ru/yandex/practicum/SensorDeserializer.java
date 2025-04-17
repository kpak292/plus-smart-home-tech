package ru.yandex.practicum;

import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;

public class SensorDeserializer extends AvroDeserializer<SensorEventAvro> {
    public SensorDeserializer() {
        super(SensorEventAvro.getClassSchema());
    }
}
