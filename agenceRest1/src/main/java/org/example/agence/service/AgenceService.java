package org.example.agence.service;

import org.example.agence.model.*;
import org.example.agence.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AgenceService {

    @Autowired
    private AgenceRepository agenceRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ChambreRepository chambreRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HotelAgenceRepository hotelAgenceRepository;

    @Autowired
    private HotelGrpcClientService hotelGrpcClientService;

    private final List<OffreAgence> offresMemoire = new ArrayList<>();

    public boolean authentifierAgence(String email, String password) {
        return agenceRepository.findFirstByEmailIgnoreCaseAndPassword(email, password).isPresent();
    }

    public List<OffreAgence> consulterOffres(String email, String password,
                                             int nbPersonnes, String ville,
                                             Date dateDebut, Date dateFin, Long agenceId) {

        Agence agence = agenceRepository.findFirstByEmailIgnoreCaseAndPassword(email, password).orElse(null);
        if (agence == null) {
            System.out.println("Agence non trouvée !");
            return Collections.emptyList();
        }

        // Récupération des chambres via gRPC
        List<ChambreDTO> chambres1 = hotelGrpcClientService.getChambreDispo(email, password, nbPersonnes, ville, dateDebut, dateFin);
        List<ChambreDTO> chambres2 = hotelGrpcClientService.getChambreDispo2(email, password, nbPersonnes, ville, dateDebut, dateFin);

        List<ChambreDTO> chambres = chambres1 != null ? new ArrayList<>(chambres1) : new ArrayList<>();
        if(chambres2 != null) chambres.addAll(chambres2);

        offresMemoire.clear();

        for (ChambreDTO c : chambres) {
            hotelRepository.findFirstByNom(c.getNomHotel()).ifPresent(hotel -> {
                HotelAgence ha = hotelAgenceRepository.findByHotelIdAndAgenceId(hotel.getId(), agenceId);
                if (ha != null) {
                    double reduction = ha.getReduction();

                    c.setDateDebut(dateDebut);
                    c.setDateFin(dateFin);

                    int nombreNuits = Math.max(1, (int) ((dateFin.getTime() - dateDebut.getTime()) / (1000*60*60*24)));
                    c.setNombreNuits(nombreNuits);

                    double prixFinalParNuit = c.getPrix() * (1 - reduction);
                    c.setPrix(prixFinalParNuit);
                    c.setPrixTotal(prixFinalParNuit * nombreNuits);
                    c.setReduction(reduction);

                    OffreAgence offre = new OffreAgence();
                    offre.setOffreId(System.currentTimeMillis() + (long)(Math.random()*1000));
                    offre.setAgenceId(agenceId);
                    offre.setChambreDTO(c);

                    offresMemoire.add(offre);
                }
            });
        }

        return offresMemoire;
    }

    public OffreAgence getOffreById(Long agenceId, Long offreId) {
        return offresMemoire.stream()
                .filter(o -> o.getAgenceId().equals(agenceId) && o.getOffreId().equals(offreId))
                .findFirst().orElse(null);
    }


    public Reservation reserverChambre(
            Long agenceId,
            Long chambreId,
            String nomClient,
            String prenomClient,
            String email,
            String numeroCarte,
            String cvc,
            double prixTotal,
            int nombreNuits,
            Date dateDebut,
            Date dateFin,
            String reference) {
        // Vérifier l'agence
        Agence agence = agenceRepository.findById(agenceId)
                .orElseThrow(() -> new RuntimeException("Agence introuvable"));

        // Créer ou récupérer le client
        Client client = clientRepository.findByEmail(email).stream().findFirst().orElseGet(() -> {
            Client c = new Client();
            c.setNom(nomClient);
            c.setPrenom(prenomClient);
            c.setEmail(email);
            return clientRepository.save(c);
        });

        // Récupérer la chambre
        Chambre chambre = chambreRepository.findById(chambreId)
                .orElseThrow(() -> new RuntimeException("Chambre introuvable"));

        // Créer la réservation
        Reservation reservation = new Reservation();
        reservation.setAgenceId(agenceId);
        reservation.setClient(client);
        reservation.setChambre(chambre);
        reservation.setPrixTotal(prixTotal);
        reservation.setNombreNuits(nombreNuits);
        reservation.setDateDebut(dateDebut);
        reservation.setDateFin(dateFin);
        reservation.setReference(UUID.randomUUID().toString());

        return reservationRepository.save(reservation);
    }

    public void annulerReservation(String reference) {
        Reservation reservation = reservationRepository.findByReference(reference)
                .orElseThrow(() -> new RuntimeException("Réservation introuvable"));
        reservationRepository.delete(reservation);
    }

    public Reservation getReservationByReference(String reference) {
        return reservationRepository.findByReference(reference)
                .orElseThrow(() -> new RuntimeException("Réservation introuvable"));
    }


}
