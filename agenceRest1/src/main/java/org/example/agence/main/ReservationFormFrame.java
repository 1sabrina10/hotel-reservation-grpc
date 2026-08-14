package org.example.agence.main;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.example.agence.model.ChambreDTO;
import org.example.agence.model.Reservation;
import org.example.agence.service.AgenceService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class ReservationFormFrame extends JFrame {

    private final AgenceService agenceService;
    private final ChambreDTO chambre;
    private final Date dateDebut;
    private final Date dateFin;

    private JTextField txtNom, txtPrenom, txtEmail, txtCarte, txtCVC, txtNbNuits, txtPrixTotal;
    private JTextField txtEmailAnnulation, txtReferenceAnnulation;
    private JButton btnReserver, btnAnnuler;

    public ReservationFormFrame(AgenceService agenceService, ChambreDTO chambre, Date dateDebut, Date dateFin) {
        this.agenceService = agenceService;
        this.chambre = chambre;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;

        setTitle("Gestion des Réservations - " + chambre.getNomHotel());
        setSize(800, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        mainPanel.setBorder(new EmptyBorder(10,10,10,10));

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
        gbc.insets = new Insets(5,10,5,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int y = 0;

        gbc.gridx = 0; gbc.gridy = y; panel.add(new JLabel("Nom :"), gbc);
        gbc.gridx = 1; txtNom = new JTextField(); panel.add(txtNom, gbc);

        gbc.gridx = 0; gbc.gridy = ++y; panel.add(new JLabel("Prénom :"), gbc);
        gbc.gridx = 1; txtPrenom = new JTextField(); panel.add(txtPrenom, gbc);

        gbc.gridx = 0; gbc.gridy = ++y; panel.add(new JLabel("Email :"), gbc);
        gbc.gridx = 1; txtEmail = new JTextField(); panel.add(txtEmail, gbc);

        gbc.gridx = 0; gbc.gridy = ++y; panel.add(new JLabel("Numéro carte :"), gbc);
        gbc.gridx = 1; txtCarte = new JTextField(); panel.add(txtCarte, gbc);

        gbc.gridx = 0; gbc.gridy = ++y; panel.add(new JLabel("CVC :"), gbc);
        gbc.gridx = 1; txtCVC = new JTextField(); panel.add(txtCVC, gbc);

        int nbNuits = Math.max(1, (int)((dateFin.getTime() - dateDebut.getTime())/(1000*60*60*24)));
        gbc.gridx = 0; gbc.gridy = ++y; panel.add(new JLabel("Nombre de nuits :"), gbc);
        gbc.gridx = 1; txtNbNuits = new JTextField(String.valueOf(nbNuits));
        txtNbNuits.setEditable(false); panel.add(txtNbNuits, gbc);

        double prixTotal = chambre.getPrix() * nbNuits;
        DecimalFormat df = new DecimalFormat("#0.00");
        gbc.gridx = 0; gbc.gridy = ++y; panel.add(new JLabel("Prix total (€) :"), gbc);
        gbc.gridx = 1; txtPrixTotal = new JTextField(df.format(prixTotal));
        txtPrixTotal.setEditable(false); panel.add(txtPrixTotal, gbc);

        gbc.gridx = 0; gbc.gridy = ++y; gbc.gridwidth = 2;
        btnReserver = new JButton("Réserver");
        btnReserver.setBackground(new Color(0,128,0));
        btnReserver.setForeground(Color.WHITE);
        btnReserver.addActionListener(e -> confirmerReservation());
        panel.add(btnReserver, gbc);

        return panel;
    }

    private JPanel initAnnulationPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(255,228,196));
        panel.setBorder(BorderFactory.createTitledBorder("Annuler une réservation"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,10,5,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int y = 0;

        gbc.gridx = 0; gbc.gridy = y; panel.add(new JLabel("Email client :"), gbc);
        gbc.gridx = 1; txtEmailAnnulation = new JTextField(); panel.add(txtEmailAnnulation, gbc);

        gbc.gridx = 0; gbc.gridy = ++y; panel.add(new JLabel("Référence :"), gbc);
        gbc.gridx = 1; txtReferenceAnnulation = new JTextField(); panel.add(txtReferenceAnnulation, gbc);

        gbc.gridx = 0; gbc.gridy = ++y; gbc.gridwidth = 2;
        btnAnnuler = new JButton("Annuler réservation");
        btnAnnuler.setBackground(new Color(178,34,34));
        btnAnnuler.setForeground(Color.WHITE);
        btnAnnuler.addActionListener(e -> annulerReservation());
        panel.add(btnAnnuler, gbc);

        return panel;
    }

    private void confirmerReservation() {
        try {
            String nom = txtNom.getText().trim();
            String prenom = txtPrenom.getText().trim();
            String email = txtEmail.getText().trim();
            String carte = txtCarte.getText().trim();
            String cvc = txtCVC.getText().trim();
            int nbNuits = Integer.parseInt(txtNbNuits.getText().trim());
            double prixTotal = Double.parseDouble(txtPrixTotal.getText().trim().replace(",", "."));

            String reference = UUID.randomUUID().toString();

            Reservation reservation = agenceService.reserverChambre(
                    1L,
                    chambre.getId(),
                    nom, prenom, email,
                    carte, cvc, prixTotal, nbNuits,
                    dateDebut, dateFin,
                    reference
            );

            if(reservation != null) {
                JOptionPane.showMessageDialog(this, "Réservation confirmée ! Référence : " + reference);
                imprimerPDF(reservation);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la réservation !");
            }

        } catch(Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
        }
    }

    private void annulerReservation() {
        String email = txtEmailAnnulation.getText().trim();
        String ref = txtReferenceAnnulation.getText().trim();
        if(email.isEmpty() || ref.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email et référence obligatoires !");
            return;
        }

        try {
            agenceService.annulerReservation(ref);
            JOptionPane.showMessageDialog(this, "Réservation annulée !");
        } catch(Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur : " + e.getMessage());
        }
    }

    private void imprimerPDF(Reservation reservation) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Document doc = new Document(PageSize.A4,50,50,50,50);
            String path = "reservation_" + reservation.getReference() + ".pdf";
            PdfWriter.getInstance(doc, new FileOutputStream(path));
            doc.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22, BaseColor.BLUE);
            Paragraph title = new Paragraph("Confirmation de Réservation", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            doc.add(title);
            doc.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);

            table.addCell("Référence"); table.addCell(reservation.getReference());
            table.addCell("Hôtel"); table.addCell(chambre.getNomHotel());
            table.addCell("Type chambre"); table.addCell(chambre.getType());
            table.addCell("Nombre de lits"); table.addCell(String.valueOf(chambre.getNombreLits()));
            table.addCell("Prix par nuit"); table.addCell(chambre.getPrix() + " €");
            table.addCell("Date début"); table.addCell(sdf.format(dateDebut));
            table.addCell("Date fin"); table.addCell(sdf.format(dateFin));
            table.addCell("Nb nuits"); table.addCell(String.valueOf(txtNbNuits.getText()));
            table.addCell("Prix total"); table.addCell(txtPrixTotal.getText() + " €");

            doc.add(table);
            doc.close();
            JOptionPane.showMessageDialog(this, "PDF généré : " + path);
        } catch(Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur génération PDF : " + e.getMessage());
        }
    }
}
