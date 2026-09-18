package cl.speedfast.app;

import cl.speedfast.controlador.ControladorPedidos;
import cl.speedfast.controlador.ControladorUsuarios;
import cl.speedfast.vista.Login;

import javax.swing.SwingUtilities;

/**
 * Clase principal de la aplicación SpeedFast.
 *
 * Inicia el sistema mediante la ventana de Login.
 *
 * @author Cristian Contreras
 * @version 1.0
 */
public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            ControladorUsuarios controladorUsuarios =
                    new ControladorUsuarios();

            ControladorPedidos controladorPedidos =
                    new ControladorPedidos();

            Login login = new Login(
                    controladorUsuarios,
                    controladorPedidos
            );

            login.setVisible(true);
        });
    }
}