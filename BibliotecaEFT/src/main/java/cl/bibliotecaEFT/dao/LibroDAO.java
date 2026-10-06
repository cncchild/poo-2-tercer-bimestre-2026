package cl.bibliotecaEFT.dao;

import cl.bibliotecaEFT.conexion.DatabaseConnection;
import cl.bibliotecaEFT.modelo.Libro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO encargado de gestionar las operaciones de la tabla libros.
 *
 * @author Cristian Contreras Child
 * @version 1.0
 */
public class LibroDAO {

    /**
     * Guarda un nuevo libro en la base de datos.
     *
     * @param libro libro que se desea guardar.
     * @throws SQLException si ocurre un error al ejecutar la operación
     *                      en la base de datos.
     */
    public void guardar(Libro libro) throws SQLException {

        String sql = """
                INSERT INTO libros
                (titulo, autor, isbn, editorial, stock, id_categoria)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection conexion = DatabaseConnection.getInstance();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setString(3, libro.getIsbn());
            ps.setString(4, libro.getEditorial());
            ps.setInt(5, libro.getStock());
            ps.setInt(6, libro.getIdCategoria());

            ps.executeUpdate();
        }
    }

    /**
     * Obtiene todos los libros registrados en la base de datos.
     *
     * @return lista con todos los libros registrados.
     * @throws SQLException si ocurre un error al consultar la base de datos.
     */
    public List<Libro> listarTodos() throws SQLException {

        List<Libro> libros = new ArrayList<>();

        String sql = """
                SELECT id, titulo, autor, isbn, editorial, stock, id_categoria
                FROM libros
                """;

        try (Connection conexion = DatabaseConnection.getInstance();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Libro libro = new Libro();

                libro.setId(rs.getInt("id"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAutor(rs.getString("autor"));
                libro.setIsbn(rs.getString("isbn"));
                libro.setEditorial(rs.getString("editorial"));
                libro.setStock(rs.getInt("stock"));
                libro.setIdCategoria(rs.getInt("id_categoria"));

                libros.add(libro);
            }
        }

        return libros;
    }

    /**
     * Busca un libro utilizando su identificador.
     *
     * @param id identificador del libro.
     * @return libro encontrado o null si no existe.
     * @throws SQLException si ocurre un error al consultar la base de datos.
     */
    public Libro buscarPorId(int id) throws SQLException {

        String sql = """
            SELECT id, titulo, autor, isbn, editorial, stock, id_categoria
            FROM libros
            WHERE id = ?
            """;

        try (
                Connection conexion = DatabaseConnection.getInstance();
                PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return new Libro(
                            rs.getInt("id"),
                            rs.getString("titulo"),
                            rs.getString("autor"),
                            rs.getString("isbn"),
                            rs.getString("editorial"),
                            rs.getInt("stock"),
                            rs.getInt("id_categoria")
                    );
                }
            }
        }

        return null;
    }

    /**
     * Actualiza los datos de un libro existente.
     *
     * @param libro libro con los datos actualizados.
     * @throws SQLException si ocurre un error al actualizar
     *                      la información en la base de datos.
     */
    public void actualizar(Libro libro) throws SQLException {

        String sql = """
                UPDATE libros
                SET titulo = ?, autor = ?, isbn = ?, editorial = ?,
                    stock = ?, id_categoria = ?
                WHERE id = ?
                """;

        try (Connection conexion = DatabaseConnection.getInstance();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setString(3, libro.getIsbn());
            ps.setString(4, libro.getEditorial());
            ps.setInt(5, libro.getStock());
            ps.setInt(6, libro.getIdCategoria());
            ps.setInt(7, libro.getId());

            ps.executeUpdate();
        }
    }

    /**
     * Elimina un libro utilizando su identificador.
     *
     * @param id identificador del libro que se desea eliminar.
     * @throws SQLException si ocurre un error al eliminar el libro
     *                      o si tiene préstamos asociados.
     */
    public void eliminar(int id) throws SQLException {

        String sql = "DELETE FROM libros WHERE id = ?";

        try (Connection conexion = DatabaseConnection.getInstance();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);

            ps.executeUpdate();

        } catch (SQLException e) {

            if (e.getErrorCode() == 1451) {
                throw new SQLException(
                        "No se puede eliminar el libro porque tiene préstamos asociados."
                );
            }

            throw e;
        }
    }

    /**
     * Disminuye en una unidad el stock de un libro.
     *
     * @param idLibro identificador del libro.
     * @return true si el stock fue disminuido correctamente,
     *         false si el libro no existe o no tiene stock disponible.
     * @throws SQLException si ocurre un error al actualizar la base de datos.
     */
    public boolean disminuirStock(int idLibro)
            throws SQLException {

        String sql = """
            UPDATE libros
            SET stock = stock - 1
            WHERE id = ?
            AND stock > 0
            """;

        try (Connection conexion =
                     DatabaseConnection.getInstance();
             PreparedStatement ps =
                     conexion.prepareStatement(sql)) {

            ps.setInt(1, idLibro);

            int filasAfectadas =
                    ps.executeUpdate();

            return filasAfectadas > 0;
        }
    }

    /**
     * Aumenta en una unidad el stock de un libro.
     *
     * @param idLibro identificador del libro.
     * @throws SQLException si ocurre un error al actualizar
     *                      el stock en la base de datos.
     */
    public void aumentarStock(int idLibro) throws SQLException {

        String sql = """
            UPDATE libros
            SET stock = stock + 1
            WHERE id = ?
            """;

        try (Connection conexion = DatabaseConnection.getInstance();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idLibro);

            ps.executeUpdate();
        }
    }
}