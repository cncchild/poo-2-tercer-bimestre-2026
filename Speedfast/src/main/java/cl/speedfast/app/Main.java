package cl.speedfast.app;

import cl.speedfast.conexion.ConexionBD;
import cl.speedfast.dao.EntregaDAO;
import cl.speedfast.dao.PedidoDAO;
import cl.speedfast.dao.RepartidorDAO;
import cl.speedfast.vista.Login;
import cl.speedfast.dao.UsuarioDAO;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Clase principal de la aplicación SpeedFast.
 *
 * Se encarga de comprobar la conexión con la base de datos
 * e iniciar la interfaz gráfica de la aplicación.
 *
 * @author Cristian Contreras
 * @version 1.0
 */
public class Main {

    /**
     * Punto de entrada principal de la aplicación.
     *
     * Verifica la conexión con la base de datos y posteriormente
     * inicia la ventana de Login utilizando el hilo de eventos
     * de Swing.
     *
     * @param args argumentos recibidos desde la línea de comandos
     */
    public static void main(String[] args) {

        try (Connection conn = ConexionBD.obtenerConexion()) {

            System.out.println(
                    "✅ Conexión exitosa a la base de datos."
            );

        } catch (SQLException e) {

            System.err.println(
                    "❌ Error al conectar con la base de datos:"
            );

            e.printStackTrace();

            return;
        }

        SwingUtilities.invokeLater(() -> {

            UsuarioDAO usuarioDAO = new UsuarioDAO();

            PedidoDAO pedidoDAO = new PedidoDAO();
            RepartidorDAO repartidorDAO = new RepartidorDAO();
            EntregaDAO entregaDAO = new EntregaDAO();

            Login login = new Login(
                    usuarioDAO,
                    pedidoDAO,
                    repartidorDAO,
                    entregaDAO
            );

            login.setVisible(true);
        });
    }
}