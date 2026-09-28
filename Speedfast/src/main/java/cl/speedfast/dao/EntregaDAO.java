package cl.speedfast.dao;

import cl.speedfast.conexion.ConexionBD;
import cl.speedfast.model.Entrega;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Acceso a datos de las entregas mediante JDBC.
 *
 * @author Cristian Contreras
 * @version 1.0
 */
public class EntregaDAO {

    /**
     * Guarda una entrega en la base de datos.
     *
     * @param entrega entrega que será almacenada
     * @throws SQLException si ocurre un error con la base de datos
     */
    public void guardar(Entrega entrega)
            throws SQLException {

        String sql = """
                INSERT INTO entrega
                (id_pedido, id_repartidor, fecha, hora)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conn =
                     ConexionBD.obtenerConexion();
             PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setInt(
                    1,
                    entrega.getIdPedido()
            );

            stmt.setInt(
                    2,
                    entrega.getIdRepartidor()
            );

            stmt.setDate(
                    3,
                    java.sql.Date.valueOf(
                            entrega.getFecha()
                    )
            );

            stmt.setTime(
                    4,
                    java.sql.Time.valueOf(
                            entrega.getHora()
                    )
            );

            stmt.executeUpdate();
        }
    }
}