package cl.bibliotecaEFT.dao;

import cl.bibliotecaEFT.conexion.DatabaseConnection;
import cl.bibliotecaEFT.modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO encargado de gestionar las operaciones de la tabla usuarios.
 *
 * @author Cristian Contreras Child
 * @version 1.0
 */
public class UsuarioDAO {

    /**
     * Guarda un nuevo usuario en la base de datos.
     *
     * @param usuario objeto Usuario que contiene los datos a registrar.
     * @throws SQLException si ocurre un error durante la operación
     * de inserción en la base de datos.
     */
    public void guardar(Usuario usuario) throws SQLException {

        String sql = """
                INSERT INTO usuarios
                (nombre, rut, correo, contraseña, rol)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conexion = DatabaseConnection.getInstance();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getRut());
            ps.setString(3, usuario.getCorreo());
            ps.setString(4, usuario.getContraseña());
            ps.setString(5, usuario.getRol());

            ps.executeUpdate();
        }
    }

    /**
     * Obtiene todos los usuarios registrados en la base de datos.
     *
     * @return lista con todos los usuarios registrados.
     * @throws SQLException si ocurre un error durante la consulta
     * a la base de datos.
     */
    public List<Usuario> listarTodos() throws SQLException {

        List<Usuario> usuarios = new ArrayList<>();

        String sql = """
                SELECT id, nombre, rut, correo, contraseña, rol
                FROM usuarios
                """;

        try (Connection conexion = DatabaseConnection.getInstance();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Usuario usuario = new Usuario();

                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setRut(rs.getString("rut"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setContraseña(rs.getString("contraseña"));
                usuario.setRol(rs.getString("rol"));

                usuarios.add(usuario);
            }
        }

        return usuarios;
    }

    /**
     * Busca un usuario utilizando su RUT.
     *
     * @param rut RUT del usuario que se desea buscar.
     * @return el usuario encontrado o {@code null} si no existe.
     * @throws SQLException si ocurre un error durante la consulta
     * a la base de datos.
     */
    public Usuario buscarPorRut(String rut) throws SQLException {

        String sql = """
                SELECT id, nombre, rut, correo, contraseña, rol
                FROM usuarios
                WHERE rut = ?
                """;

        try (Connection conexion = DatabaseConnection.getInstance();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, rut);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Usuario usuario = new Usuario();

                    usuario.setId(rs.getInt("id"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setRut(rs.getString("rut"));
                    usuario.setCorreo(rs.getString("correo"));
                    usuario.setContraseña(rs.getString("contraseña"));
                    usuario.setRol(rs.getString("rol"));

                    return usuario;
                }
            }
        }

        return null;
    }
}