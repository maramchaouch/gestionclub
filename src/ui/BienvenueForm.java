package ui;

import javax.swing.*;
import java.awt.*;

public class BienvenueForm extends JFrame {

    private JRadioButton rbInscrire;
    private JRadioButton rbConnecter;

    public BienvenueForm() {

        setTitle("Bienvenue");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();
    }

    private void initComponents() {

        JLabel lblBienvenue = new JLabel("Bienvenue");
        lblBienvenue.setFont(new Font("Arial", Font.BOLD, 18));
        lblBienvenue.setHorizontalAlignment(SwingConstants.CENTER);

        rbInscrire = new JRadioButton("S'inscrire");
        rbConnecter = new JRadioButton("Se connecter");

        ButtonGroup group = new ButtonGroup();
        group.add(rbInscrire);
        group.add(rbConnecter);

        JButton btnOk = new JButton("OK");
        JButton btnAnnuler = new JButton("Annuler");

        btnOk.addActionListener(e -> actionOk());
        btnAnnuler.addActionListener(e -> System.exit(0));

        JPanel panelRadio = new JPanel(new GridLayout(2, 1));
        panelRadio.add(rbInscrire);
        panelRadio.add(rbConnecter);

        JPanel panelButtons = new JPanel();
        panelButtons.add(btnOk);
        panelButtons.add(btnAnnuler);

        setLayout(new BorderLayout());
        add(lblBienvenue, BorderLayout.NORTH);
        add(panelRadio, BorderLayout.CENTER);
        add(panelButtons, BorderLayout.SOUTH);
    }

    private void actionOk() {
        if (rbInscrire.isSelected()) {
            new InscriptionForm().setVisible(true);
            dispose();
        } else if (rbConnecter.isSelected()) {
            new LoginForm().setVisible(true); // ouvrir la fenêtre Login
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez choisir une option");
        }
    }


    public static void main(String[] args) {
        new BienvenueForm().setVisible(true);
    }
}
