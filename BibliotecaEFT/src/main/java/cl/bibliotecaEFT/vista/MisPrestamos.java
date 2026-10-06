package cl.bibliotecaEFT.vista;

import cl.bibliotecaEFT.controlador.EstudianteController;
import cl.bibliotecaEFT.controlador.LibroController;
import cl.bibliotecaEFT.controlador.PrestamoController;
import cl.bibliotecaEFT.dao.CategoriaDAO;
import cl.bibliotecaEFT.dao.EstudianteDAO;
import cl.bibliotecaEFT.dao.LibroDAO;
import cl.bibliotecaEFT.dao.PrestamoDAO;
import cl.bibliotecaEFT.modelo.Estudiante;
import cl.bibliotecaEFT.modelo.Libro;
import cl.bibliotecaEFT.modelo.Prestamo;
import cl.bibliotecaEFT.modelo.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Ventana donde el estudiante puede consultar sus préstamos
 * y registrar la devolución de un libro.
 *
 * @author Cristian Contreras Child
 * @version 1.0
 */
public class MisPrestamos extends JFrame {

    private final Usuario usuario;

    private JTable tablaPrestamos;
    private DefaultTableModel modeloTabla;

    private final PrestamoController prestamoController;
    private final EstudianteController estudianteController;
    private final LibroController libroController;

    /**
     * Constructor de la ventana de préstamos del estudiante.
     * Inicializa el usuario, los controladores, la ventana,
     * sus componentes y carga los préstamos correspondientes.
     *
     * @param usuario usuario que consulta sus préstamos.
     */
    public MisPrestamos(Usuario usuario) {

        this.usuario = usuario;

        prestamoController =
                new PrestamoController(
                        new PrestamoDAO()
                );

        estudianteController =
                new EstudianteController(
                        new EstudianteDAO()
                );

        libroController =
                new LibroController(
                        new LibroDAO(),
                        new CategoriaDAO()
                );

        inicializarVentana();
        inicializarComponentes();
        cargarPrestamos();
    }

    /**
     * Configura las propiedades principales de la ventana,
     * incluyendo título, tamaño, cierre y posición.
     */
    private void inicializarVentana() {

        setTitle(
                "BibliotecaEFT - Mis préstamos"
        );

        setSize(900, 500);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLocationRelativeTo(null);
    }

    /**
     * Inicializa y organiza los componentes gráficos
     * utilizados para consultar y gestionar los préstamos
     * del estudiante.
     */
    private void inicializarComponentes() {

        modeloTabla =
                new DefaultTableModel(
                        new Object[]{
                                "ID",
                                "Libro",
                                "Fecha préstamo",
                                "Fecha devolución",
                                "Estado"
                        },
                        0
                );

        tablaPrestamos =
                new JTable(modeloTabla);

        JScrollPane scrollPane =
                new JScrollPane(tablaPrestamos);

        JButton btnDevolver =
                new JButton("Registrar devolución");

        JButton btnActualizar =
                new JButton("Actualizar");

        JButton btnVolver =
                new JButton("Volver al Home");

        JPanel panelBotones =
                new JPanel();

        panelBotones.add(btnDevolver);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnVolver);

        btnDevolver.addActionListener(
                e -> registrarDevolucion()
        );

        btnActualizar.addActionListener(
                e -> cargarPrestamos()
        );

        btnVolver.addActionListener(
                e -> volverAlHome()
        );

        add(
                scrollPane,
                BorderLayout.CENTER
        );

