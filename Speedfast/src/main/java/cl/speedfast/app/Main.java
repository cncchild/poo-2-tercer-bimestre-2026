package cl.speedfast;

import cl.speedfast.conexion.ConexionBD;
import cl.speedfast.controlador.ControladorUsuarios;
import cl.speedfast.dao.EntregaDAO;
import cl.speedfast.dao.PedidoDAO;
import cl.speedfast.dao.RepartidorDAO;
import cl.speedfast.vista.Login;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Clase principal de la aplicación SpeedFast.
 *
 * @author Cristian Contreras
 * @version 1.0
 */

public class Main {

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

            ControladorUsuarios controladorUsuarios =
                    new ControladorUsuarios();

            PedidoDAO pedidoDAO =
                    new PedidoDAO();

            RepartidorDAO repartidorDAO =
                    new RepartidorDAO();

            EntregaDAO entregaDAO =
                    new EntregaDAO();

            Login login = new Login(
                    controladorUsuarios,
                    pedidoDAO,
                    repartidorDAO,
                    entregaDAO
            );

            login.setVisible(true);
        });
    }
}