/*
package org.example.agence.main;

import org.example.agence.model.ChambreDTO;
import org.example.agence.service.AgenceService;
import org.example.agence.service.HotelGrpcClientService;
import com.toedter.calendar.JDateChooser;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class RechercheChambresUI extends JFrame {

    private final AgenceService agenceService;
    private final HotelGrpcClientService hotelGrpcClientService;

    private JTextField loginField;
    private JPasswordField passwordField;
    private JTextField villeField;
    private JTextField nbPersonnesField;
    private JDateChooser dateDebutChooser;
    private JDateChooser dateFinChooser;
    private JButton rechercherBtn;
    private JPanel offresPanel;
    private JScrollPane scrollPane;

    private String emailConnecte;
    private String passwordConnecte;

    public RechercheChambresUI(AgenceService agenceService, HotelGrpcClientService hotelGrpcClientService) {
        this.agenceService = agenceService;
        this.hotelGrpcClientService = hotelGrpcClientService;

        setTitle("🌍 Agence - Réservation d’Hôtels");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        creerFormulaireLogin();
    }

    private void creerFormulaireLogin() {
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBorder(new EmptyBorder(50, 50, 50, 50));

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 15, 15));
        formPanel.setOpaque(false);

        JLabel title = new JLabel("🔐 Connexion Agence");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        loginField = new JTextField(12);
        passwordField = new JPasswordField(12);
        JButton loginBtn = new JButton("Se connecter");

        formPanel.add(new JLabel("Login :", SwingConstants.RIGHT));
        formPanel.add(loginField);
        formPanel.add(new JLabel("Mot de passe :", SwingConstants.RIGHT));
        formPanel.add(passwordField);
        formPanel.add(new JLabel());
        formPanel.add(loginBtn);

        JPanel container = new JPanel(new BorderLayout());
        container.setOpaque(false);
        container.add(title, BorderLayout.NORTH);
        container.add(formPanel, BorderLayout.CENTER);

        loginPanel.add(container);
        add(loginPanel, BorderLayout.CENTER);

        loginBtn.addActionListener(e -> {
            emailConnecte = loginField.getText().trim();
            passwordConnecte = new String(passwordField.getPassword()).trim();

            if(emailConnecte.isEmpty() || passwordConnecte.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir login et mot de passe !");
                return;
            }

            if(agenceService.authentifierAgence(emailConnecte, passwordConnecte)) {
                // Supprimer page login
                getContentPane().removeAll();
                revalidate();
                repaint();

                // Créer nouvelle page
                creerFormulaireRecherche();
            } else {
                JOptionPane.showMessageDialog(this, "Login ou mot de passe invalide !");
            }
        });
    }

    private void creerFormulaireRecherche() {
        // Header avec dégradé
        JPanel header = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(0, 102, 204);
                Color color2 = new Color(102, 178, 255);
                int width = getWidth();
                int height = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, color1, width, 0, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, width, height);
            }
        };
        header.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 15));
        JLabel titre = new JLabel("🔍 Recherche d’Hôtels - Agence : " + emailConnecte);
        titre.setFont(new Font("SansSerif", Font.BOLD, 16));
        titre.setForeground(Color.WHITE);
        header.add(titre);
        header.setPreferredSize(new Dimension(getWidth(), 50));
        add(header, BorderLayout.NORTH);

        // Formulaire recherche
        JPanel form = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        form.setBackground(new Color(230, 240, 250));

        villeField = new JTextField(10);
        nbPersonnesField = new JTextField(3);
        dateDebutChooser = new JDateChooser();
        dateDebutChooser.setDateFormatString("yyyy-MM-dd");
        dateFinChooser = new JDateChooser();
        dateFinChooser.setDateFormatString("yyyy-MM-dd");
        rechercherBtn = new JButton("Rechercher");

        form.add(new JLabel("Ville:"));
        form.add(villeField);
        form.add(new JLabel("Début:"));
        form.add(dateDebutChooser);
        form.add(new JLabel("Fin:"));
        form.add(dateFinChooser);
        form.add(new JLabel("Nb pers:"));
        form.add(nbPersonnesField);
        form.add(rechercherBtn);

        add(form, BorderLayout.SOUTH);

        // Panel résultats
        offresPanel = new JPanel();
        offresPanel.setLayout(new BoxLayout(offresPanel, BoxLayout.Y_AXIS));
        offresPanel.setBackground(Color.WHITE);

        scrollPane = new JScrollPane(offresPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);

        rechercherBtn.addActionListener(e -> rechercherChambres());

        revalidate();
        repaint();
    }

    private void rechercherChambres() {
        try {
            offresPanel.removeAll();

            String ville = villeField.getText().trim();
            if(ville.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez saisir une ville !");
                return;
            }
            if(dateDebutChooser.getDate() == null || dateFinChooser.getDate() == null) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner les deux dates !");
                return;
            }

            int nbPersonnes;
            try {
                nbPersonnes = Integer.parseInt(nbPersonnesField.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer un nombre valide de personnes !");
                return;
            }

            Date dateDebut = dateDebutChooser.getDate();
            Date dateFin = dateFinChooser.getDate();

            // Appels gRPC
            List<ChambreDTO> chambres1 = hotelGrpcClientService.getChambreDispo(
                    emailConnecte, passwordConnecte, nbPersonnes, ville, dateDebut, dateFin
            );
            List<ChambreDTO> chambres2 = hotelGrpcClientService.getChambreDispo2(
                    emailConnecte, passwordConnecte, nbPersonnes, ville, dateDebut, dateFin
            );

            List<ChambreDTO> chambres = chambres1 != null ? chambres1 : Collections.emptyList();
            if(chambres2 != null) chambres.addAll(chambres2);

            if(chambres.isEmpty()) {
                JOptionPane.showMessageDialog(this, "❌ Aucune chambre disponible !");
                return;
            }

            // Affichage résultats
            for(ChambreDTO c : chambres) {
                JPanel chambrePanel = new JPanel(new BorderLayout(15, 10));
                chambrePanel.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(new Color(180, 180, 180), 1, true),
                        new EmptyBorder(10, 10, 10, 10)
                ));
                chambrePanel.setBackground(Color.WHITE);
                chambrePanel.setMaximumSize(new Dimension(1100, 160));

                // Photo
                JLabel photoLabel = new JLabel();
                if(c.getImage() != null && c.getImage().length > 0) {
                    try {
                        BufferedImage img = ImageIO.read(new ByteArrayInputStream(c.getImage()));
                        Image dimg = img.getScaledInstance(120, 100, Image.SCALE_SMOOTH);
                        photoLabel.setIcon(new ImageIcon(dimg));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        photoLabel.setText("Erreur photo");
                    }
                } else {
                    photoLabel.setText("Pas de photo");
                    photoLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    photoLabel.setPreferredSize(new Dimension(120, 100));
                    photoLabel.setBorder(new LineBorder(Color.LIGHT_GRAY));
                }
                chambrePanel.add(photoLabel, BorderLayout.WEST);

                // Infos
                String info = "<html><b style='font-size:14px; color:#003366;'>" +
                        (c.getNomHotel() != null ? c.getNomHotel() : "") +
                        "</b><br>Type: " + (c.getType() != null ? c.getType() : "") +
                        " - Lits: " + c.getNombreLits() +
                        "<br>Prix: " + c.getPrix() + " €</html>";
                JLabel infoLabel = new JLabel(info);
                chambrePanel.add(infoLabel, BorderLayout.CENTER);

                // Bouton Réserver
                JButton reserverBtn = new JButton("Réserver");
                reserverBtn.addActionListener(ev -> {
                    JOptionPane.showMessageDialog(this, "Réservation pour : " + c.getNomHotel());
                });
                chambrePanel.add(reserverBtn, BorderLayout.EAST);

                offresPanel.add(chambrePanel);
                offresPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }

            offresPanel.revalidate();
            offresPanel.repaint();

        } catch(Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Ici, tu passes tes services réels gRPC
            AgenceService agenceService = new AgenceService();
            HotelGrpcClientService hotelService = new HotelGrpcClientService();
            new RechercheChambresUI(agenceService, hotelService).setVisible(true);
        });
    }
}
*/
package org.example.agence.main;

