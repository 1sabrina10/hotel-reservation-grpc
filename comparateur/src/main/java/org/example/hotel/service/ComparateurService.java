package org.example.hotel.service;

import org.example.agence.grpc.*;
import org.example.hotel.model.Adresse;
import org.example.hotel.model.ChambreDTO;
import org.example.hotel.model.OffreAgence;
import org.springframework.stereotype.Service;
import net.devh.boot.grpc.client.inject.GrpcClient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ComparateurService {

    @GrpcClient("agence1")
    private AgenceServiceGrpc.AgenceServiceBlockingStub agence1Client;

    @GrpcClient("agence2")
    private AgenceServiceGrpc.AgenceServiceBlockingStub agence2Client;

    public ComparateurService() {
    }

   public List<OffreAgence> getOffresAgence(String email, String password,
                                             String ville, int nbPersonnes,
                                             Date dateDebut, Date dateFin) {

        System.out.println("Appel au service agence : ville=" + ville + ", nbPersonnes=" + nbPersonnes
                + ", dateDebut=" + dateDebut + ", dateFin=" + dateFin);

       List<OffreAgence> offreAgence1 = getOffreAgence1(email, password, ville, nbPersonnes, dateDebut, dateFin);
       List<OffreAgence> offreAgence2 = getOffreAgence2(email, password, ville, nbPersonnes, dateDebut, dateFin);
       offreAgence1.addAll(offreAgence2);
       return offreAgence1;
   }

    private List<OffreAgence> getOffreAgence1(String email, String password, String ville, int nbPersonnes, Date dateDebut, Date dateFin) {
        try {
            OffreRequest request = OffreRequest.newBuilder()
                    .setEmail(email)
                    .setPassword(password)
                    .setNbPersonnes(nbPersonnes)
                    .setVille(ville)
                    .setDateDebut(dateDebut.getTime())
                    .setDateFin(dateFin.getTime())
                    .setAgenceId(1L)
                    .build();

            OffreResponse response = agence1Client.consulterOffres(request);

            List<OffreAgence> offres = new ArrayList<>();
            for (org.example.agence.grpc.OffreAgence grpcOffre : response.getOffresList()) {
                OffreAgence offre = convertGrpcOffreToModel(grpcOffre, 1L);
                offres.add(offre);
            }

            if (offres.isEmpty()) {
                System.out.println("Aucune offre reçue de l'agence 1");
                return new ArrayList<>();
            }

            for (OffreAgence o : offres) {
                System.out.println("Offre agenceId=" + o.getAgenceId()
                        + ", hotel=" + o.getChambreDTO());
            }

            return offres;

        } catch (Exception e) {
            System.out.println("Erreur lors de l'appel à l'agence 1 : " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private List<OffreAgence> getOffreAgence2(String email, String password, String ville, int nbPersonnes, Date dateDebut, Date dateFin) {
        try {
            OffreRequest request = OffreRequest.newBuilder()
                    .setEmail(email)
                    .setPassword(password)
                    .setNbPersonnes(nbPersonnes)
                    .setVille(ville)
                    .setDateDebut(dateDebut.getTime())
                    .setDateFin(dateFin.getTime())
                    .setAgenceId(2L)
                    .build();

            OffreResponse response = agence2Client.consulterOffres(request);

            List<OffreAgence> offres = new ArrayList<>();
            for (org.example.agence.grpc.OffreAgence grpcOffre : response.getOffresList()) {
                OffreAgence offre = convertGrpcOffreToModel(grpcOffre, 2L);
                offres.add(offre);
            }

            if (offres.isEmpty()) {
                System.out.println("Aucune offre reçue de l'agence 2");
                return new ArrayList<>();
            }

            for (OffreAgence o : offres) {
                System.out.println("Offre agenceId=" + o.getAgenceId()
                        + ", hotel=" + o.getChambreDTO());
            }

            return offres;

        } catch (Exception e) {
            System.out.println("Erreur lors de l'appel à l'agence 2 : " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private OffreAgence convertGrpcOffreToModel(org.example.agence.grpc.OffreAgence grpcOffre, Long agenceId) {
        org.example.agence.grpc.ChambreDTO grpcChambre = grpcOffre.getChambreDto();
        
        Adresse adresse = new Adresse();
        adresse.setPays(grpcChambre.getAdresse().getPays());
        adresse.setVille(grpcChambre.getAdresse().getVille());
        adresse.setRue(grpcChambre.getAdresse().getRue());
        adresse.setNumero(grpcChambre.getAdresse().getNumero());
        adresse.setGps(grpcChambre.getAdresse().getGps());

        ChambreDTO chambreDTO = new ChambreDTO(
                grpcChambre.getId(),
                grpcChambre.getNomHotel(),
                adresse,
                grpcChambre.getType(),
                grpcChambre.getNombreLits(),
                grpcChambre.getPrix(),
                grpcChambre.getReduction(),
                grpcChambre.getImage().toByteArray(),
                grpcChambre.getHasOffer(),
                grpcChambre.getEtoiles()
        );

        OffreAgence offre = new OffreAgence();
        offre.setOffreId(grpcOffre.getOffreId());
        offre.setAgenceId(agenceId);
        offre.setChambreDTO(chambreDTO);

        return offre;
    }

}
