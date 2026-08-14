package org.example.agence.service;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.example.agence.model.Adresse;
import org.example.agence.model.ChambreDTO;
import org.example.hotel.grpc.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class HotelGrpcClientService {

    @GrpcClient("hotel1")
    private HotelConsultationServiceGrpc.HotelConsultationServiceBlockingStub hotel1Stub;

    @GrpcClient("hotel2")
    private HotelConsultationServiceGrpc.HotelConsultationServiceBlockingStub hotel2Stub;

    public List<ChambreDTO> getChambreDispo(String email, String password, int nbPersonnes, String ville, Date dateDebut, Date dateFin) {
        try {
            DisponibiliteRequest request = DisponibiliteRequest.newBuilder()
                    .setHotelId(1L)
                    .setEmail(email)
                    .setPassword(password)
                    .setNbPersonnes(nbPersonnes)
                    .setVille(ville)
                    .setDateDebut(dateDebut.getTime())
                    .setDateFin(dateFin.getTime())
                    .build();

            DisponibiliteResponse response = hotel1Stub.consulterDisponibilites(request);

            List<ChambreDTO> chambres = new ArrayList<>();
            for (org.example.hotel.grpc.ChambreDTO chambreProto : response.getChambresList()) {
                ChambreDTO chambre = convertToChambreDTO(chambreProto);
                chambres.add(chambre);
            }

            System.out.println("Chambres récupérées depuis gRPC hotel1 : " + chambres.size());
            return chambres;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<ChambreDTO> getChambreDispo2(String email, String password, int nbPersonnes, String ville, Date dateDebut, Date dateFin) {
        try {
            DisponibiliteRequest request = DisponibiliteRequest.newBuilder()
                    .setHotelId(2L)
                    .setEmail(email)
                    .setPassword(password)
                    .setNbPersonnes(nbPersonnes)
                    .setVille(ville)
                    .setDateDebut(dateDebut.getTime())
                    .setDateFin(dateFin.getTime())
                    .build();

            DisponibiliteResponse response = hotel2Stub.consulterDisponibilites(request);

            List<ChambreDTO> chambres = new ArrayList<>();
            for (org.example.hotel.grpc.ChambreDTO chambreProto : response.getChambresList()) {
                ChambreDTO chambre = convertToChambreDTO(chambreProto);
                chambres.add(chambre);
            }

            System.out.println("Chambres récupérées depuis gRPC hotel2 : " + chambres.size());
            return chambres;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private ChambreDTO convertToChambreDTO(org.example.hotel.grpc.ChambreDTO chambreProto) {
        ChambreDTO chambre = new ChambreDTO();
        chambre.setId(chambreProto.getId());
        chambre.setNomHotel(chambreProto.getNomHotel());
        chambre.setType(chambreProto.getType());
        chambre.setNombreLits(chambreProto.getNombreLits());
        chambre.setPrix(chambreProto.getPrix());

        if (chambreProto.hasAdresse()) {
            org.example.hotel.grpc.Adresse adrProto = chambreProto.getAdresse();
            Adresse adresse = new Adresse(
                    adrProto.getPays(),
                    adrProto.getVille(),
                    adrProto.getRue(),
                    adrProto.getNumero(),
                    adrProto.getGps()
            );
            chambre.setAdresse(adresse);
        }

        if (chambreProto.getImage() != null && !chambreProto.getImage().isEmpty()) {
            chambre.setImage(chambreProto.getImage().toByteArray());
        }

        return chambre;
    }
}

