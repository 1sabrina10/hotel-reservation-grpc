package org.example.agence.grpc;

import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.example.agence.service.AgenceService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@GrpcService
public class AgenceGrpcService extends AgenceServiceGrpc.AgenceServiceImplBase {

    @Autowired
    private AgenceService agenceService;

    // =========================
    // AUTHENTIFICATION
    // =========================
    @Override
    public void testAuth(AuthRequest request,
                         StreamObserver<AuthResponse> responseObserver) {

        boolean auth = agenceService.authentifierAgence(
                request.getEmail(),
                request.getPassword()
        );

        AuthResponse response = AuthResponse.newBuilder()
                .setAuth(auth)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    // =========================
    // CONSULTER OFFRES
    // =========================
    @Override
    public void consulterOffres(OffreRequest request,
                                StreamObserver<OffreResponse> responseObserver) {

        Date dateDebut = new Date(request.getDateDebut());
        Date dateFin = new Date(request.getDateFin());

        List<org.example.agence.model.OffreAgence> offresMetier =
                agenceService.consulterOffres(
                        request.getEmail(),
                        request.getPassword(),
                        request.getNbPersonnes(),
                        request.getVille(),
                        dateDebut,
                        dateFin,
                        request.getAgenceId()
                );

        OffreResponse.Builder responseBuilder = OffreResponse.newBuilder();

        for (org.example.agence.model.OffreAgence offreMetier : offresMetier) {

            OffreAgence.Builder offreBuilder = OffreAgence.newBuilder()
                    .setOffreId(offreMetier.getOffreId())
                    .setAgenceId(offreMetier.getAgenceId());

            if (offreMetier.getChambreDTO() != null) {
                org.example.agence.model.ChambreDTO chambre = offreMetier.getChambreDTO();

                ChambreDTO.Builder chambreBuilder = ChambreDTO.newBuilder()
                        .setId(chambre.getId())
                        .setNomHotel(chambre.getNomHotel() == null ? "" : chambre.getNomHotel())
                        .setType(chambre.getType() == null ? "" : chambre.getType())
                        .setNombreLits(chambre.getNombreLits())
                        .setPrix(chambre.getPrix())
                        .setReduction(chambre.getReduction())
                        .setHasOffer(chambre.isHasOffer())
                        .setEtoiles(chambre.getEtoiles())
                        .setNombreNuits(chambre.getNombreNuits())
                        .setPrixTotal(chambre.getPrixTotal());

                if (chambre.getAdresse() != null) {
                    org.example.agence.model.Adresse adr = chambre.getAdresse();

                    Adresse.Builder adresseBuilder = Adresse.newBuilder()
                            .setPays(adr.getPays() == null ? "" : adr.getPays())
                            .setVille(adr.getVille() == null ? "" : adr.getVille())
                            .setRue(adr.getRue() == null ? "" : adr.getRue())
                            .setNumero(adr.getNumero() == null ? "" : adr.getNumero())
                            .setGps(adr.getGPS() == null ? "" : adr.getGPS());

                    chambreBuilder.setAdresse(adresseBuilder.build());
                }

                if (chambre.getImage() != null) {
                    chambreBuilder.setImage(ByteString.copyFrom(chambre.getImage()));
                }

                chambreBuilder
                        .setDateDebut(chambre.getDateDebut().getTime())
                        .setDateFin(chambre.getDateFin().getTime());

                offreBuilder.setChambreDto(chambreBuilder.build());
            }

            responseBuilder.addOffres(offreBuilder.build());
        }

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    // =========================
    // GET OFFRE BY ID
    // =========================
    @Override
    public void getOffreById(GetOffreRequest request,
                             StreamObserver<OffreAgence> responseObserver) {

        org.example.agence.model.OffreAgence offreMetier =
                agenceService.getOffreById(
                        request.getAgenceId(),
                        request.getOffreId()
                );

        if (offreMetier == null) {
            responseObserver.onError(
                    new RuntimeException("Offre introuvable"));
            return;
        }

        OffreAgence.Builder offreBuilder = OffreAgence.newBuilder()
                .setOffreId(offreMetier.getOffreId())
                .setAgenceId(offreMetier.getAgenceId());

        if (offreMetier.getChambreDTO() != null) {
            org.example.agence.model.ChambreDTO chambre = offreMetier.getChambreDTO();

            ChambreDTO.Builder chambreBuilder = ChambreDTO.newBuilder()
                    .setId(chambre.getId())
                    .setNomHotel(chambre.getNomHotel() == null ? "" : chambre.getNomHotel())
                    .setType(chambre.getType() == null ? "" : chambre.getType())
                    .setNombreLits(chambre.getNombreLits())
                    .setPrix(chambre.getPrix())
                    .setReduction(chambre.getReduction())
                    .setHasOffer(chambre.isHasOffer())
                    .setEtoiles(chambre.getEtoiles())
                    .setNombreNuits(chambre.getNombreNuits())
                    .setPrixTotal(chambre.getPrixTotal());

            if (chambre.getAdresse() != null) {
                org.example.agence.model.Adresse adr = chambre.getAdresse();

                Adresse.Builder adresseBuilder = Adresse.newBuilder()
                        .setPays(adr.getPays() == null ? "" : adr.getPays())
                        .setVille(adr.getVille() == null ? "" : adr.getVille())
                        .setRue(adr.getRue() == null ? "" : adr.getRue())
                        .setNumero(adr.getNumero() == null ? "" : adr.getNumero())
                        .setGps(adr.getGPS() == null ? "" : adr.getGPS());

                chambreBuilder.setAdresse(adresseBuilder.build());
            }

            if (chambre.getImage() != null) {
                chambreBuilder.setImage(ByteString.copyFrom(chambre.getImage()));
            }

            chambreBuilder
                    .setDateDebut(chambre.getDateDebut().getTime())
                    .setDateFin(chambre.getDateFin().getTime());

            offreBuilder.setChambreDto(chambreBuilder.build());
        }

        responseObserver.onNext(offreBuilder.build());
        responseObserver.onCompleted();
    }
}
