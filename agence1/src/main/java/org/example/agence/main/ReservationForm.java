/*
package org.example.agenceservice.main;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.example.agence.grpc.AgenceServiceGrpc;
import org.example.agence.grpc.ReservationRequest;
import org.example.agence.grpc.ReservationResponse;
import org.example.agence.grpc.AnnulationRequest;
import org.example.agence.grpc.AnnulationResponse;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class ReservationForm extends JFrame {

    private final AgenceServiceGrpc.AgenceServiceBlockingStub agenceStub;

    private JTextField txtNom, txtPrenom, txtEmail, txtNumeroCarte, txtCVC, txtNbNuits, txtPrixTotal;
    private JButton btnReserver;
    private JTextField txtReferenceAnnulation, txtEmailAnnulation;
    private JButton btnAnnuler;

    private final Long chambreId;
    private final double prixParNuit;
    private final Date dateDebut, dateFin;

    public ReservationForm(Long chambreId, double prixParNuit, Date dateDebut, Date dateFin) {
        // Initialise le channel gRPC
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();
        this.agenceStub = AgenceServiceGrpc.newBlockingStub(channel);

        this.chambreId = chambreId;
        this.prixParNuit = prixParNuit;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;

        setTitle("Gestion des Réservations");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 450);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout(10, 10));

        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        mainPanel.add(initReservationPanel());
        mainPanel.add(initAnnulationPanel());

        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private JPanel initReservationPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(173, 216, 230));
        panel.setBorder(BorderFactory.createTitledBorder("Réserver une chambre"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int y = 0;
        gbc.gridx = 0;

        gbc.gridy = y; panel.add(new JLabel("Nom :"), gbc);
        gbc.gridx = 1; txtNom = new JTextField(); panel.add(txtNom, gbc);

        gbc.gridx = 0; gbc.gridy = ++y; panel.add(new JLabel("Prénom :"), gbc);
        gbc.gridx = 1; txtPrenom = new JTextField(); panel.add(txtPrenom, gbc);

        gbc.gridx = 0; gbc.gridy = ++y; panel.add(new JLabel("Email :"), gbc);
        gbc.gridx = 1; txtEmail = new JTextField(); panel.add(txtEmail, gbc);

        gbc.gridx = 0; gbc.gridy = ++y; panel.add(new JLabel("Numéro de carte :"), gbc);
        gbc.gridx = 1; txtNumeroCarte = new JTextField(); panel.add(txtNumeroCarte, gbc);

        gbc.gridx = 0; gbc.gridy = ++y; panel.add(new JLabel("CVC :"), gbc);
        gbc.gridx = 1; txtCVC = new JTextField(); panel.add(txtCVC, gbc);

        int nbNuits = Math.max(1, (int)((dateFin.getTime() - dateDebut.getTime()) / (1000*60*60*24)));
        gbc.gridx = 0; gbc.gridy = ++y; panel.add(new JLabel("Nombre de nuits :"), gbc);
        gbc.gridx = 1; txtNbNuits = new JTextField(String.valueOf(nbNuits));
        txtNbNuits.setEditable(false);
        panel.add(txtNbNuits, gbc);

        double prixTotal = prixParNuit * nbNuits;
        DecimalFormat df = new DecimalFormat("#0.00");
        gbc.gridx = 0; gbc.gridy = ++y; panel.add(new JLabel("Prix total :"), gbc);
        gbc.gridx = 1; txtPrixTotal = new JTextField(df.format(prixTotal));
        txtPrixTotal.setEditable(false);
        panel.add(txtPrixTotal, gbc);

        gbc.gridx = 0; gbc.gridy = ++y; gbc.gridwidth = 2;
        btnReserver = new JButton("Réserver");
        btnReserver.setBackground(new Color(0, 128, 0));
        btnReserver.setForeground(Color.WHITE);
        btnReserver.addActionListener(e -> reserverAction());
        panel.add(btnReserver, gbc);

        return panel;
    }

    private JPanel initAnnulationPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(255, 228, 196));
        panel.setBorder(BorderFactory.createTitledBorder("Annuler une réservation"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,10,5,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int y = 0;
        gbc.gridx = 0;

        gbc.gridy = y; panel.add(new JLabel("Email (annulation) :"), gbc);
        gbc.gridx = 1; txtEmailAnnulation = new JTextField(); panel.add(txtEmailAnnulation, gbc);

        gbc.gridx = 0; gbc.gridy = ++y; panel.add(new JLabel("Référence :"), gbc);
        gbc.gridx = 1; txtReferenceAnnulation = new JTextField(); panel.add(txtReferenceAnnulation, gbc);

        gbc.gridx = 0; gbc.gridy = ++y; gbc.gridwidth = 2;
        btnAnnuler = new JButton("Annuler réservation");
        btnAnnuler.setBackground(new Color(178, 34, 34));
        btnAnnuler.setForeground(Color.WHITE);
        btnAnnuler.addActionListener(e -> annulerAction());
        panel.add(btnAnnuler, gbc);

        return panel;
    }

    private void reserverAction() {
        try {
            String nom = txtNom.getText().trim();
            String prenom = txtPrenom.getText().trim();
            String email = txtEmail.getText().trim();
            int nbNuits = Integer.parseInt(txtNbNuits.getText().trim());
            double prixTotal = Double.parseDouble(txtPrixTotal.getText().trim().replace(",", "."));

            ReservationRequest request = ReservationRequest.newBuilder()
                    .setChambreId(chambreId)
                    .setNom(nom)
                    .setPrenom(prenom)
                    .setEmail(email)
                    .setNbNuits(nbNuits)
                    .setPrixTotal(prixTotal)
                    .setNumeroCarte(txtNumeroCarte.getText().trim())
                    .setCvc(txtCVC.getText().trim())
                    .build();

            ReservationResponse response = agenceStub.reserverChambre(request);

            if(response.getSuccess()) {
                JOptionPane.showMessageDialog(this, "Réservation réussie ! Référence : " + response.getReference());
                imprimerPdf(response.getReference());
            } else {
                JOptionPane.showMessageDialog(this, "Échec : " + response.getMessage());
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur : " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void annulerAction() {
        try {
            String email = txtEmailAnnulation.getText().trim();
            String ref = txtReferenceAnnulation.getText().trim();

            if(email.isEmpty() || ref.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Email et référence obligatoires !");
                return;
            }

            AnnulationRequest request = AnnulationRequest.newBuilder()
                    .setEmail(email)
                    .setReference(ref)
                    .build();

            AnnulationResponse response = agenceStub.annulerReservation(request);

            if(response.getSuccess()) JOptionPane.showMessageDialog(this, "Réservation annulée.");
            else JOptionPane.showMessageDialog(this, "Aucune réservation trouvée.");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur : " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void imprimerPdf(String ref) {
        try {
            String nom = txtNom.getText().trim();
            String prenom = txtPrenom.getText().trim();
            String email = txtEmail.getText().trim();
            int nbNuits = Integer.parseInt(txtNbNuits.getText().trim());
            double prixTotal = Double.parseDouble(txtPrixTotal.getText().trim().replace(",", "."));

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
            String path = "/home/user/reservation_" + (ref.isEmpty() ? "temp" : ref) + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(path));
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22, BaseColor.BLUE);
            Paragraph title = new Paragraph("Confirmation de Réservation", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" "));

            PdfPTable tableClient = new PdfPTable(2);
            tableClient.setWidthPercentage(100);
            tableClient.setSpacingBefore(10f);
            tableClient.setSpacingAfter(10f);

            tableClient.addCell(new Phrase("Nom", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
            tableClient.addCell(nom);
            tableClient.addCell(new Phrase("Prénom", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
            tableClient.addCell(prenom);
            tableClient.addCell(new Phrase("Email", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
            tableClient.addCell(email);
            tableClient.addCell(new Phrase("Référence", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.RED)));
            tableClient.addCell(ref);

            document.add(tableClient);

            PdfPTable tableDates = new PdfPTable(2);
            tableDates.setWidthPercentage(50);
            tableDates.setHorizontalAlignment(Element.ALIGN_RIGHT);

            tableDates.addCell(new Phrase("Date début", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
            tableDates.addCell(sdf.format(dateDebut));
            tableDates.addCell(new Phrase("Date fin", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
            tableDates.addCell(sdf.format(dateFin));
            tableDates.addCell(new Phrase("Nombre de nuits", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
            tableDates.addCell(String.valueOf(nbNuits));
            tableDates.addCell(new Phrase("Prix total", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.RED)));
            tableDates.addCell(prixTotal + " €");

            document.add(tableDates);

            document.close();
            JOptionPane.showMessageDialog(this, "PDF généré avec succès : " + path);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur génération PDF : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ReservationForm(101L, 75.0, new Date(), new Date(System.currentTimeMillis() + 2*24*60*60*1000));
        });
    }
}
*/
