package ui;

import database.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class addactivity extends JFrame {

    JComboBox<String> comboClub;
    JTextField txtActivite, txtType, txtDate;

    public addactivity() {
        setTitle("Add Activity");
        setSize(400, 300);
        setLayout(new GridLayout(5, 2, 10, 10));

        comboClub = new JComboBox<>();
        txtActivite = new JTextField();
        txtType = new JTextField();
        txtDate = new JTextField("yyyy-MM-dd");

        add(new JLabel("Club"));
        add(comboClub);
        add(new JLabel("Activité"));
        add(txtActivite);
        add(new JLabel("Type"));
        add(txtType);
        add(new JLabel("Date"));
        add(txtDate);

        JButton btnOk = new JButton("Add");
        add(btnOk);

        loadClubs();
        btnOk.addActionListener(e -> insertEvent());

        setLocationRelativeTo(null);
    }

    private void loadClubs() {
        try {
            Connection con = DBConnection.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT nom FROM club");
            while (rs.next()) comboClub.addItem(rs.getString("nom"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertEvent() {
        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps1 = con.prepareStatement(
                    "SELECT idclub FROM club WHERE nom=?"
            );
            ps1.setString(1, comboClub.getSelectedItem().toString());
            ResultSet rs = ps1.executeQuery();
            rs.next();

            int idClub = rs.getInt(1);

            PreparedStatement ps2 = con.prepareStatement(
                    "INSERT INTO gestion_event (activite, type, date_event, idclub) VALUES (?,?,?,?)"
            );
            ps2.setString(1, txtActivite.getText());
            ps2.setString(2, txtType.getText());
            ps2.setDate(3, Date.valueOf(txtDate.getText()));
            ps2.setInt(4, idClub);

            ps2.executeUpdate();
            JOptionPane.showMessageDialog(this, "Activity added ✅");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
