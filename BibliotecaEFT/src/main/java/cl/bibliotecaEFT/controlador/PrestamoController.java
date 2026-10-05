package cl.bibliotecaEFT.controlador;

import cl.bibliotecaEFT.dao.PrestamoDAO;
import cl.bibliotecaEFT.modelo.Prestamo;

import java.sql.SQLException;
import java.util.List;

/**
 * Controlador encargado de gestionar las operaciones
 * relacionadas con los préstamos de la biblioteca.
 *
 * @author Cristian Contreras
 * @version 1.0
 */
public class PrestamoController {

    private final PrestamoDAO prestamoDAO;

    /**
     * Constructor del controlador.
     *
     * @param prestamoDAO DAO encargado de acceder a la tabla prestamos.
     */
    public PrestamoController(PrestamoDAO prestamoDAO) {
        this.prestamoDAO = prestamoDAO;
    }

    /**
     * Registra un nuevo préstamo.
     *
     * @param prestamo préstamo que se desea registrar.
     * @throws SQLException si ocurre un error con la base de datos.
     */
    public void registrarPrestamo(Prestamo prestamo) throws SQLException {
        prestamoDAO.guardar(prestamo);
    }

    /**
     * Obtiene todos los préstamos registrados.
     *
     * @return lista de préstamos.
     * @throws SQLException si ocurre un error con la base de datos.
     */
    public List<Prestamo> listarPrestamos() throws SQLException {
        return prestamoDAO.listarTodos();
    }

    /**
     * Busca un préstamo utilizando su ID.
     *
     * @param id identificador del préstamo.
     * @return préstamo encontrado o null si no existe.
     * @throws SQLException si ocurre un error con la base de datos.
     */
    public Prestamo buscarPorId(int id) throws SQLException {
        return prestamoDAO.buscarPorId(id);
    }

    /**
     * Actualiza los datos de un préstamo.
     *
     * @param prestamo préstamo con los datos actualizados.
     * @throws SQLException si ocurre un error con la base de datos.
     */
    public void actualizarPrestamo(Prestamo prestamo) throws SQLException {
        prestamoDAO.actualizar(prestamo);
    }

    /**
     * Elimina un préstamo utilizando su ID.
     *
     * @param id identificador del préstamo.
     * @throws SQLException si ocurre un error con la base de datos.
     */
    public void eliminarPrestamo(int id) throws SQLException {
        prestamoDAO.eliminar(id);
    }
}
