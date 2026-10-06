package cl.bibliotecaEFT.dao;

import cl.bibliotecaEFT.conexion.DatabaseConnection;
import cl.bibliotecaEFT.modelo.Categoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO encargado de gestionar las operaciones de la tabla categorias.
 *
 * @author Cristian Contreras Child
 * @version 1.0
 */
public class CategoriaDAO {

    /**
     * Guarda una nueva categoría en la base de datos.
     *
     * @param categoria categoría que se desea guardar.
     * @throws SQLException si ocurre un error al ejecutar la operación
     *                      en la base de datos.
     */
    public void guardar(Categoria categoria) throws SQLException {

        String sql = "INSERT INTO categorias (nombre) VALUES (?)";

        try (Connection conexion = DatabaseConnection.getInstance();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, categoria.getNombre());
            ps.executeUpdate();
        }
    }

    /**
     * Obtiene todas las categorías registradas en la base de datos.
     *
     * @return lista con todas las categorías registradas.
     * @throws SQLException si ocurre un error al consultar la base de datos.
     */
    public List<Categoria> listarTodos() throws SQLException {

        List<Categoria> categorias = new ArrayList<>();

        String sql = "SELECT id, nombre FROM categorias";

        try (Connection conexion = DatabaseConnection.getInstance();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Categoria categoria = new Categoria();

                categoria.setId(rs.getInt("id"));
                categoria.setNombre(rs.getString("nombre"));

                categorias.add(categoria);
            }
        }

        return categorias;
    }
}