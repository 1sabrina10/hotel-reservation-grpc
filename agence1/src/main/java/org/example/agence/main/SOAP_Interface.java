package org.example.agence.main;

import org.example.agence.service.AgenceService;
import org.example.agence.service.HotelGrpcClientService;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class SOAP_Interface extends JFrame {

    private final AgenceService agenceService;
    private final HotelGrpcClientService hotelGrpcClientService;

    public SOAP_Interface(AgenceService agenceService, HotelGrpcClientService hotelGrpcClientService) {
        this.agenceService = agenceService;
        this.hotelGrpcClientService = hotelGrpcClientService;
        initComponents();
    }

    private void initComponents() {

        jPanel1 = new JPanel();
        jPanel2 = new JPanel();
        jButton1 = new JButton();
        jButton2 = new JButton();
        jButton3 = new JButton();
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Application de réservation d'hôtels");

        jPanel1.setBackground(new Color(255, 250, 240));
        jPanel2.setBackground(new Color(220, 220, 220));

        Color boutonColor = new Color(218, 165, 32);
        jButton1.setBackground(boutonColor);
        jButton2.setBackground(boutonColor);
        jButton3.setBackground(boutonColor);

        jButton1.setForeground(Color.WHITE);
        jButton2.setForeground(Color.WHITE);
        jButton3.setForeground(Color.WHITE);

        jButton1.setFont(new Font("Liberation Sans", Font.BOLD, 24));
        jButton1.setText("Quitter");
        jButton1.addActionListener(e -> System.exit(0));

        jButton2.setFont(new Font("Liberation Sans", Font.BOLD, 24));
        jButton2.setText("Accueil");
        jButton2.addActionListener(e -> JOptionPane.showMessageDialog(this, "Vous êtes déjà sur l'accueil !"));

        jButton3.setFont(new Font("Liberation Sans", Font.BOLD, 24));
        jButton3.setText("Recherche Hôtel");
        jButton3.addActionListener(e -> {
            // ⚡ Utiliser les beans Spring injectés
            RechercheChambresUI rechercheWindow = new RechercheChambresUI(agenceService, hotelGrpcClientService);
            rechercheWindow.setVisible(true);
            this.dispose(); // Optionnel : fermer la fenêtre SOAP_Interface
        });

        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createSequentialGroup()
                        .addGap(22)
                        .addComponent(jButton2, 235, 235, 235)
                        .addGap(98)
                        .addComponent(jButton3, 240, 240, 240)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
                        .addComponent(jButton1, 236, 236, 236)
                        .addGap(19)
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1, 65, 65, 65)
                        .addComponent(jButton2, 65, 65, 65)
                        .addComponent(jButton3, 65, 65, 65)
        );

        jLabel1.setFont(new Font("Liberation Sans", Font.BOLD | Font.ITALIC, 32));
        jLabel1.setForeground(new Color(50, 50, 50));
        jLabel1.setText("Bienvenue dans l'application de réservation d'hôtels");

        try {
            URL imgURL = getClass().getResource("/images/hotel_royal.jpg");
            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                Image image = icon.getImage().getScaledInstance(700, 400, Image.SCALE_SMOOTH);
                jLabel2.setIcon(new ImageIcon(image));
            } else {
                System.err.println("Image introuvable !");
            }
        } catch (Exception e) {
            System.err.println("Erreur de chargement de l'image : " + e.getMessage());
        }

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addComponent(jLabel2, 700, 700, 700)
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(30)
                        .addComponent(jLabel1)
                        .addGap(20)
                        .addComponent(jLabel2, 400, 400, 400)
                        .addGap(30)
        );

        getContentPane().add(jPanel1);
        pack();
        setLocationRelativeTo(null);
    }

    // Variables Swing
    private JButton jButton1;
    private JButton jButton2;
    private JButton jButton3;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JPanel jPanel1;
    private JPanel jPanel2;
}

/*
package org.example.agence.main;

import org.example.agence.service.AgenceService;
import org.example.agence.service.HotelGrpcClientService;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class SOAP_Interface extends JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(SOAP_Interface.class.getName());

    public SOAP_Interface() {
        initComponents();
    }

    private void initComponents() {

        // Services à passer à la fenêtre de recherche
        AgenceService agenceService = new AgenceService();
        HotelGrpcClientService hotelGrpcClientService = new HotelGrpcClientService();

        jPanel1 = new JPanel();
        jPanel2 = new JPanel();
        jButton1 = new JButton();
        jButton2 = new JButton();
        jButton3 = new JButton();
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Application de réservation d'hôtels");

        jPanel1.setBackground(new Color(255, 250, 240));
        jPanel2.setBackground(new Color(220, 220, 220));

        Color boutonColor = new Color(218, 165, 32);
        jButton1.setBackground(boutonColor);
        jButton2.setBackground(boutonColor);
        jButton3.setBackground(boutonColor);

        jButton1.setForeground(Color.WHITE);
        jButton2.setForeground(Color.WHITE);
        jButton3.setForeground(Color.WHITE);

        jButton1.setFont(new Font("Liberation Sans", Font.BOLD, 24));
        jButton1.setText("Quitter");
        jButton1.setFocusPainted(false);
        jButton1.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));
        jButton1.addActionListener(e -> System.exit(0));

        jButton2.setFont(new Font("Liberation Sans", Font.BOLD, 24));
        jButton2.setText("Accueil");
        jButton2.setFocusPainted(false);
        jButton2.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));
        jButton2.addActionListener(e -> JOptionPane.showMessageDialog(this, "Vous êtes déjà sur l'accueil !"));

        jButton3.setFont(new Font("Liberation Sans", Font.BOLD, 24));
        jButton3.setText("Recherche Hôtel");
        jButton3.setFocusPainted(false);
        jButton3.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));
        jButton3.addActionListener(e -> {
            RechercheChambresUI rechercheWindow = new RechercheChambresUI(agenceService, hotelGrpcClientService);
            rechercheWindow.setVisible(true);
        });

        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createSequentialGroup()
                        .addGap(22)
                        .addComponent(jButton2, 235, 235, 235)
                        .addGap(98)
                        .addComponent(jButton3, 240, 240, 240)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
                        .addComponent(jButton1, 236, 236, 236)
                        .addGap(19)
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1, 65, 65, 65)
                        .addComponent(jButton2, 65, 65, 65)
                        .addComponent(jButton3, 65, 65, 65)
        );

        jLabel1.setFont(new Font("Liberation Sans", Font.BOLD | Font.ITALIC, 32));
        jLabel1.setForeground(new Color(50, 50, 50));
        jLabel1.setText("Bienvenue dans l'application de réservation d'hôtels");

        try {
            URL imgURL = getClass().getResource("/images/hotel_royal.jpg");
            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                Image image = icon.getImage().getScaledInstance(700, 400, Image.SCALE_SMOOTH);
                jLabel2.setIcon(new ImageIcon(image));
            } else {
                System.err.println("Image introuvable !");
            }
        } catch (Exception e) {
            System.err.println("Erreur de chargement de l'image : " + e.getMessage());
        }


        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addComponent(jLabel2, 700, 700, 700)
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(30)
                        .addComponent(jLabel1)
                        .addGap(20)
                        .addComponent(jLabel2, 400, 400, 400)
                        .addGap(30)
        );

        getContentPane().add(jPanel1);
        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String args[]) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }

        EventQueue.invokeLater(() -> new SOAP_Interface().setVisible(true));
    }

    private JButton jButton1;
    private JButton jButton2;
    private JButton jButton3;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JPanel jPanel1;
    private JPanel jPanel2;
}
*/
