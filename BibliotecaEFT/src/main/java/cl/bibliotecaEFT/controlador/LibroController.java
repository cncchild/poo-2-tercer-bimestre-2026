package cl.bibliotecaEFT.controlador;

import cl.bibliotecaEFT.dao.CategoriaDAO;
import cl.bibliotecaEFT.dao.LibroDAO;
import cl.bibliotecaEFT.modelo.Categoria;
import cl.bibliotecaEFT.modelo.Libro;

import java.sql.SQLException;
import java.util.List;

/**
 * Controlador encargado de gestionar las operaciones
 * relacionadas con los libros de la biblioteca.
 *
 * @author Cristian Contreras
 * @version 1.0
 */
public class LibroController {

    private final LibroDAO libroDAO;
    private final CategoriaDAO categoriaDAO;

    /**
     * Constructor del controlador.
     *
     * @param libroDAO DAO encargado de acceder a la tabla libros.
     * @param categoriaDAO DAO encargado de acceder a la tabla categorias.
     */
    public LibroController(LibroDAO libroDAO, CategoriaDAO categoriaDAO) {
        this.libroDAO = libroDAO;
        this.categoriaDAO = categoriaDAO;
    }

    /**
     * Registra un nuevo libro.
     *
     * @param libro libro que se desea registrar.
     * @throws SQLException si ocurre un error con la base de datos.
     */
    public void registrarLibro(Libro libro) throws SQLException {
        libroDAO.guardar(libro);
    }

    /**
     * Obtiene todos los libros registrados.
     *
     * @return lista de libros.
     * @throws SQLException si ocurre un error con la base de datos.
     */
    public List<Libro> listarLibros() throws SQLException {
        return libroDAO.listarTodos();
    }

    /**
     * Busca un libro utilizando su ID.
     *
     * @param id identificador del libro.
     * @return libro encontrado o null si no existe.
     * @throws SQLException si ocurre un error con la base de datos.
     */
    public Libro buscarPorId(int id) throws SQLException {
        return libroDAO.buscarPorId(id);
    }

    /**
     * Actualiza los datos de un libro.
     *
     * @param libro libro con los datos actualizados.
     * @throws SQLException si ocurre un error con la base de datos.
     */
    public void actualizarLibro(Libro libro) throws SQLException {
        libroDAO.actualizar(libro);
    }

    /**
     * Elimina un libro utilizando su ID.
     *
     * @param id identificador del libro.
     * @throws SQLException si ocurre un error con la base de datos.
     */
    public void eliminarLibro(int id) throws SQLException {
        libroDAO.eliminar(id);
    }

    /**
     * Obtiene todas las categorías disponibles.
     *
     * @return lista de categorías.
     * @throws SQLException si ocurre un error con la base de datos.
     */
    public List<Categoria> listarCategorias() throws SQLException {
        return categoriaDAO.listarTodos();
    }
    public boolean disminuirStock(int idLibro)
            throws SQLException {

        return libroDAO.disminuirStock(idLibro);
    }


    public void aumentarStock(int idLibro)
            throws SQLException {

        libroDAO.aumentarStock(idLibro);
    }

}
