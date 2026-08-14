package org.example.hotel.controller;

import org.example.hotel.model.Reservation;
import org.example.hotel.service.ReservationService;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

// CONTRÔLEUR REST DÉSACTIVÉ - Conversion en gRPC complète
// @RestController
// @RequestMapping("/api/reservation")
public class ReservationController {

    private final ReservationService service;

    public ReservationController(ReservationService service) {
        this.service = service;
    }

    @PostMapping("/reserverAuto")
    public Reservation reserverAuto(@RequestParam Long agenceId,
                                    @RequestParam String nomClient,
                                    @RequestParam String prenomClient,
                                    @RequestParam String email,
                                    @RequestParam String numeroCarte,
                                    @RequestParam String cvc,
                                    @RequestParam double prixTotal,
                                    @RequestParam int nombreNuits,
                                    @RequestParam int nbPersonnes,
                                    @RequestParam String ville,
                                    @RequestParam long dateDebut,
                                    @RequestParam long dateFin) {

        return service.reserverChambreAuto(
                agenceId,
                nomClient,
                prenomClient,
                new Date(dateDebut),
                new Date(dateFin),
                email,
                numeroCarte,
                cvc,
                prixTotal,
                nombreNuits,
                nbPersonnes,
                ville
        );
    }

    @DeleteMapping("/annuler")
    public boolean annuler(@RequestParam String email, @RequestParam String reference) {
        return service.annulerReservation(email, reference);
    }
}
