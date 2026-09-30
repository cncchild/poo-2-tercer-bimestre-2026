package cl.speedfast.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase encargada de gestionar la conexión con la base de datos
 * MySQL utilizada por la aplicación SpeedFast.
 *
 * @author Cristian Contreras
 * @version 1.0
 */
public class ConexionBD {

    private static final String URL =
            "jdbc:mysql://localhost:3306/speedfast_db";

    private static final String USUARIO = "root";

    private static final String CONTRASENA = "";

    /**
     * Establece y obtiene una conexión con la base de datos.
     *
     * Utiliza los datos de conexión definidos en la clase
     * para acceder a la base de datos speedfast_db.
     *
     * @return una conexión activa con la base de datos
     * @throws SQLException si ocurre un error al establecer
     *                      la conexión
     */
    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(
                URL,
                USUARIO,
                CONTRASENA
        );
    }
}