package cl.speedfast.dao;

import cl.speedfast.conexion.ConexionBD;
import cl.speedfast.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

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


    // =========================================================
    // LISTAR USUARIOS
    // =========================================================

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


    // =========================================================
    // ACTUALIZAR USUARIO
    // =========================================================

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


    // =========================================================
    // ELIMINAR USUARIO
    // =========================================================

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