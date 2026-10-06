package cl.bibliotecaEFT.dao;

import cl.bibliotecaEFT.conexion.DatabaseConnection;
import cl.bibliotecaEFT.modelo.Estudiante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO encargado de gestionar las operaciones de la tabla estudiantes.
 *
 * @author Cristian Contreras Child
 * @version 1.0
 */
public class EstudianteDAO {

    /**
     * Guarda un nuevo estudiante en la base de datos.
     *
     * @param estudiante estudiante que se desea guardar.
     * @throws SQLException si ocurre un error al ejecutar la operación
     *                      en la base de datos.
     */
    public void guardar(Estudiante estudiante) throws SQLException {

        String sql = """
                INSERT INTO estudiantes
                (nombre, rut, curso, correo)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conexion = DatabaseConnection.getInstance();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, estudiante.getNombre());
            ps.setString(2, estudiante.getRut());
            ps.setString(3, estudiante.getCurso());
            ps.setString(4, estudiante.getCorreo());

            int filasAfectadas = ps.executeUpdate();

            System.out.println(
                    "Estudiante guardado. Filas afectadas: "
                            + filasAfectadas
            );
        }
    }

    /**
     * Obtiene todos los estudiantes registrados.
     *
     * @return lista con todos los estudiantes registrados.
     * @throws SQLException si ocurre un error al consultar la base de datos.
     */
    public List<Estudiante> listarTodos() throws SQLException {

        List<Estudiante> estudiantes = new ArrayList<>();

        String sql = """
                SELECT id, nombre, rut, curso, correo
                FROM estudiantes
                """;

        try (Connection conexion = DatabaseConnection.getInstance();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Estudiante estudiante = new Estudiante();

                estudiante.setId(rs.getInt("id"));
                estudiante.setNombre(rs.getString("nombre"));
                estudiante.setRut(rs.getString("rut"));
                estudiante.setCurso(rs.getString("curso"));
                estudiante.setCorreo(rs.getString("correo"));

                estudiantes.add(estudiante);
            }
        }

        return estudiantes;
    }

    /**
     * Busca un estudiante por su identificador.
     *
     * @param id identificador del estudiante.
     * @return estudiante encontrado o null si no existe.
     * @throws SQLException si ocurre un error al consultar la base de datos.
     */
    public Estudiante buscarPorId(int id) throws SQLException {

        String sql = """
                SELECT id, nombre, rut, curso, correo
                FROM estudiantes
                WHERE id = ?
                """;

        try (Connection conexion = DatabaseConnection.getInstance();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Estudiante estudiante = new Estudiante();

                    estudiante.setId(rs.getInt("id"));
                    estudiante.setNombre(rs.getString("nombre"));
                    estudiante.setRut(rs.getString("rut"));
                    estudiante.setCurso(rs.getString("curso"));
                    estudiante.setCorreo(rs.getString("correo"));

                    return estudiante;
                }
            }
        }

        return null;
    }

    /**
     * Actualiza los datos de un estudiante existente.
     *
     * @param estudiante estudiante con los datos actualizados.
     * @throws SQLException si ocurre un error al actualizar
     *                      la información en la base de datos.
     */
    public void actualizar(Estudiante estudiante) throws SQLException {

        String sql = """
            UPDATE estudiantes
            SET nombre = ?, rut = ?, curso = ?, correo = ?
            WHERE id = ?
            """;

        try (Connection conexion = DatabaseConnection.getInstance();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, estudiante.getNombre());
            ps.setString(2, estudiante.getRut());
            ps.setString(3, estudiante.getCurso());
            ps.setString(4, estudiante.getCorreo());
            ps.setInt(5, estudiante.getId());

            ps.executeUpdate();
        }
    }

    /**
     * Elimina un estudiante utilizando su identificador.
     *
     * @param id identificador del estudiante que se desea eliminar.
     * @throws SQLException si ocurre un error al eliminar el estudiante
     *                      o si tiene préstamos asociados.
     */
    public void eliminar(int id) throws SQLException {

        String sql = """
        DELETE FROM estudiantes
        WHERE id = ?
        """;

        try (Connection conexion = DatabaseConnection.getInstance();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);

            ps.executeUpdate();

        } catch (SQLException e) {

            if (e.getErrorCode() == 1451) {
                throw new SQLException(
                        "No se puede eliminar el estudiante porque tiene préstamos asociados."
                );
            }

            throw e;
        }
    }
}