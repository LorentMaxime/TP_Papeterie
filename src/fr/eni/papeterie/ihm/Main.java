package fr.eni.papeterie.ihm;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(
                () -> {
                    JFrame app = new GUI();
                }
        );
        JFrame app = new GUI();
    }
}