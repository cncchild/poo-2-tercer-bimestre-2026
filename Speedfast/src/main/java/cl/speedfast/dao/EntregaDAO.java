package cl.speedfast.dao;

import cl.speedfast.conexion.ConexionBD;
import cl.speedfast.model.Entrega;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * Obtiene todas las entregas registradas
     * en la base de datos.
     *
     * @return lista de entregas
     * @throws SQLException si ocurre un error con la base de datos
     */
    public List<Entrega> listarTodos()
            throws SQLException {

        List<Entrega> entregas =
                new ArrayList<>();

        String sql = """
                SELECT id, id_pedido, id_repartidor, fecha, hora
                FROM entrega
                ORDER BY id
                """;

        try (Connection conn =
                     ConexionBD.obtenerConexion();
             PreparedStatement stmt =
                     conn.prepareStatement(sql);
             ResultSet rs =
                     stmt.executeQuery()) {

            while (rs.next()) {

                Entrega entrega =
                        new Entrega(
                                rs.getInt("id"),
                                rs.getInt("id_pedido"),
                                rs.getInt("id_repartidor"),
                                rs.getDate("fecha").toLocalDate(),
                                rs.getTime("hora").toLocalTime()
                        );

                entregas.add(entrega);
            }
        }

        return entregas;
    }

    /**
     * Actualiza una entrega existente.
     *
     * @param entrega entrega que se desea actualizar
     * @throws SQLException si ocurre un error con la base de datos
     */
    public void actualizar(Entrega entrega)
            throws SQLException {

        String sql = """
                UPDATE entrega
                SET id_pedido = ?,
                    id_repartidor = ?,
                    fecha = ?,
                    hora = ?
                WHERE id = ?
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

            stmt.setInt(
                    5,
                    entrega.getId()
            );

            stmt.executeUpdate();
        }
    }

    /**
     * Elimina una entrega de la base de datos.
     *
     * @param id identificador de la entrega
     * @throws SQLException si ocurre un error con la base de datos
     */
    public void eliminar(int id) throws SQLException {

        String sql = """
        DELETE FROM entrega
        WHERE id = ?
        """;

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            stmt.executeUpdate();
        }
    }
}