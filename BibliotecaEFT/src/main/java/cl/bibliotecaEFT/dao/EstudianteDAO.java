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
 * @author Cristian Contreras
 * @version 1.0
 */
public class EstudianteDAO {

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

            ps.executeUpdate();
        }
    }

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