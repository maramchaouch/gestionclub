package ui;

import database.DBConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class AdminDashboard extends JFrame {

    JRadioButton rbUsers, rbAddEvent;
    JTable table;

    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(700, 450);
        setLayout(null);

        rbUsers = new JRadioButton("Voir Users");
        rbAddEvent = new JRadioButton("Add Activity");

        ButtonGroup bg = new ButtonGroup();
        bg.add(rbUsers);
        bg.add(rbAddEvent);

        rbUsers.setBounds(30, 30, 150, 30);
        rbAddEvent.setBounds(30, 60, 150, 30);

        add(rbUsers);
        add(rbAddEvent);

        JButton btnOk = new JButton("OK");
        btnOk.setBounds(30, 100, 100, 30);
        add(btnOk);

        table = new JTable();
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(200, 30, 460, 350);
        add(sp);

        btnOk.addActionListener(e -> {
            if (rbUsers.isSelected()) {
                loadUsers();
            } else if (rbAddEvent.isSelected()) {
                new addactivity().setVisible(true);
            }
        });

        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void loadUsers() {
        try {
            Connection con = DBConnection.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM membre");

            DefaultTableModel model = new DefaultTableModel(
                    new String[]{"ID", "Nom", "Prénom", "Email", "Role", "ID Club"}, 0
            );

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("idMembre"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("role"),
                        rs.getInt("idClub")
                });
            }
            table.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
