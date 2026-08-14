package org.example.hotel.grpc;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.example.hotel.dto.ChambreDTO;           // POJO
import org.example.hotel.model.Adresse;           // POJO
import org.example.hotel.model.Reservation;       // POJO
import org.example.hotel.service.HotelConsultationService;
import org.example.hotel.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@GrpcService
public class HotelConsultationGrpcService extends HotelConsultationServiceGrpc.HotelConsultationServiceImplBase {

    @Autowired
    private HotelConsultationService hotelConsultationService;

    @Autowired
    private ReservationService reservationService;

    @Override
    public void authentifier(AuthRequest request, StreamObserver<AuthResponse> responseObserver) {
        boolean success = hotelConsultationService.authentifierAgence(
                request.getEmail(),
                request.getPassword()
        );

        AuthResponse response = AuthResponse.newBuilder()
                .setSuccess(success)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void consulterDisponibilites(DisponibiliteRequest request, StreamObserver<DisponibiliteResponse> responseObserver) {
        Date dateDebut = new Date(request.getDateDebut());
        Date dateFin = new Date(request.getDateFin());

        List<ChambreDTO> chambres = hotelConsultationService.consulterDisponibilites(
                request.getHotelId(),
                request.getEmail(),
                request.getPassword(),
                request.getNbPersonnes(),
                request.getVille(),
                dateDebut,
                dateFin
        );

        DisponibiliteResponse.Builder responseBuilder = DisponibiliteResponse.newBuilder();

        for (ChambreDTO chambrePojo : chambres) {
            org.example.hotel.grpc.ChambreDTO.Builder chambreBuilder = org.example.hotel.grpc.ChambreDTO.newBuilder()
                    .setId(chambrePojo.getId())
                    .setNomHotel(chambrePojo.getNomHotel())
                    .setType(chambrePojo.getType())
                    .setNombreLits(chambrePojo.getNombreLits())
                    .setPrix(chambrePojo.getPrix());

            if (chambrePojo.getAdresse() != null) {
                Adresse adrPojo = chambrePojo.getAdresse();
                org.example.hotel.grpc.Adresse.Builder adresseBuilder = org.example.hotel.grpc.Adresse.newBuilder()
                        .setPays(adrPojo.getPays() != null ? adrPojo.getPays() : "")
                        .setVille(adrPojo.getVille() != null ? adrPojo.getVille() : "")
                        .setRue(adrPojo.getRue() != null ? adrPojo.getRue() : "")
                        .setNumero(adrPojo.getNumero() != null ? adrPojo.getNumero() : "")
                        .setGps(adrPojo.getGPS() != null ? adrPojo.getGPS() : "");
                chambreBuilder.setAdresse(adresseBuilder.build());
            }

            if (chambrePojo.getImage() != null) {
                chambreBuilder.setImage(com.google.protobuf.ByteString.copyFrom(chambrePojo.getImage()));
            }

            responseBuilder.addChambres(chambreBuilder.build());
        }

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void reserverChambreAuto(HotelReservationRequest request, StreamObserver<HotelReservationResponse> responseObserver) {
        try {
            Date dateDebut = new Date(request.getDateDebut());
            Date dateFin = new Date(request.getDateFin());

            Reservation reservation = reservationService.reserverChambreAuto(
                    request.getAgenceId(),
                    request.getNomClient(),
                    request.getPrenomClient(),
                    dateDebut,
                    dateFin,
                    request.getEmail(),
                    request.getNumeroCarte(),
                    request.getCvc(),
                    request.getPrixTotal(),
                    request.getNombreNuits(),
                    request.getNbPersonnes(),
                    request.getVille()
            );

            org.example.hotel.grpc.HotelReservationDTO.Builder reservationBuilder = org.example.hotel.grpc.HotelReservationDTO.newBuilder()
                    .setId(reservation.getId())
                    .setReference(reservation.getReference())
                    .setAgenceId(reservation.getAgence().getId())
                    .setChambreId(reservation.getChambre().getId())
                    .setClientId(reservation.getClient().getId())
                    .setPrixTotal(reservation.getPrixTotal())
                    .setNombreNuits(reservation.getNombreNuits())
                    .setDateDebut(reservation.getDateDebut().getTime())
                    .setDateFin(reservation.getDateFin().getTime());

            HotelReservationResponse response = HotelReservationResponse.newBuilder()
                    .setSuccess(true)
                    .setMessage("Réservation effectuée avec succès")
                    .setReservation(reservationBuilder.build())
                    .build();

            responseObserver.onNext(response);
        } catch (Exception e) {
            HotelReservationResponse response = HotelReservationResponse.newBuilder()
                    .setSuccess(false)
                    .setMessage("Erreur lors de la réservation: " + e.getMessage())
                    .build();
            responseObserver.onNext(response);
        }

        responseObserver.onCompleted();
    }

    @Override
    public void annulerReservation(HotelAnnulerReservationRequest request, StreamObserver<HotelAnnulerReservationResponse> responseObserver) {
        boolean success = reservationService.annulerReservation(request.getEmail(), request.getReference());

        HotelAnnulerReservationResponse response = HotelAnnulerReservationResponse.newBuilder()
                .setSuccess(success)
                .setMessage(success ? "Réservation annulée avec succès" : "Réservation introuvable")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
