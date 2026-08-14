//package org.example.hotel.main;
//
//import org.example.hotel.model.*;
//import org.example.hotel.repository.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//import java.util.Calendar;
//import java.util.Date;
//
//@Component
//public class ReservationInitializer implements CommandLineRunner {
//
//    private static final Logger logger = LoggerFactory.getLogger(ReservationInitializer.class);
//
//    private final ClientRepository clientRepository;
//    private final ChambreRepository chambreRepository;
//    private final AgenceRepository agenceRepository;
//    private final ReservationRepository reservationRepository;
//
//    public ReservationInitializer(ClientRepository clientRepository,
//                                  ChambreRepository chambreRepository,
//                                  AgenceRepository agenceRepository,
//                                  ReservationRepository reservationRepository) {
//        this.clientRepository = clientRepository;
//        this.chambreRepository = chambreRepository;
//        this.agenceRepository = agenceRepository;
//        this.reservationRepository = reservationRepository;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        logger.info("Initialisation des réservations...");
//
//        // ----------------------
//        // 1️⃣ Clients
//        // ----------------------
//        Client client1 = new Client();
//        client1.setNom("Dupont");
//        client1.setPrenom("Alice");
//        clientRepository.save(client1);
//
//        Client client2 = new Client();
//        client2.setNom("Martin");
//        client2.setPrenom("Bob");
//        clientRepository.save(client2);
//
//        logger.info("Clients créés: {} et {}", client1.getNom(), client2.getNom());
//
//        // ----------------------
//        // 2️⃣ Agence
//        // ----------------------
//        Agence agence = agenceRepository.findFirstByEmailIgnoreCaseAndPassword("test@agence.com", "1234")
//                .orElseThrow(() -> new RuntimeException("Agence non trouvée"));
//
//        // ----------------------
//        // 3️⃣ Réserver des chambres
//        // ----------------------
//        // Récupérer quelques chambres
//        Chambre chambre1 = chambreRepository.findById(1L).orElse(null);
//        Chambre chambre2 = chambreRepository.findById(2L).orElse(null);
//
//        if (chambre1 != null) {
//            Reservation res1 = new Reservation();
//            res1.setClient(client1);
//            res1.setAgence(agence);
//            res1.setChambre(chambre1);
//
//            // Dates: aujourd'hui + 1 jour pour 2 nuits
//            Calendar cal = Calendar.getInstance();
//            Date debut = cal.getTime();
//            cal.add(Calendar.DAY_OF_MONTH, 2);
//            Date fin = cal.getTime();
//
//            res1.setDateDebut(debut);
//            res1.setDateFin(fin);
//            res1.setNombreNuits(2);
//            res1.setPrixTotal(chambre1.getPrix() * 2);
//            res1.setReference("RES-A1");
//
//            reservationRepository.save(res1);
//        }
//
//        if (chambre2 != null) {
//            Reservation res2 = new Reservation();
//            res2.setClient(client2);
//            res2.setAgence(agence);
//            res2.setChambre(chambre2);
//
//            Calendar cal = Calendar.getInstance();
//            cal.add(Calendar.DAY_OF_MONTH, 3);
//            Date debut = cal.getTime();
//            cal.add(Calendar.DAY_OF_MONTH, 1);
//            Date fin = cal.getTime();
//
//            res2.setDateDebut(debut);
//            res2.setDateFin(fin);
//            res2.setNombreNuits(1);
//            res2.setPrixTotal(chambre2.getPrix() * 1);
//            res2.setReference("RES-B1");
//
//            reservationRepository.save(res2);
//        }
//
//        logger.info("Réservations initialisées ✅");
//    }
//}
