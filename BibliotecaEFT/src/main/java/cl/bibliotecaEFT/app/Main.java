package cl.bibliotecaEFT.app;
import cl.bibliotecaEFT.vista.*;
import cl.bibliotecaEFT.vista.Login;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new Login().setVisible(true);
        });
        //new GestionPrestamos().setVisible(true);
    }
}
