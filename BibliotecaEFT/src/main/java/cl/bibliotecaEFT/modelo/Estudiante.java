package cl.bibliotecaEFT.modelo;

/**
 * Representa a un estudiante registrado en la biblioteca.
 *
 * @author Cristian Contreras Child
 * @version 1.0
 */
public class Estudiante {

    private int id;
    private String nombre;
    private String rut;
    private String curso;
    private String correo;

    /**
     * Constructor vacío del estudiante.
     */
    public Estudiante() {
    }

    /**
     * Constructor que permite crear un estudiante con sus datos.
     *
     * @param id identificador del estudiante.
     * @param nombre nombre del estudiante.
     * @param rut RUT del estudiante.
     * @param curso curso al que pertenece el estudiante.
     * @param correo correo electrónico del estudiante.
     */
    public Estudiante(int id, String nombre, String rut,
                      String curso, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.rut = rut;
        this.curso = curso;
        this.correo = correo;
    }

    /**
     * Obtiene el identificador del estudiante.
     *
     * @return identificador del estudiante.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador del estudiante.
     *
     * @param id identificador del estudiante.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del estudiante.
     *
     * @return nombre del estudiante.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del estudiante.
     *
     * @param nombre nombre del estudiante.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el RUT del estudiante.
     *
     * @return RUT del estudiante.
     */
    public String getRut() {
        return rut;
    }

    /**
     * Establece el RUT del estudiante.
     *
     * @param rut RUT del estudiante.
     */
    public void setRut(String rut) {
        this.rut = rut;
    }

    /**
     * Obtiene el curso del estudiante.
     *
     * @return curso del estudiante.
     */
    public String getCurso() {
        return curso;
    }

    /**
     * Establece el curso del estudiante.
     *
     * @param curso curso del estudiante.
     */
    public void setCurso(String curso) {
        this.curso = curso;
    }

    /**
     * Obtiene el correo electrónico del estudiante.
     *
     * @return correo electrónico del estudiante.
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Establece el correo electrónico del estudiante.
     *
     * @param correo correo electrónico del estudiante.
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Retorna el nombre del estudiante como representación textual.
     *
     * @return nombre del estudiante.
     */
    @Override
    public String toString() {
        return nombre;
    }
}