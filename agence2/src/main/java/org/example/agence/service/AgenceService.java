package org.example.agence.service;

import com.itextpdf.text.*;
import org.example.agence.model.*;
import org.example.agence.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
@Service
public class AgenceService {

    @Autowired
    private AgenceRepository agenceRepository;
    private ClientRepository clientRepository;
    private ReservationRepository reservationRepository;
    private ChambreRepository chambreRepository;
    private HotelRepository hotelRepository;
    private HotelAgenceRepository hotelAgenceRepository;
    
    @Autowired
    private HotelGrpcClientService hotelGrpcClientService;

    private final List<OffreAgence> offresMemoire = new ArrayList<>();

    public AgenceService(AgenceRepository agenceRepository,
                         ClientRepository clientRepository, ReservationRepository reservationRepository,
                         ChambreRepository chambreRepository, HotelRepository hotelRepository,
                         HotelAgenceRepository hotelAgenceRepository) {

        this.agenceRepository = agenceRepository;
        this.clientRepository = clientRepository;
        this.reservationRepository = reservationRepository;
        this.chambreRepository = chambreRepository;
        this.hotelRepository = hotelRepository;
        this.hotelAgenceRepository = hotelAgenceRepository;
    }

    public boolean authentifierAgence(String email, String password) {
        // L'authentification est maintenant gérée directement par le service
        // car les hôtels vérifient l'authentification lors de la consultation des disponibilités
        return true;
    }

    public List<OffreAgence> consulterOffres(String email, String password,
                                             int nbPersonnes, String ville,
                                             Date dateDebut, Date dateFin, Long agenceId) {


        Agence agence = agenceRepository.findFirstByEmailIgnoreCaseAndPassword(email, password)
                .orElse(null);

        if (agence == null) {
            System.out.println("Agence non trouvée en BDD !");
            return new ArrayList<>();
        }


        // hotel1 via gRPC
        List<ChambreDTO> chambresDispo = hotelGrpcClientService.getChambreDispo(email, password, nbPersonnes, ville, dateDebut, dateFin);

        // hotel 2 via gRPC
        List<ChambreDTO> chambresDispo2 = hotelGrpcClientService.getChambreDispo2(email, password, nbPersonnes, ville, dateDebut, dateFin);

        chambresDispo.addAll(chambresDispo2);

        System.out.println("Nombre de chambres récupérées : " + chambresDispo.size());

        offresMemoire.clear();

        for (ChambreDTO c : chambresDispo) {

            Optional<Hotel> optionalHotel = hotelRepository.findFirstByNom(c.getNomHotel());
            if (!optionalHotel.isPresent()) {
                System.out.println("Impossible de retrouver l'hôtel pour " + c.getNomHotel());
                continue;
            }

            Hotel hotel = optionalHotel.get();
            HotelAgence ha = hotelAgenceRepository.findByHotelIdAndAgenceId(hotel.getId(), agenceId);
            if (ha == null) {
                System.out.println("Hotel NON autorisé pour agenceId=" + agenceId + " → " + hotel.getNom());
                continue;
            }
            double reduction = ha.getReduction();

            c.setDateDebut(dateDebut);
            c.setDateFin(dateFin);

            long diffMillis = dateFin.getTime() - dateDebut.getTime();
            int nombreNuits = (int) (diffMillis / (1000 * 60 * 60 * 24));
            if (nombreNuits < 1) nombreNuits = 1;
            c.setNombreNuits(nombreNuits);

            double prixFinalParNuit = c.getPrix() * (1 - reduction);
            double prixTotal = prixFinalParNuit * nombreNuits;
            c.setPrix(prixFinalParNuit);
            c.setPrixTotal(prixTotal);
            c.setReduction(reduction);

            Long offreId = System.currentTimeMillis() + (long) (Math.random() * 1000);

            OffreAgence offre = new OffreAgence();
            offre.setOffreId(offreId);
            offre.setAgenceId(agenceId);
            offre.setChambreDTO(c);

            offresMemoire.add(offre);
        }

        return offresMemoire;
    }

    public OffreAgence getOffreById(Long agenceId, Long offreId) {
        return offresMemoire.stream()
                .filter(o -> o.getAgenceId().equals(agenceId) && o.getOffreId().equals(offreId))
                .findFirst()
                .orElse(null);
    }


    public Reservation reserverChambre(ReservationRequest request) {
        try {

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date dateDebut = formatter.parse(request.getDateDebut());
            Date dateFin = formatter.parse(request.getDateFin());

            if (!dateFin.after(dateDebut)) {
                throw new RuntimeException("La date de fin doit être après la date de début.");
            }

            int nombreNuits = request.getNombreNuits() > 0 ? request.getNombreNuits() :
                    (int) ((dateFin.getTime() - dateDebut.getTime()) / (1000 * 60 * 60 * 24));
            if (nombreNuits < 1) nombreNuits = 1;
            request.setNombreNuits(nombreNuits);

            List<Client> clientsExistants = clientRepository.findByEmail(request.getEmail());
            Client savedClient;
            if (clientsExistants.isEmpty()) {

                Client client = new Client();
                client.setNom(request.getNomClient());
                client.setPrenom(request.getPrenomClient());
                client.setEmail(request.getEmail());
                client.setNumeroCarte(request.getNumeroCarte());
                client.setCvc(request.getCvc());
                savedClient = clientRepository.save(client);
            } else {

                savedClient = clientsExistants.get(0);
            }

            Chambre chambre = chambreRepository.findById(request.getChambreId())
                    .orElseThrow(() -> new RuntimeException("Chambre introuvable"));

            HotelAgence ha = hotelAgenceRepository.findByHotelIdAndAgenceId(chambre.getHotel().getId(), request.getAgenceId());
            double reduction = (ha != null) ? ha.getReduction() : 0.0;
            double prixFinalParNuit = chambre.getPrix() * (1 - reduction);
            double prixTotal = prixFinalParNuit * nombreNuits;


            Reservation reservation = new Reservation();
            reservation.setClient(savedClient);
            reservation.setChambre(chambre);
            reservation.setAgenceId(request.getAgenceId());
            reservation.setPrixTotal(prixTotal);
            reservation.setReference("RES-" + System.currentTimeMillis());
            reservation.setNombreNuits(nombreNuits);
            reservation.setDateDebut(dateDebut);
            reservation.setDateFin(dateFin);

            return reservationRepository.save(reservation);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean annulerReservation(String reference) {
        Optional<Reservation> reservationOpt = reservationRepository.findByReference(reference);

        if (reservationOpt.isPresent()) {
            reservationRepository.delete(reservationOpt.get());
            return true;
        }

        return false;
    }

    public Reservation getReservationByReference(String reference) {
        return reservationRepository.findByReference(reference).orElse(null);
    }

}
