package cl.bibliotecaEFT.modelo;

/**
 * Representa un libro registrado en la biblioteca.
 *
 * @author Cristian Contreras Child
 * @version 1.0
 */
public class Libro {

    private int id;
    private String titulo;
    private String autor;
    private String isbn;
    private String editorial;
    private int stock;
    private int idCategoria;

    /**
     * Constructor vacío del libro.
     */
    public Libro() {
    }

    /**
     * Constructor que permite crear un libro con todos sus datos.
     *
     * @param id identificador del libro.
     * @param titulo título del libro.
     * @param autor autor del libro.
     * @param isbn código ISBN del libro.
     * @param editorial editorial del libro.
     * @param stock cantidad disponible del libro.
     * @param idCategoria identificador de la categoría del libro.
     */
    public Libro(int id, String titulo, String autor, String isbn,
                 String editorial, int stock, int idCategoria) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.editorial = editorial;
        this.stock = stock;
        this.idCategoria = idCategoria;
    }

    /**
     * Obtiene el identificador del libro.
     *
     * @return identificador del libro.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador del libro.
     *
     * @param id identificador del libro.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el título del libro.
     *
     * @return título del libro.
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Establece el título del libro.
     *
     * @param titulo título del libro.
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Obtiene el autor del libro.
     *
     * @return autor del libro.
     */
    public String getAutor() {
        return autor;
    }

    /**
     * Establece el autor del libro.
     *
     * @param autor autor del libro.
     */
    public void setAutor(String autor) {
        this.autor = autor;
    }

    /**
     * Obtiene el ISBN del libro.
     *
     * @return ISBN del libro.
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Establece el ISBN del libro.
     *
     * @param isbn ISBN del libro.
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Obtiene la editorial del libro.
     *
     * @return editorial del libro.
     */
    public String getEditorial() {
        return editorial;
    }

    /**
     * Establece la editorial del libro.
     *
     * @param editorial editorial del libro.
     */
    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    /**
     * Obtiene la cantidad disponible del libro.
     *
     * @return cantidad de ejemplares disponibles.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Establece la cantidad disponible del libro.
     *
     * @param stock cantidad de ejemplares disponibles.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Obtiene el identificador de la categoría del libro.
     *
     * @return identificador de la categoría.
     */
    public int getIdCategoria() {
        return idCategoria;
    }

    /**
     * Establece el identificador de la categoría del libro.
     *
     * @param idCategoria identificador de la categoría.
     */
    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    /**
     * Retorna el título del libro como representación textual.
     *
     * @return título del libro.
     */
    @Override
    public String toString() {
        return titulo;
    }
}