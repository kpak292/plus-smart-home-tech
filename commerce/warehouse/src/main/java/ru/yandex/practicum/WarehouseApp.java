package ru.yandex.practicum;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableFeignClients
public class WarehouseApp {
    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(WarehouseApp.class, args);
    }
}
