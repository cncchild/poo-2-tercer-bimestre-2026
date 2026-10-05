package cl.bibliotecaEFT.controlador;

import cl.bibliotecaEFT.dao.EstudianteDAO;
import cl.bibliotecaEFT.modelo.Estudiante;

import java.sql.SQLException;
import java.util.List;

/**
 * Controlador encargado de gestionar las operaciones
 * relacionadas con los estudiantes de la biblioteca.
 *
 * @author Cristian Contreras
 * @version 1.0
 */
public class EstudianteController {

    private final EstudianteDAO estudianteDAO;

    /**
     * Constructor del controlador.
     *
     * @param estudianteDAO DAO encargado de acceder a la tabla estudiantes.
     */
    public EstudianteController(EstudianteDAO estudianteDAO) {
        this.estudianteDAO = estudianteDAO;
    }

    /**
     * Registra un nuevo estudiante.
     *
     * @param estudiante estudiante que se desea registrar.
     * @throws SQLException si ocurre un error con la base de datos.
     */
    public void registrarEstudiante(Estudiante estudiante) throws SQLException {
        estudianteDAO.guardar(estudiante);
    }

    /**
     * Obtiene todos los estudiantes registrados.
     *
     * @return lista de estudiantes.
     * @throws SQLException si ocurre un error con la base de datos.
     */
    public List<Estudiante> listarEstudiantes() throws SQLException {
        return estudianteDAO.listarTodos();
    }

    /**
     * Busca un estudiante utilizando su ID.
     *
     * @param id identificador del estudiante.
     * @return estudiante encontrado o null si no existe.
     * @throws SQLException si ocurre un error con la base de datos.
     */
    public Estudiante buscarPorId(int id) throws SQLException {
        return estudianteDAO.buscarPorId(id);
    }

    /**
     * Actualiza los datos de un estudiante.
     *
     * @param estudiante estudiante que se desea actualizar.
     * @throws SQLException si ocurre un error con la base de datos.
     */
    public void actualizarEstudiante(Estudiante estudiante)
            throws SQLException {

        estudianteDAO.actualizar(estudiante);
    }
    
    /**
     * Elimina un estudiante por su ID.
     *
     * @param id identificador del estudiante.
     * @throws SQLException si ocurre un error con la base de datos.
     */
    public void eliminarEstudiante(int id)
            throws SQLException {

        estudianteDAO.eliminar(id);
    }

}
