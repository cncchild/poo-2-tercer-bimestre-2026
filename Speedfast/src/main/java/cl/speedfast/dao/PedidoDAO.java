package cl.speedfast.dao;

import cl.speedfast.conexion.ConexionBD;
import cl.speedfast.model.EstadoPedido;
import cl.speedfast.model.Pedido;
import cl.speedfast.model.PedidoComida;
import cl.speedfast.model.PedidoEncomienda;
import cl.speedfast.model.PedidoExpress;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Acceso a datos de los pedidos mediante JDBC.
 *
 * @author Cristian Contreras
 * @version 1.0
 */
public class PedidoDAO {

    /**
     * Guarda un pedido en la base de datos.
     *
     * El identificador es generado automáticamente
     * por MySQL mediante AUTO_INCREMENT.
     *
     * @param pedido pedido que se desea guardar
     * @throws SQLException si ocurre un error con la base de datos
     */
    public void guardar(Pedido pedido) throws SQLException {

        String sql = """
                INSERT INTO pedido
                (direccion, tipo, distancia_km, estado)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setString(
                    1,
                    pedido.getDireccionEntrega()
            );

            stmt.setString(
                    2,
                    pedido.obtenerTipoPedido()
            );

            stmt.setDouble(
                    3,
                    pedido.getDistanciaKm()
            );

            stmt.setString(
                    4,
                    pedido.getEstado().name()
            );

            stmt.executeUpdate();
        }
    }

    /**
     * Obtiene todos los pedidos desde MySQL.
     *
     * @return lista de pedidos
     * @throws SQLException si ocurre un error con la base de datos
     */
    public List<Pedido> listarTodos()
            throws SQLException {

        List<Pedido> pedidos =
                new ArrayList<>();

        String sql = """
                SELECT
                    p.id,
                    p.direccion,
                    p.tipo,
                    p.distancia_km,
                    p.estado,
                    r.nombre AS repartidor
                FROM pedido p
                LEFT JOIN entrega e
                    ON e.id = (
                        SELECT MAX(e2.id)
                        FROM entrega e2
                        WHERE e2.id_pedido = p.id
                    )
                LEFT JOIN repartidor r
                    ON r.id = e.id_repartidor
                ORDER BY p.id
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

                String direccion =
                        rs.getString("direccion");

                String tipo =
                        rs.getString("tipo");

                double distancia =
                        rs.getDouble("distancia_km");

                String estado =
                        rs.getString("estado");

                String repartidor =
                        rs.getString("repartidor");

                Pedido pedido =
                        crearPedido(
                                tipo,
                                id,
                                direccion,
                                distancia
                        );

                if (pedido != null) {

                    pedido.setEstado(
                            EstadoPedido.valueOf(
                                    estado
                            )
                    );

                    if (repartidor != null) {

                        pedido.asignarRepartidor(
                                repartidor
                        );
                    }

                    pedidos.add(pedido);
                }
            }
        }

        return pedidos;
    }

    /**
     * Actualiza la dirección y distancia
     * de un pedido.
     *
     * @param pedido pedido que se desea actualizar
     * @throws SQLException si ocurre un error con la base de datos
     */
    public void actualizar(Pedido pedido)
            throws SQLException {

        String sql = """
                UPDATE pedido
                SET direccion = ?,
                    distancia_km = ?
                WHERE id = ?
                """;

        try (Connection conn =
                     ConexionBD.obtenerConexion();
             PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setString(
                    1,
                    pedido.getDireccionEntrega()
            );

            stmt.setDouble(
                    2,
                    pedido.getDistanciaKm()
            );

            stmt.setInt(
                    3,
                    pedido.getIdPedido()
            );

            stmt.executeUpdate();
        }
    }

    /**
     * Elimina un pedido de la base de datos.
     *
     * @param id identificador del pedido
     * @throws SQLException si ocurre un error con la base de datos
     */
    public void eliminar(int id) throws SQLException {

        String sql = """
            DELETE FROM pedido
            WHERE id = ?
            """;

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            stmt.executeUpdate();
        }
    }
    
    /**
     * Actualiza el estado de un pedido.
     *
     * @param id identificador del pedido
     * @param estado nuevo estado del pedido
     * @throws SQLException si ocurre un error con la base de datos
     */
    public void actualizarEstado(
            int id,
            EstadoPedido estado)
            throws SQLException {

        String sql = """
                UPDATE pedido
                SET estado = ?
                WHERE id = ?
                """;

        try (Connection conn =
                     ConexionBD.obtenerConexion();
             PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setString(
                    1,
                    estado.name()
            );

            stmt.setInt(
                    2,
                    id
            );

            stmt.executeUpdate();
        }
    }

    /**
     * Crea la subclase correspondiente
     * según el tipo de pedido almacenado.
     *
     * @param tipo tipo de pedido
     * @param id identificador del pedido
     * @param direccion dirección de entrega
     * @param distancia distancia del pedido
     * @return objeto Pedido correspondiente
     */
    private Pedido crearPedido(
            String tipo,
            int id,
            String direccion,
            double distancia) {

        String tipoNormalizado =
                tipo.trim().toUpperCase();

        switch (tipoNormalizado) {

            case "COMIDA":
            case "PEDIDO COMIDA":
                return new PedidoComida(
                        id,
                        direccion,
                        distancia
                );

            case "ENCOMIENDA":
            case "PEDIDO ENCOMIENDA":
                return new PedidoEncomienda(
                        id,
                        direccion,
                        distancia
                );

            case "EXPRESS":
            case "PEDIDO EXPRESS":
                return new PedidoExpress(
                        id,
                        direccion,
                        distancia
                );

            default:
                return null;
        }
    }
    /**
     * Obtiene el siguiente identificador disponible para un pedido.
     *
     * @return siguiente identificador disponible
     * @throws SQLException si ocurre un error con la base de datos
     */
    public int obtenerSiguienteId() throws SQLException {

        String sql = """
            SELECT COALESCE(MAX(id), 0) + 1 AS siguiente_id
            FROM pedido
            """;

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("siguiente_id");
            }
        }

        return 1;
    }
}