package ru.yandex.practicum.config;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.dto.CollectHubEventRequest;
import ru.yandex.practicum.dto.CollectSensorEventRequest;

import java.util.Map;

@Configuration
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer addMixIns() {
        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder.mixIns(
                Map.of(CollectHubEventRequest.class, CollectHubEventRequestMixin.class,
                        CollectSensorEventRequest.class, CollectSensorEventRequestMixin.class));
    }
}
