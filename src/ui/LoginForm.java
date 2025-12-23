package ui;

import database.DBConnection;
import javax.swing.*;
import java.sql.*;

public class LoginForm extends JFrame {

    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public LoginForm() {
        setTitle("Login");
        setSize(350, 220);
        setLayout(null);

        JLabel lblTitle = new JLabel("LOGIN");
        lblTitle.setBounds(140, 10, 100, 25);
        add(lblTitle);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(30, 50, 100, 25);
        add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(120, 50, 170, 25);
        add(txtEmail);

        JLabel lblPass = new JLabel("Password:");
        lblPass.setBounds(30, 90, 100, 25);
        add(lblPass);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(120, 90, 170, 25);
        add(txtPassword);

        btnLogin = new JButton("Login");
        btnLogin.setBounds(120, 140, 100, 30);
        add(btnLogin);

        btnLogin.addActionListener(e -> login());

        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void login() {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(
                    "SELECT * FROM membre WHERE email=? AND password=?"
            );
            pst.setString(1, txtEmail.getText());
            pst.setString(2, String.valueOf(txtPassword.getPassword()));

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");
                int idClub = rs.getInt("idClub");

                if ("admin".equalsIgnoreCase(role)) {
                    new AdminDashboard().setVisible(true);
                } else {
                    new EventView(idClub).setVisible(true);
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Email ou mot de passe incorrect ❌");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
