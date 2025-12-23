package ui;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import database.DBConnection;

public class CalendrierForm extends JFrame {

    public CalendrierForm(int idClub, String nomClub) {

        setTitle("Calendrier des événements");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel lblTitre = new JLabel("Calendrier des événements du club : " + nomClub);
        lblTitre.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitre.setHorizontalAlignment(SwingConstants.CENTER);

        JTextArea txtCalendrier = new JTextArea();
        txtCalendrier.setEditable(false);

        // Charger les événements depuis la base
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT activite, type, date_event FROM gestion_event WHERE idclub = ?"
             )) {

            ps.setInt(1, idClub);
            ResultSet rs = ps.executeQuery();

            StringBuilder sb = new StringBuilder();
            boolean hasEvents = false;
            while (rs.next()) {
                hasEvents = true;
                sb.append("Activité: ").append(rs.getString("activite"))
                        .append(", Type: ").append(rs.getString("type"))
                        .append(", Date: ").append(rs.getDate("date_event"))
                        .append("\n");
            }

            if (!hasEvents) {
                sb.append("Aucun événement programmé pour le moment.\n");
            }

            txtCalendrier.setText(sb.toString());

        } catch (SQLException e) {
            e.printStackTrace();
            txtCalendrier.setText("Erreur lors du chargement des événements !");
        }

        JButton btnOk = new JButton("OK");
        JButton btnAnnuler = new JButton("Annuler");

        btnOk.addActionListener(e -> dispose());
        btnAnnuler.addActionListener(e -> dispose());

        JPanel panelButtons = new JPanel();
        panelButtons.add(btnOk);
        panelButtons.add(btnAnnuler);

        setLayout(new BorderLayout());
        add(lblTitre, BorderLayout.NORTH);
        add(new JScrollPane(txtCalendrier), BorderLayout.CENTER);
        add(panelButtons, BorderLayout.SOUTH);
    }
}
