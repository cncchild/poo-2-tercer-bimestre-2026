package cl.speedfast.controlador;

import cl.speedfast.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class ControladorUsuarios {
    private List<Usuario> usuarios;

    public ControladorUsuarios() {
        this.usuarios = new ArrayList<>();
        cargarUsuariosPorDefecto();
    }

    private void cargarUsuariosPorDefecto() {
        usuarios.add(new Usuario("admin", "123", "administrador"));
        usuarios.add(new Usuario("usuario", "123", "usuario"));
    }

    public Usuario autenticar(String nombreUsuario, String contrasenia) {
        for (Usuario u : usuarios) {
            if (u.getNombreUsuario().equals(nombreUsuario)
                    && u.getContrasenia().equals(contrasenia)) {
                return u;
            }
        }
        return null;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }
}