package cl.bibliotecaEFT.modelo;

/**
 * Representa un usuario del sistema de gestión de biblioteca.
 *
 * @author Cristian Contreras Child
 * @version 1.0
 */
public class Usuario {

    private int id;
    private String nombre;
    private String rut;
    private String correo;
    private String contraseña;
    private String rol;

    /**
     * Constructor vacío del usuario.
     */
    public Usuario() {
    }

    /**
     * Constructor que permite crear un usuario con todos sus datos.
     *
     * @param id identificador del usuario.
     * @param nombre nombre del usuario.
     * @param rut RUT del usuario.
     * @param correo correo electrónico del usuario.
     * @param contraseña contraseña utilizada para iniciar sesión.
     * @param rol rol asignado al usuario dentro del sistema.
     */
    public Usuario(int id, String nombre, String rut, String correo,
                   String contraseña, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.rut = rut;
        this.correo = correo;
        this.contraseña = contraseña;
        this.rol = rol;
    }

    /**
     * Obtiene el identificador del usuario.
     *
     * @return identificador del usuario.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador del usuario.
     *
     * @param id identificador del usuario.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del usuario.
     *
     * @return nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del usuario.
     *
     * @param nombre nombre del usuario.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el RUT del usuario.
     *
     * @return RUT del usuario.
     */
    public String getRut() {
        return rut;
    }

    /**
     * Establece el RUT del usuario.
     *
     * @param rut RUT del usuario.
     */
    public void setRut(String rut) {
        this.rut = rut;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     *
     * @return correo electrónico del usuario.
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Establece el correo electrónico del usuario.
     *
     * @param correo correo electrónico del usuario.
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return contraseña del usuario.
     */
    public String getContraseña() {
        return contraseña;
    }

    /**
     * Establece la contraseña del usuario.
     *
     * @param contraseña contraseña del usuario.
     */
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    /**
     * Obtiene el rol del usuario.
     *
     * @return rol asignado al usuario.
     */
    public String getRol() {
        return rol;
    }

    /**
     * Establece el rol del usuario.
     *
     * @param rol rol asignado al usuario.
     */
    public void setRol(String rol) {
        this.rol = rol;
    }
}