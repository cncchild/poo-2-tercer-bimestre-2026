package cl.speedfast.dao;

import cl.speedfast.conexion.ConexionBD;
import cl.speedfast.model.Repartidor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Acceso a datos de los repartidores mediante JDBC.
 *
 * @author Cristian Contreras
 * @version 1.0
 */
public class RepartidorDAO {

    /**
     * Obtiene todos los repartidores registrados
     * en la base de datos.
     *
     * @return lista de repartidores
     * @throws SQLException si ocurre un error con la base de datos
     */
    public List<Repartidor> listarTodos()
            throws SQLException {

        List<Repartidor> repartidores =
                new ArrayList<>();

        String sql = """
                SELECT id, nombre
                FROM repartidor
                ORDER BY id
                """;

        try (Connection conn =
                     ConexionBD.obtenerConexion();
             PreparedStatement stmt =
                     conn.prepareStatement(sql);
             ResultSet rs =
                     stmt.executeQuery()) {

            while (rs.next()) {

                int id =
                        rs.getInt("id");

                String nombre =
                        rs.getString("nombre");

                Repartidor repartidor =
                        new Repartidor(
                                id,
                                nombre
                        );

                repartidores.add(repartidor);
            }
        }

        return repartidores;
    }
    /**
     * Guarda un nuevo repartidor en la base de datos.
     *
     * @param repartidor repartidor que se desea registrar
     * @throws SQLException si ocurre un error con la base de datos
     */
    public void guardar(Repartidor repartidor)
            throws SQLException {

        String sql = """
                INSERT INTO repartidor (nombre)
                VALUES (?)
                """;

        try (Connection conn =
                     ConexionBD.obtenerConexion();
             PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setString(
                    1,
                    repartidor.getNombre()
            );

            stmt.executeUpdate();
        }
    }
}