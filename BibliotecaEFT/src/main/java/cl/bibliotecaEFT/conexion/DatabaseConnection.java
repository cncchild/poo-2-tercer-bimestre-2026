package cl.bibliotecaEFT.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase Singleton encargada de gestionar la conexión
 * con la base de datos MySQL de la aplicación BibliotecaEFT.
 *
 * @author Cristian Contreras
 * @version 1.0
 */
public class DatabaseConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/biblioteca";

    private static final String USUARIO = "root";

    private static final String CONTRASENA = "";

    private static Connection instancia;

    /**
     * Constructor privado para implementar Singleton.
     */
    private DatabaseConnection() {
    }

    /**
     * Obtiene la única instancia de la conexión.
     *
     * @return conexión activa con la base de datos
     * @throws SQLException si ocurre un error al establecer la conexión
     */
    public static Connection getInstance() throws SQLException {

        if (instancia == null || instancia.isClosed()) {
            instancia = DriverManager.getConnection(
                    URL,
                    USUARIO,
                    CONTRASENA
            );
        }

        return instancia;
    }
}