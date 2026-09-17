import javax.swing.*;
import controlador.ControladorUsuarios;
import vista.Login;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            ControladorUsuarios controlador = new ControladorUsuarios();
            Login ventanaLogin = new Login(controlador);
            ventanaLogin.setVisible(true);
        });
    }
}