        add(
                panelBotones,
                BorderLayout.SOUTH
        );
    }

    /**
     * Busca el estudiante asociado al usuario actual,
     * obtiene sus préstamos y actualiza la información
     * mostrada en la tabla.
     */
    private void cargarPrestamos() {

        try {

            Estudiante estudiante =
                    buscarEstudiante();

            if (estudiante == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "No se encontró el estudiante asociado "
                                + "al usuario.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            modeloTabla.setRowCount(0);

            List<Prestamo> prestamos =
                    prestamoController.listarPrestamos();

            for (Prestamo prestamo : prestamos) {

                if (prestamo.getIdEstudiante()
                        != estudiante.getId()) {

                    continue;
                }

                String tituloLibro =
                        obtenerTituloLibro(
                                prestamo.getIdLibro()
                        );

                String estado;

                if (prestamo.isDevuelto()) {

                    estado = "Devuelto";

                } else if (
                        prestamo.getFechaDevolucion() != null
                                && LocalDate.now().isAfter(
                                prestamo.getFechaDevolucion()
                        )
                ) {

                    estado = "Atrasado";

                } else {

                    estado = "Prestado";
                }

                modeloTabla.addRow(
                        new Object[]{
                                prestamo.getId(),
                                tituloLibro,
                                prestamo.getFechaPrestamo(),
                                prestamo.getFechaDevolucion(),
                                estado
                        }
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al cargar los préstamos:\n"
                            + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Busca en la base de datos el estudiante cuyo RUT
     * corresponde al usuario que inició sesión.
     *
     * @return estudiante asociado al usuario o null si no existe.
     * @throws Exception si ocurre un error durante la consulta.
     */
    private Estudiante buscarEstudiante()
            throws Exception {

        List<Estudiante> estudiantes =
                estudianteController.listarEstudiantes();

        for (Estudiante estudiante : estudiantes) {

            if (estudiante.getRut() != null
                    && estudiante.getRut().equals(
                    usuario.getRut()
            )) {

                return estudiante;
            }
        }

        return null;
    }

    /**
     * Busca un libro por su identificador y obtiene su título.
     *
     * @param idLibro identificador del libro.
     * @return título del libro o un mensaje si no existe.
     * @throws Exception si ocurre un error durante la consulta.
     */
    private String obtenerTituloLibro(
            int idLibro) throws Exception {

        Libro libro =
                libroController.buscarPorId(idLibro);

        if (libro == null) {
            return "Libro no encontrado";
        }

        return libro.getTitulo();
    }

    /**
     * Obtiene el préstamo seleccionado, solicita confirmación
     * y registra la devolución del libro. También actualiza
     * el stock correspondiente.
     */
    private void registrarDevolucion() {

        int filaSeleccionada =
                tablaPrestamos.getSelectedRow();

        if (filaSeleccionada == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar un préstamo.",
                    "Préstamo no seleccionado",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int idPrestamo =
                (int) modeloTabla.getValueAt(
                        filaSeleccionada,
                        0
                );

        try {

            Prestamo prestamo =
                    prestamoController.buscarPorId(
                            idPrestamo
                    );

            if (prestamo == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "No se encontró el préstamo.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            if (prestamo.isDevuelto()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Este préstamo ya fue devuelto.",
                        "Devolución",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            int respuesta =
                    JOptionPane.showConfirmDialog(
                            this,
                            "¿Desea registrar la devolución?",
                            "Confirmar devolución",
                            JOptionPane.YES_NO_OPTION
                    );

            if (respuesta != JOptionPane.YES_OPTION) {
                return;
            }

            synchronized (this) {

                prestamo.setDevuelto(true);

                prestamoController.actualizarPrestamo(
                        prestamo
                );

                libroController.aumentarStock(
                        prestamo.getIdLibro()
                );
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Devolución registrada correctamente.\n"
                            + "El stock del libro fue actualizado.",
                    "Devolución",
                    JOptionPane.INFORMATION_MESSAGE
            );

            cargarPrestamos();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al registrar la devolución:\n"
                            + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Solicita confirmación al usuario antes de cerrar
     * la ventana y volver al menú principal.
     */
    private void volverAlHome() {

        int respuesta =
                JOptionPane.showConfirmDialog(
                        this,
                        "¿Desea volver al menú principal?",
                        "Volver al Home",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }

        dispose();
    }
}