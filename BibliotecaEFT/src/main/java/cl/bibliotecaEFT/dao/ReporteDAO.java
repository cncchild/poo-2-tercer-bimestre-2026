package cl.bibliotecaEFT.dao;

import cl.bibliotecaEFT.conexion.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO encargado de ejecutar las consultas relacionadas
 * con los reportes de la biblioteca.
 *
 * @author Cristian Contreras Child
 * @version 1.0
 */
public class ReporteDAO {

    /**
     * Obtiene los libros ordenados según la cantidad
     * de préstamos registrados.
     *
     * @return lista de resultados del reporte.
     * @throws SQLException si ocurre un error con la base de datos.
     */
    public List<String[]> librosMasPrestados()
            throws SQLException {

        List<String[]> resultados =
                new ArrayList<>();

        String sql = """
                SELECT l.titulo,
                       COUNT(p.id) AS cantidad_prestamos
                FROM libros l
                INNER JOIN prestamos p
                    ON l.id = p.id_libro
                GROUP BY l.id, l.titulo
                ORDER BY cantidad_prestamos DESC
                """;

        try (Connection conexion =
                     DatabaseConnection.getInstance();
             PreparedStatement ps =
                     conexion.prepareStatement(sql);
             ResultSet rs =
                     ps.executeQuery()) {

            while (rs.next()) {

                String titulo =
                        rs.getString("titulo");

                String cantidad =
                        String.valueOf(
                                rs.getInt(
                                        "cantidad_prestamos"
                                )
                        );

                resultados.add(
                        new String[]{
                                titulo,
                                cantidad
                        }
                );
            }
        }

        return resultados;
    }

    /**
     * Obtiene el historial de préstamos de un estudiante.
     *
     * @param idEstudiante identificador del estudiante.
     * @return lista con los préstamos realizados por el estudiante.
     * @throws SQLException si ocurre un error con la base de datos.
     */
    public List<String[]> historialEstudiante(
            int idEstudiante) throws SQLException {

        List<String[]> resultados =
                new ArrayList<>();

        String sql = """
            SELECT l.titulo,
                   p.fecha_prestamo,
                   p.fecha_devolucion,
                   p.devuelto
            FROM prestamos p
            INNER JOIN libros l
                ON p.id_libro = l.id
            WHERE p.id_estudiante = ?
            ORDER BY p.fecha_prestamo DESC
            """;

        try (Connection conexion =
                     DatabaseConnection.getInstance();
             PreparedStatement ps =
                     conexion.prepareStatement(sql)) {

            ps.setInt(1, idEstudiante);

            try (ResultSet rs =
                         ps.executeQuery()) {

                while (rs.next()) {

                    String titulo =
                            rs.getString("titulo");

                    String fechaPrestamo =
                            String.valueOf(
                                    rs.getDate("fecha_prestamo")
                            );

                    String fechaDevolucion =
                            String.valueOf(
                                    rs.getDate("fecha_devolucion")
                            );

                    String estado =
                            rs.getBoolean("devuelto")
                                    ? "Devuelto"
                                    : "Prestado";

                    resultados.add(
                            new String[]{
                                    titulo,
                                    fechaPrestamo,
                                    fechaDevolucion,
                                    estado
                            }
                    );
                }
            }
        }

        return resultados;
    }

    /**
     * Obtiene los libros que actualmente se encuentran
     * en préstamo y aún no han sido devueltos.
     *
     * @return lista con los estudiantes, libros y fechas
     *         correspondientes a los préstamos activos.
     * @throws SQLException si ocurre un error con la base de datos.
     */
    public List<String[]> librosActualmenteEnPrestamo()
            throws SQLException {

        List<String[]> resultados =
                new ArrayList<>();

        String sql = """
            SELECT e.nombre,
                   l.titulo,
                   p.fecha_prestamo,
                   p.fecha_devolucion
            FROM prestamos p
            INNER JOIN estudiantes e
                ON p.id_estudiante = e.id
            INNER JOIN libros l
                ON p.id_libro = l.id
            WHERE p.devuelto = false
            ORDER BY p.fecha_devolucion ASC
            """;

        try (Connection conexion =
                     DatabaseConnection.getInstance();
             PreparedStatement ps =
                     conexion.prepareStatement(sql);
             ResultSet rs =
                     ps.executeQuery()) {

            while (rs.next()) {

                String estudiante =
                        rs.getString("nombre");

                String libro =
                        rs.getString("titulo");

                String fechaPrestamo =
                        String.valueOf(
                                rs.getDate("fecha_prestamo")
                        );

                String fechaDevolucion =
                        String.valueOf(
                                rs.getDate("fecha_devolucion")
                        );

                resultados.add(
                        new String[]{
                                estudiante,
                                libro,
                                fechaPrestamo,
                                fechaDevolucion
                        }
                );
            }
        }

        return resultados;
    }
}