package cl.speedfast.controlador;

import cl.speedfast.dao.UsuarioDAO;
import cl.speedfast.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class ControladorUsuarios {

    private final List<Usuario> usuarios;
    private final UsuarioDAO usuarioDAO;

    public ControladorUsuarios() {

        this.usuarios = new ArrayList<>();
        this.usuarioDAO = new UsuarioDAO();

        cargarUsuariosPorDefecto();
    }

    private void cargarUsuariosPorDefecto() {

        usuarios.add(
                new Usuario(
                        "admin",
                        "123",
                        "administrador"
                )
        );

        usuarios.add(
                new Usuario(
                        "usuario",
                        "123",
                        "usuario"
                )
        );
    }

    public Usuario autenticar(
            String nombreUsuario,
            String contrasenia) {

        return usuarioDAO.autenticar(
                nombreUsuario,
                contrasenia
        );
    }

    public boolean registrarUsuario(
            String nombreUsuario,
            String contrasenia,
            String rol) {

        // Verificar que no exista en la lista
        for (Usuario u : usuarios) {

            if (u.getNombreUsuario()
                    .equalsIgnoreCase(nombreUsuario)) {

                return false;
            }
        }

        Usuario nuevoUsuario =
                new Usuario(
                        nombreUsuario,
                        contrasenia,
                        rol
                );

        // Guardar en MySQL
        boolean guardado =
                usuarioDAO.guardar(nuevoUsuario);

        if (guardado) {

            // Mantener también la lista actualizada
            usuarios.add(nuevoUsuario);

            return true;
        }

        return false;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }
}