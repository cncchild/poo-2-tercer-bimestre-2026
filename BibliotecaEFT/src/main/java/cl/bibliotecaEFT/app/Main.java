package cl.bibliotecaEFT.app;

import cl.bibliotecaEFT.vista.*;

import javax.swing.SwingUtilities;

/**
 * Clase principal de la aplicación BibliotecaEFT.
 * <p>
 * Se encarga de iniciar la aplicación y mostrar la ventana
 * de inicio de sesión.
 *
 * @author Cristian Contreras Child
 * @version 1.0
 */
public class Main {

    /**
     * Método principal que inicia la aplicación BibliotecaEFT.
     * <p>
     * Utiliza {@link SwingUtilities#invokeLater(Runnable)} para
     * ejecutar la interfaz gráfica dentro del Event Dispatch Thread
     * de Swing.
     *
     * @param args argumentos recibidos desde la línea de comandos
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new Login().setVisible(true);
        });
    }
}