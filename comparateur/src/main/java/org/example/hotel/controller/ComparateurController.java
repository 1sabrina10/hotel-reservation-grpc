package org.example.hotel.controller;

import org.example.hotel.model.OffreAgence;
import org.example.hotel.service.ComparateurService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class ComparateurController {

    private final ComparateurService service;

    public ComparateurController(ComparateurService service) {
        this.service = service;
    }

    @GetMapping("/comparateur")
    public String pageComparateur() {
        return "comparateur";
    }


    @GetMapping("/comparateur/offres")
    @ResponseBody
    public List<OffreAgence> getOffres(@RequestParam String email,
                                       @RequestParam  String password,
                                       @RequestParam int nbPersonnes,
                                       @RequestParam String ville,
                                       @RequestParam long dateDebut,
                                       @RequestParam long dateFin) {

        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            System.out.println("Authentification échouée : paramètres manquants");
            return new ArrayList<>();
        }

        Date debut = new Date(dateDebut);
        Date fin = new Date(dateFin);

        List<OffreAgence> offres = service.getOffresAgence(email, password, ville, nbPersonnes, debut, fin);

        return offres;
    }
}

