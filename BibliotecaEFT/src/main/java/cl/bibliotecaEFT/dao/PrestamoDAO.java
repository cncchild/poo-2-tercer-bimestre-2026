package cl.bibliotecaEFT.dao;

import cl.bibliotecaEFT.conexion.DatabaseConnection;
import cl.bibliotecaEFT.modelo.Prestamo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO encargado de gestionar las operaciones de la tabla prestamos.
 *
 * @author Cristian Contreras Child
 * @version 1.0
 */
public class PrestamoDAO {

    /**
     * Guarda un nuevo préstamo en la base de datos.
     *
     * @param prestamo préstamo que se desea registrar.
     * @throws SQLException si ocurre un error al insertar el préstamo
     *                      en la base de datos.
     */
    public void guardar(Prestamo prestamo) throws SQLException {

        String sql = """
                INSERT INTO prestamos
                (id_estudiante, id_libro, fecha_prestamo,
                 fecha_devolucion, devuelto)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conexion = DatabaseConnection.getInstance();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, prestamo.getIdEstudiante());
            ps.setInt(2, prestamo.getIdLibro());
            ps.setDate(3,
                    java.sql.Date.valueOf(prestamo.getFechaPrestamo()));

            if (prestamo.getFechaDevolucion() != null) {
                ps.setDate(4,
                        java.sql.Date.valueOf(prestamo.getFechaDevolucion()));
            } else {
                ps.setDate(4, null);
            }

            ps.setBoolean(5, prestamo.isDevuelto());

            ps.executeUpdate();
        }
    }

    /**
     * Obtiene todos los préstamos registrados.
     *
     * @return lista con todos los préstamos registrados.
     * @throws SQLException si ocurre un error al consultar la base de datos.
     */
    public List<Prestamo> listarTodos() throws SQLException {

        List<Prestamo> prestamos = new ArrayList<>();

        String sql = """
                SELECT id, id_estudiante, id_libro,
                       fecha_prestamo, fecha_devolucion, devuelto
                FROM prestamos
                """;

        try (Connection conexion = DatabaseConnection.getInstance();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Prestamo prestamo = new Prestamo();

                prestamo.setId(rs.getInt("id"));
                prestamo.setIdEstudiante(rs.getInt("id_estudiante"));
                prestamo.setIdLibro(rs.getInt("id_libro"));

                if (rs.getDate("fecha_prestamo") != null) {
                    prestamo.setFechaPrestamo(
                            rs.getDate("fecha_prestamo").toLocalDate()
                    );
                }

                if (rs.getDate("fecha_devolucion") != null) {
                    prestamo.setFechaDevolucion(
                            rs.getDate("fecha_devolucion").toLocalDate()
                    );
                }

                prestamo.setDevuelto(rs.getBoolean("devuelto"));

                prestamos.add(prestamo);
            }
        }

        return prestamos;
    }

    /**
     * Busca un préstamo utilizando su identificador.
     *
     * @param id identificador del préstamo.
     * @return préstamo encontrado o null si no existe.
     * @throws SQLException si ocurre un error al consultar la base de datos.
     */
    public Prestamo buscarPorId(int id) throws SQLException {

        String sql = """
                SELECT id, id_estudiante, id_libro,
                       fecha_prestamo, fecha_devolucion, devuelto
                FROM prestamos
                WHERE id = ?
                """;

        try (Connection conexion = DatabaseConnection.getInstance();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Prestamo prestamo = new Prestamo();

                    prestamo.setId(rs.getInt("id"));
                    prestamo.setIdEstudiante(rs.getInt("id_estudiante"));
                    prestamo.setIdLibro(rs.getInt("id_libro"));

                    if (rs.getDate("fecha_prestamo") != null) {
                        prestamo.setFechaPrestamo(
                                rs.getDate("fecha_prestamo").toLocalDate()
                        );
                    }

                    if (rs.getDate("fecha_devolucion") != null) {
                        prestamo.setFechaDevolucion(
                                rs.getDate("fecha_devolucion").toLocalDate()
                        );
                    }

                    prestamo.setDevuelto(rs.getBoolean("devuelto"));

                    return prestamo;
                }
            }
        }

        return null;
    }

    /**
     * Actualiza los datos de un préstamo existente.
     *
     * @param prestamo préstamo con los datos actualizados.
     * @throws SQLException si ocurre un error al actualizar el préstamo
     *                      en la base de datos.
     */
    public void actualizar(Prestamo prestamo) throws SQLException {

        String sql = """
                UPDATE prestamos
                SET id_estudiante = ?,
                    id_libro = ?,
                    fecha_prestamo = ?,
                    fecha_devolucion = ?,
                    devuelto = ?
                WHERE id = ?
                """;

        try (Connection conexion = DatabaseConnection.getInstance();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, prestamo.getIdEstudiante());
            ps.setInt(2, prestamo.getIdLibro());

            ps.setDate(3,
                    java.sql.Date.valueOf(prestamo.getFechaPrestamo()));

            if (prestamo.getFechaDevolucion() != null) {
                ps.setDate(4,
                        java.sql.Date.valueOf(prestamo.getFechaDevolucion()));
            } else {
                ps.setDate(4, null);
            }

            ps.setBoolean(5, prestamo.isDevuelto());
            ps.setInt(6, prestamo.getId());

            ps.executeUpdate();
        }
    }

    /**
     * Elimina un préstamo utilizando su identificador.
     *
     * @param id identificador del préstamo que se desea eliminar.
     * @throws SQLException si ocurre un error al eliminar el préstamo
     *                      de la base de datos.
     */
    public void eliminar(int id) throws SQLException {

        String sql = "DELETE FROM prestamos WHERE id = ?";

        try (Connection conexion = DatabaseConnection.getInstance();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);

            ps.executeUpdate();
        }
    }
}