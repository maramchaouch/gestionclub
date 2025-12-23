package ui;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Première fenêtre : Bienvenue
            new BienvenueForm().setVisible(true);
        });
    }
}
