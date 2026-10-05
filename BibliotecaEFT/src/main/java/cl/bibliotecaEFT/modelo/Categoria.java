package cl.bibliotecaEFT.modelo;

/**
 * Representa una categoría de libros de la biblioteca.
 *
 * @author Cristian Contreras
 * @version 1.0
 */
public class Categoria {

    private int id;
    private String nombre;

    public Categoria() {
    }

    public Categoria(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    @Override
    public String toString() {
        return nombre;
    }
}