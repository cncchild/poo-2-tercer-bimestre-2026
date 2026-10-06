package cl.bibliotecaEFT.modelo;

/**
 * Representa una categoría de libros de la biblioteca.
 *
 * @author Cristian Contreras Child
 * @version 1.0
 */
public class Categoria {

    private int id;
    private String nombre;

    /**
     * Constructor vacío de la categoría.
     */
    public Categoria() {
    }

    /**
     * Constructor que permite crear una categoría con sus datos.
     *
     * @param id identificador de la categoría.
     * @param nombre nombre de la categoría.
     */
    public Categoria(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    /**
     * Obtiene el identificador de la categoría.
     *
     * @return identificador de la categoría.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador de la categoría.
     *
     * @param id identificador de la categoría.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre de la categoría.
     *
     * @return nombre de la categoría.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la categoría.
     *
     * @param nombre nombre de la categoría.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Retorna el nombre de la categoría como representación textual.
     *
     * @return nombre de la categoría.
     */
    @Override
    public String toString() {
        return nombre;
    }
}