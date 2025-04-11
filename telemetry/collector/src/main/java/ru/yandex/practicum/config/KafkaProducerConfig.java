package ru.yandex.practicum.config;

import com.google.protobuf.Message;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaProducerConfig {

    @Value("spring.kafka.producer.bootstrap-servers")
    private String bootstrapServers;

    // Protobuf Producer Configuration
    @Bean
    public ProducerFactory<String, Message> grpcProducerFactory(
            @Value("spring.kafka.grpc-producer.key-serializer") String keySerializer,
            @Value("spring.kafka.grpc-producer.value-serializer") String valueSerializer) {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Message> grpcKafkaTemplate(ProducerFactory<String, Message> grpcProducerFactory) {
        return new KafkaTemplate<>(grpcProducerFactory);
    }

    // String Producer Configuration
    @Bean
    public ProducerFactory<String, byte[]> stringProducerFactory(
            @Value("spring.kafka.byte-producer.key-serializer") String keySerializer,
            @Value("spring.kafka.byte-producer.value-serializer") String valueSerializer) {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, byte[]> byteKafkaTemplate(ProducerFactory<String, byte[]> byteProducerFactory) {
        return new KafkaTemplate<>(byteProducerFactory);
    }
}

