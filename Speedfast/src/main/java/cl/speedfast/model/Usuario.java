package cl.speedfast.model;

/**
 * Representa un usuario del sistema SpeedFast.
 *
 * Contiene las credenciales básicas y el rol asignado
 * para controlar el acceso a las funcionalidades
 * de la aplicación.
 *
 * @author Cristian Contreras
 * @version 1.0
 */
public class Usuario {

    private String nombreUsuario;
    private String contrasenia;
    private String rol;

    /**
     * Constructor de la clase Usuario.
     *
     * El rol se convierte a minúsculas para mantener
     * un formato estándar dentro de la aplicación.
     *
     * @param nombreUsuario nombre de usuario
     * @param contrasenia contraseña del usuario
     * @param rol rol asignado al usuario
     */
    public Usuario(
            String nombreUsuario,
            String contrasenia,
            String rol) {

        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.rol = rol.toLowerCase();
    }

    /**
     * Obtiene el nombre de usuario.
     *
     * @return nombre de usuario
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return contraseña del usuario
     */
    public String getContrasenia() {
        return contrasenia;
    }

    /**
     * Obtiene el rol del usuario.
     *
     * @return rol asignado al usuario
     */
    public String getRol() {
        return rol;
    }

    /**
     * Obtiene una representación textual del usuario.
     *
     * @return nombre de usuario y rol
     */
    @Override
    public String toString() {
        return nombreUsuario + " (" + rol + ")";
    }
}