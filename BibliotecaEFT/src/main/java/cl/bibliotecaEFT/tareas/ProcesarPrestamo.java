package cl.bibliotecaEFT.tareas;

import cl.bibliotecaEFT.dao.LibroDAO;
import cl.bibliotecaEFT.dao.PrestamoDAO;
import cl.bibliotecaEFT.modelo.Libro;
import cl.bibliotecaEFT.modelo.Prestamo;

/**
 * Tarea encargada de procesar el registro de un préstamo
 * en segundo plano.
 *
 * La operación se ejecuta mediante un hilo y utiliza
 * sincronización para evitar que dos préstamos modifiquen
 * el stock del mismo libro al mismo tiempo.
 *
 * @author Cristian Contreras Child
 * @version 1.0
 */
public class ProcesarPrestamo implements Runnable {

    private final Prestamo prestamo;
    private final PrestamoDAO prestamoDAO;
    private final LibroDAO libroDAO;

    /**
     * Objeto utilizado como mecanismo de sincronización.
     */
    private static final Object LOCK = new Object();

    /**
     * Constructor de la tarea.
     *
     * @param prestamo préstamo que se desea registrar.
     * @param prestamoDAO DAO encargado de gestionar los préstamos.
     * @param libroDAO DAO encargado de gestionar los libros.
     */
    public ProcesarPrestamo(
            Prestamo prestamo,
            PrestamoDAO prestamoDAO,
            LibroDAO libroDAO) {

        this.prestamo = prestamo;
        this.prestamoDAO = prestamoDAO;
        this.libroDAO = libroDAO;
    }

    /**
     * Ejecuta el proceso de registro del préstamo.
     * <p>
     * Verifica la existencia y disponibilidad del libro,
     * disminuye su stock y registra el préstamo en la base
     * de datos. El acceso al proceso se sincroniza mediante
     * {@code synchronized} para evitar modificaciones
     * simultáneas del stock.
     */
    @Override
    public void run() {

        synchronized (LOCK) {

            try {

                Libro libro = libroDAO.buscarPorId(
                        prestamo.getIdLibro()
                );

                if (libro == null) {
                    System.out.println(
                            "No se encontró el libro seleccionado."
                    );
                    return;
                }

                if (libro.getStock() <= 0) {
                    System.out.println(
                            "No hay stock disponible para el libro."
                    );
                    return;
                }

                boolean stockActualizado =
                        libroDAO.disminuirStock(
                                prestamo.getIdLibro()
                        );

                if (!stockActualizado) {

                    System.out.println(
                            "No fue posible registrar el préstamo: "
                                    + "no hay stock disponible."
                    );

                    return;
                }

                // Registra el préstamo en la base de datos.
                prestamoDAO.guardar(prestamo);

                System.out.println(
                        "Préstamo registrado correctamente."
                );

            } catch (Exception e) {

                System.out.println(
                        "Error al procesar el préstamo: "
                                + e.getMessage()
                );
            }
        }
    }
}