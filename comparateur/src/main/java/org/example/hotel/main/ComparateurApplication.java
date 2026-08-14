package org.example.hotel.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
        "org.example.hotel.controller",
        "org.example.hotel.service"
})
@EnableJpaRepositories(basePackages = "org.example.hotel.repository")
@EntityScan(basePackages = "org.example.hotel.model")
public class ComparateurApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComparateurApplication.class, args);
    }
}