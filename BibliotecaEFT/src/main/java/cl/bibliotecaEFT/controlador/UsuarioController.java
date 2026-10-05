package cl.bibliotecaEFT.controlador;

import cl.bibliotecaEFT.dao.UsuarioDAO;
import cl.bibliotecaEFT.modelo.Usuario;

import java.sql.SQLException;
import java.util.List;

/**
 * Controlador encargado de gestionar las operaciones
 * relacionadas con los usuarios de la biblioteca.
 *
 * @author Cristian Contreras
 * @version 1.0
 */
public class UsuarioController {

    private final UsuarioDAO usuarioDAO;

    /**
     * Constructor del controlador.
     *
     * @param usuarioDAO DAO encargado de acceder a la tabla usuarios.
     */
    public UsuarioController(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    /**
     * Registra un nuevo usuario.
     *
     * @param usuario usuario que se desea registrar.
     * @throws SQLException si ocurre un error con la base de datos.
     */
    public void registrarUsuario(Usuario usuario) throws SQLException {
        usuarioDAO.guardar(usuario);
    }

    /**
     * Obtiene todos los usuarios registrados.
     *
     * @return lista de usuarios.
     * @throws SQLException si ocurre un error con la base de datos.
     */
    public List<Usuario> listarUsuarios() throws SQLException {
        return usuarioDAO.listarTodos();
    }

    /**
     * Busca un usuario utilizando su RUT.
     *
     * @param rut RUT del usuario.
     * @return usuario encontrado o null si no existe.
     * @throws SQLException si ocurre un error con la base de datos.
     */
    public Usuario buscarPorRut(String rut) throws SQLException {
        return usuarioDAO.buscarPorRut(rut);
    }
}
