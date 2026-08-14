package org.example.agence.main;

import org.example.agence.service.AgenceService;
import org.example.agence.service.HotelGrpcClientService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.swing.SwingUtilities;

@SpringBootApplication(scanBasePackages = "org.example.agence")
@EntityScan(basePackages = "org.example.agence.model")
@EnableJpaRepositories(basePackages = "org.example.agence.repository")
public class AgenceApplication {

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");

        // Démarrage Spring Boot
        var context = SpringApplication.run(AgenceApplication.class, args);

        // Récupération des beans Spring
        AgenceService agenceService = context.getBean(AgenceService.class);
        HotelGrpcClientService hotelGrpcClientService = context.getBean(HotelGrpcClientService.class);

        // Lancement Swing
        SwingUtilities.invokeLater(() -> {
            new SOAP_Interface(agenceService, hotelGrpcClientService).setVisible(true);
        });
    }
}

/*
package org.example.agence.main;

import org.example.agence.service.AgenceService;
import org.example.agence.service.HotelGrpcClientService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.swing.SwingUtilities;

@SpringBootApplication(scanBasePackages = "org.example.agence")
@EntityScan(basePackages = "org.example.agence.model")
@EnableJpaRepositories(basePackages = "org.example.agence.repository")
public class AgenceApplication {

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");

        // 1️⃣ Démarrage Spring Boot
        var context = SpringApplication.run(AgenceApplication.class, args);

        // 2️⃣ Récupérer les beans Spring pour les passer à Swing
        AgenceService agenceService = context.getBean(AgenceService.class);
        HotelGrpcClientService hotelGrpcClientService = context.getBean(HotelGrpcClientService.class);

        // 3️⃣ Lancement Swing
        SwingUtilities.invokeLater(() -> {
            new RechercheChambresUI(agenceService, hotelGrpcClientService).setVisible(true);
        });
    }
}
*/
