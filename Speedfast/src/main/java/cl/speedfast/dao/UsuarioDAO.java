package cl.speedfast.dao;

import cl.speedfast.conexion.ConexionBD;
import cl.speedfast.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Acceso a datos de los usuarios mediante JDBC.
 *
 * Se encarga de registrar, autenticar, listar, actualizar
 * y eliminar usuarios de la base de datos.
 *
 * @author Cristian Contreras
 * @version 1.0
 */
public class UsuarioDAO {

    /**
     * Guarda un nuevo usuario en la base de datos.
     *
     * @param usuario usuario que se desea registrar
     * @return true si el usuario fue guardado correctamente;
     *         false si ocurre un error
     */
    public boolean guardar(Usuario usuario) {

        String sql = """
                INSERT INTO usuario
                (nombre_usuario, contrasenia, rol)
                VALUES (?, ?, ?)
                """;

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, usuario.getNombreUsuario());
            ps.setString(2, usuario.getContrasenia());
            ps.setString(3, usuario.getRol());

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {

            System.out.println(
                    "Error al guardar usuario: "
                            + e.getMessage()
            );

            return false;
        }
    }

    /**
     * Autentica un usuario utilizando su nombre de usuario
     * y contraseña.
     *
     * @param nombreUsuario nombre del usuario
     * @param contrasenia contraseña del usuario
     * @return usuario autenticado o null si las credenciales
     *         no son válidas
     */
    public Usuario autenticar(
            String nombreUsuario,
            String contrasenia) {

        String sql = """
                SELECT nombre_usuario, contrasenia, rol
                FROM usuario
                WHERE nombre_usuario = ?
                AND contrasenia = ?
                """;

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, nombreUsuario);
            ps.setString(2, contrasenia);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return new Usuario(
                        rs.getString("nombre_usuario"),
                        rs.getString("contrasenia"),
                        rs.getString("rol")
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al autenticar usuario: "
                            + e.getMessage()
            );
        }

        return null;
    }

    /**
     * Obtiene todos los usuarios registrados
     * en la base de datos.
     *
     * @return lista de usuarios registrados
     */
    public List<Usuario> listarTodos() {

        List<Usuario> usuarios = new ArrayList<>();

        String sql = """
                SELECT nombre_usuario, contrasenia, rol
                FROM usuario
                """;

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                usuarios.add(
                        new Usuario(
                                rs.getString("nombre_usuario"),
                                rs.getString("contrasenia"),
                                rs.getString("rol")
                        )
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al listar usuarios: "
                            + e.getMessage()
            );
        }

        return usuarios;
    }

    /**
     * Actualiza los datos de un usuario existente.
     *
     * @param nombreUsuarioOriginal nombre de usuario original
     * @param usuario usuario con los nuevos datos
     * @return true si el usuario fue actualizado correctamente;
     *         false si ocurre un error
     */
    public boolean actualizar(
            String nombreUsuarioOriginal,
            Usuario usuario) {

        String sql = """
                UPDATE usuario
                SET nombre_usuario = ?,
                    contrasenia = ?,
                    rol = ?
                WHERE nombre_usuario = ?
                """;

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, usuario.getNombreUsuario());
            ps.setString(2, usuario.getContrasenia());
            ps.setString(3, usuario.getRol());
            ps.setString(4, nombreUsuarioOriginal);

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {

            System.out.println(
                    "Error al actualizar usuario: "
                            + e.getMessage()
            );

            return false;
        }
    }

    /**
     * Elimina un usuario de la base de datos.
     *
     * @param nombreUsuario nombre del usuario que se desea eliminar
     * @return true si el usuario fue eliminado correctamente;
     *         false si ocurre un error
     */
    public boolean eliminar(String nombreUsuario) {

        String sql = """
                DELETE FROM usuario
                WHERE nombre_usuario = ?
                """;

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, nombreUsuario);

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {

            System.out.println(
                    "Error al eliminar usuario: "
                            + e.getMessage()
            );

            return false;
        }
    }
}