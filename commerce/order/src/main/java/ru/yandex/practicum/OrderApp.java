package ru.yandex.practicum;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableFeignClients
public class OrderApp {
    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(OrderApp.class, args);
    }
}