import org.example.agence.model.ChambreDTO;
import org.example.agence.model.Reservation;
import org.example.agence.service.AgenceService;
import org.example.agence.service.HotelGrpcClientService;
import com.toedter.calendar.JDateChooser;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class RechercheChambresUI extends JFrame {

    private final AgenceService agenceService;
    private final HotelGrpcClientService hotelGrpcClientService;

    private JTextField loginField;
    private JPasswordField passwordField;

    private JTextField villeField;
    private JTextField nbPersonnesField;
    private JDateChooser dateDebutChooser;
    private JDateChooser dateFinChooser;
    private JButton rechercherBtn;

    private JPanel offresPanel;
    private JScrollPane scrollPane;

    private JTextField txtEmailAnnulation;
    private JTextField txtReferenceAnnulation;
    private JButton btnAnnuler;

    private JPanel annulationPanel; // Panel unique Annulation

    private String emailConnecte;
    private String passwordConnecte;

    public RechercheChambresUI(AgenceService agenceService, HotelGrpcClientService hotelGrpcClientService) {
        this.agenceService = agenceService;
        this.hotelGrpcClientService = hotelGrpcClientService;

        setTitle("🌍 Agence - Réservation d’Hôtels");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        creerFormulaireLogin();
    }

    private void creerFormulaireLogin() {
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBorder(new EmptyBorder(50, 50, 50, 50));

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 15, 15));
        formPanel.setOpaque(false);

        JLabel title = new JLabel("🔐 Connexion Agence");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        loginField = new JTextField(12);
        passwordField = new JPasswordField(12);
        JButton loginBtn = new JButton("Se connecter");

        formPanel.add(new JLabel("Login :", SwingConstants.RIGHT));
        formPanel.add(loginField);
        formPanel.add(new JLabel("Mot de passe :", SwingConstants.RIGHT));
        formPanel.add(passwordField);
        formPanel.add(new JLabel());
        formPanel.add(loginBtn);

        JPanel container = new JPanel(new BorderLayout());
        container.setOpaque(false);
        container.add(title, BorderLayout.NORTH);
        container.add(formPanel, BorderLayout.CENTER);

        loginPanel.add(container);
        add(loginPanel, BorderLayout.CENTER);

        loginBtn.addActionListener(e -> {
            emailConnecte = loginField.getText().trim();
            passwordConnecte = new String(passwordField.getPassword()).trim();

            if(emailConnecte.isEmpty() || passwordConnecte.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir login et mot de passe !");
                return;
            }

            if(agenceService.authentifierAgence(emailConnecte, passwordConnecte)) {
                getContentPane().removeAll();
                revalidate();
                repaint();

                creerInterfacePrincipale();
            } else {
                JOptionPane.showMessageDialog(this, "Login ou mot de passe invalide !");
            }
        });
    }

    private void creerInterfacePrincipale() {
        // Header
        JPanel header = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(0,102,204), getWidth(), 0, new Color(102,178,255));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        header.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 15));
        JLabel titre = new JLabel("🔍 Recherche d’Hôtels - Agence : " + emailConnecte);
        titre.setFont(new Font("SansSerif", Font.BOLD, 16));
        titre.setForeground(Color.WHITE);
        header.add(titre);
        header.setPreferredSize(new Dimension(getWidth(), 50));
        add(header, BorderLayout.NORTH);

        // Formulaire recherche
        JPanel form = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        form.setBackground(new Color(230, 240, 250));

        villeField = new JTextField(10);
        nbPersonnesField = new JTextField(3);
        dateDebutChooser = new JDateChooser();
        dateDebutChooser.setDateFormatString("yyyy-MM-dd");
        dateFinChooser = new JDateChooser();
        dateFinChooser.setDateFormatString("yyyy-MM-dd");
        rechercherBtn = new JButton("Rechercher");

        form.add(new JLabel("Ville:"));
        form.add(villeField);
        form.add(new JLabel("Début:"));
        form.add(dateDebutChooser);
        form.add(new JLabel("Fin:"));
        form.add(dateFinChooser);
        form.add(new JLabel("Nb pers:"));
        form.add(nbPersonnesField);
        form.add(rechercherBtn);

        add(form, BorderLayout.SOUTH);

        // Panel résultats
        offresPanel = new JPanel();
        offresPanel.setLayout(new BoxLayout(offresPanel, BoxLayout.Y_AXIS));
        offresPanel.setBackground(Color.WHITE);

        scrollPane = new JScrollPane(offresPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);

        rechercherBtn.addActionListener(e -> rechercherChambres());

        revalidate();
        repaint();
    }

    private JPanel initAnnulationPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(255, 228, 196));
        panel.setBorder(BorderFactory.createTitledBorder("Annuler une réservation"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,10,5,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int y = 0;

        gbc.gridx = 0; gbc.gridy = y;
        panel.add(new JLabel("Email :"), gbc);
        txtEmailAnnulation = new JTextField(); gbc.gridx = 1;
        panel.add(txtEmailAnnulation, gbc);

        gbc.gridx = 0; gbc.gridy = ++y;
        panel.add(new JLabel("Référence:"), gbc);
        txtReferenceAnnulation = new JTextField(); gbc.gridx = 1;
        panel.add(txtReferenceAnnulation, gbc);

        gbc.gridx = 0; gbc.gridy = ++y; gbc.gridwidth = 2;
        btnAnnuler = new JButton("Annuler réservation");
        btnAnnuler.setBackground(new Color(178,34,34));
        btnAnnuler.setForeground(Color.WHITE);
        panel.add(btnAnnuler, gbc);

        btnAnnuler.addActionListener(e -> {
            String ref = txtReferenceAnnulation.getText().trim();
            if(ref.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez saisir la référence !");
                return;
            }
            try {
                agenceService.annulerReservation(ref);
                JOptionPane.showMessageDialog(this, "Réservation annulée avec succès !");
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
            }
        });

        return panel;
    }

    private void rechercherChambres() {
        try {
            offresPanel.removeAll();

            String ville = villeField.getText().trim();
            if(ville.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez saisir une ville !");
                return;
            }
            if(dateDebutChooser.getDate() == null || dateFinChooser.getDate() == null) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner les deux dates !");
                return;
            }

            int nbPersonnes;
            try {
                nbPersonnes = Integer.parseInt(nbPersonnesField.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer un nombre valide de personnes !");
                return;
            }

            Date dateDebut = dateDebutChooser.getDate();
            Date dateFin = dateFinChooser.getDate();

            // Appels gRPC
            List<ChambreDTO> chambres1 = hotelGrpcClientService.getChambreDispo(
                    emailConnecte, passwordConnecte, nbPersonnes, ville, dateDebut, dateFin
            );
            List<ChambreDTO> chambres2 = hotelGrpcClientService.getChambreDispo2(
                    emailConnecte, passwordConnecte, nbPersonnes, ville, dateDebut, dateFin
            );

            List<ChambreDTO> chambres = chambres1 != null ? chambres1 : Collections.emptyList();
            if(chambres2 != null) chambres.addAll(chambres2);

            if(chambres.isEmpty()) {
                JOptionPane.showMessageDialog(this, "❌ Aucune chambre disponible !");
                return;
            }

            for(ChambreDTO c : chambres) {
                JPanel chambrePanel = new JPanel(new BorderLayout(15, 10));
                chambrePanel.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(new Color(180, 180, 180), 1, true),
                        new EmptyBorder(10, 10, 10, 10)
                ));
                chambrePanel.setBackground(Color.WHITE);
                chambrePanel.setMaximumSize(new Dimension(1100, 160));

                // Photo
                JLabel photoLabel = new JLabel();
                if(c.getImage() != null && c.getImage().length > 0) {
                    try {
                        BufferedImage img = ImageIO.read(new ByteArrayInputStream(c.getImage()));
                        Image dimg = img.getScaledInstance(120, 100, Image.SCALE_SMOOTH);
                        photoLabel.setIcon(new ImageIcon(dimg));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        photoLabel.setText("Erreur photo");
                    }
                } else {
                    photoLabel.setText("Pas de photo");
                    photoLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    photoLabel.setPreferredSize(new Dimension(120, 100));
                    photoLabel.setBorder(new LineBorder(Color.LIGHT_GRAY));
                }
                chambrePanel.add(photoLabel, BorderLayout.WEST);

                // Infos
                String info = "<html><b style='font-size:14px; color:#003366;'>" +
                        (c.getNomHotel() != null ? c.getNomHotel() : "") +
                        "</b><br>Type: " + (c.getType() != null ? c.getType() : "") +
                        " - Lits: " + c.getNombreLits() +
                        "<br>Prix: " + c.getPrix() + " €</html>";
                JLabel infoLabel = new JLabel(info);
                chambrePanel.add(infoLabel, BorderLayout.CENTER);

                // Bouton Réserver
                JButton reserverBtn = new JButton("Réserver");
                reserverBtn.addActionListener(ev -> openReservationForm(c, dateDebut, dateFin));
                chambrePanel.add(reserverBtn, BorderLayout.EAST);

                offresPanel.add(chambrePanel);
                offresPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }

            offresPanel.revalidate();
            offresPanel.repaint();

        } catch(Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
        }
    }

    private void openReservationForm(ChambreDTO chambre, Date dateDebut, Date dateFin) {
        ReservationFormFrame reservationFrame = new ReservationFormFrame(agenceService, chambre, dateDebut, dateFin);
        reservationFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AgenceService agenceService = new AgenceService();
            HotelGrpcClientService hotelService = new HotelGrpcClientService();
            new RechercheChambresUI(agenceService, hotelService).setVisible(true);
        });
    }
}


