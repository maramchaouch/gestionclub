package ui;

import database.DBConnection;
import ui.CalendrierForm;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class InscriptionForm extends JFrame {

    private JTextField txtNom, txtPrenom, txtEmail;
    private JPasswordField txtPassword;
    private JComboBox<String> comboClub;
    private JComboBox<String> comboRole; // nouveau combo pour role

    public InscriptionForm() {
        setTitle("Inscription Membre");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();
        chargerClubs();
    }

    private void initComponents() {
        JLabel lblNom = new JLabel("Nom :");
        JLabel lblPrenom = new JLabel("Prénom :");
        JLabel lblEmail = new JLabel("Email :");
        JLabel lblPassword = new JLabel("Mot de passe :");
        JLabel lblClub = new JLabel("Club :");
        JLabel lblRole = new JLabel("Rôle :"); // label pour role

        txtNom = new JTextField(15);
        txtPrenom = new JTextField(15);
        txtEmail = new JTextField(15);
        txtPassword = new JPasswordField(15);
        comboClub = new JComboBox<>();
        comboRole = new JComboBox<>(new String[]{"user", "admin"}); // ajout des options role

        JButton btnOk = new JButton("OK");
        JButton btnAnnuler = new JButton("Annuler");

        btnOk.addActionListener(e -> inscrireMembre());
        btnAnnuler.addActionListener(e -> dispose());

        setLayout(new GridLayout(8, 2, 10, 10)); // 8 lignes maintenant

        add(lblNom); add(txtNom);
        add(lblPrenom); add(txtPrenom);
        add(lblEmail); add(txtEmail);
        add(lblPassword); add(txtPassword);
        add(lblClub); add(comboClub);
        add(lblRole); add(comboRole); // ajout dans le layout
        add(btnOk); add(btnAnnuler);
    }

    private void chargerClubs() {
        comboClub.removeAllItems(); // vider le combo
        String sql = "SELECT nom FROM club";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                comboClub.addItem(rs.getString("nom"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur chargement clubs : " + e.getMessage());
        }
    }

    private void inscrireMembre() {

        String nom = txtNom.getText();
        String prenom = txtPrenom.getText();
        String email = txtEmail.getText();
        String password = new String(txtPassword.getPassword());

        if (comboClub.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un club !");
            return;
        }

        String nomClub = comboClub.getSelectedItem().toString(); // juste le nom
        String role = comboRole.getSelectedItem().toString(); // récupérer le role

        try (Connection conn = DBConnection.getConnection()) {

            // 1️⃣ Récupérer l'id du club
            PreparedStatement psClub = conn.prepareStatement("SELECT idclub FROM club WHERE nom = ?");
            psClub.setString(1, nomClub);
            ResultSet rs = psClub.executeQuery();

            int idClub = -1;
            if (rs.next()) {
                idClub = rs.getInt("idclub");
            } else {
                JOptionPane.showMessageDialog(this, "Club non trouvé !");
                return;
            }

            // 2️⃣ Insérer le membre avec role
            String sqlInsert = "INSERT INTO membre (nom, prenom, email, password, idclub, role) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement psInsert = conn.prepareStatement(sqlInsert);
            psInsert.setString(1, nom);
            psInsert.setString(2, prenom);
            psInsert.setString(3, email);
            psInsert.setString(4, password);
            psInsert.setInt(5, idClub);
            psInsert.setString(6, role);

            psInsert.executeUpdate();

            JOptionPane.showMessageDialog(this, "Inscription réussie !");

            // 3️⃣ Ouvrir calendrier du club
            new CalendrierForm(idClub, nomClub).setVisible(true);
            dispose();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de l'inscription : " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new InscriptionForm().setVisible(true);
    }
}
