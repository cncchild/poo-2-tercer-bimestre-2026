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

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Ventana para gestionar los préstamos de la biblioteca.
 *
 * @author Cristian Contreras
 * @version 1.0
 */
public class GestionPrestamos extends JFrame {

    private JTable tablaPrestamos;
    private DefaultTableModel modeloTabla;

    private final PrestamoController prestamoController;
    private final EstudianteController estudianteController;
    private final LibroController libroController;

    public GestionPrestamos() {

        PrestamoDAO prestamoDAO =
                new PrestamoDAO();

        EstudianteDAO estudianteDAO =
                new EstudianteDAO();

        LibroDAO libroDAO =
                new LibroDAO();

        CategoriaDAO categoriaDAO =
                new CategoriaDAO();

        prestamoController =
                new PrestamoController(
                        prestamoDAO
                );

        estudianteController =
                new EstudianteController(
                        estudianteDAO
                );

        libroController =
                new LibroController(
                        libroDAO,
                        categoriaDAO
                );

        inicializarVentana();
        inicializarComponentes();
        cargarPrestamos();
    }

    private void inicializarVentana() {

        setTitle(
                "BibliotecaEFT - Gestión de préstamos"
        );

        setSize(950, 500);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLocationRelativeTo(null);
    }

    private void inicializarComponentes() {

        modeloTabla =
                new DefaultTableModel(
                        new Object[]{
                                "ID",
                                "Estudiante",
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

        JButton btnAgregar =
                new JButton("Nuevo préstamo");

        JButton btnDevolver =
                new JButton("Registrar devolución");

        JButton btnActualizar =
                new JButton("Actualizar");

        JButton btnEliminar =
                new JButton("Eliminar");

        JButton btnVolver =
                new JButton("Volver al Home");

        JPanel panelBotones =
                new JPanel();

        panelBotones.add(btnAgregar);
        panelBotones.add(btnDevolver);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnVolver);

        btnAgregar.addActionListener(
                e -> abrirAgregarPrestamo()
        );

        btnDevolver.addActionListener(
                e -> registrarDevolucion()
        );

        btnActualizar.addActionListener(
                e -> cargarPrestamos()
        );

        btnEliminar.addActionListener(
                e -> eliminarPrestamo()
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

    private void cargarPrestamos() {

        try {

            modeloTabla.setRowCount(0);

            List<Prestamo> prestamos =
                    prestamoController.listarPrestamos();

            for (Prestamo prestamo : prestamos) {

                String nombreEstudiante =
                        obtenerNombreEstudiante(
                                prestamo.getIdEstudiante()
                        );

                String tituloLibro =
                        obtenerTituloLibro(
                                prestamo.getIdLibro()
                        );

                String estado =
                        prestamo.isDevuelto()
                                ? "Devuelto"
                                : obtenerEstadoPrestamo(
                                prestamo
                        );

                modeloTabla.addRow(
                        new Object[]{
                                prestamo.getId(),
                                nombreEstudiante,
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

    private String obtenerNombreEstudiante(
            int idEstudiante) throws Exception {

        Estudiante estudiante =
                estudianteController.buscarPorId(
                        idEstudiante
                );

        if (estudiante == null) {
            return "Estudiante no encontrado";
        }

        return estudiante.getNombre();
    }

    private String obtenerTituloLibro(
            int idLibro) throws Exception {

        Libro libro =
                libroController.buscarPorId(
                        idLibro
                );

        if (libro == null) {
            return "Libro no encontrado";
        }

        return libro.getTitulo();
    }

    private String obtenerEstadoPrestamo(
            Prestamo prestamo) {

        LocalDate fechaActual =
                LocalDate.now();

        if (prestamo.getFechaDevolucion() != null
                && fechaActual.isAfter(
                prestamo.getFechaDevolucion()
        )) {

            return "Atrasado";
        }

        return "Prestado";
    }

    private void abrirAgregarPrestamo() {

        AgregarPrestamo ventana =
                new AgregarPrestamo(this);

        ventana.setVisible(true);

        cargarPrestamos();
    }

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
    private void eliminarPrestamo() {

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

            int respuesta =
                    JOptionPane.showConfirmDialog(
                            this,
                            "¿Está seguro de que desea eliminar este préstamo?\n"
                                    + "Esta acción no se puede deshacer.",
                            "Confirmar eliminación",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );

            if (respuesta != JOptionPane.YES_OPTION) {
                return;
            }

            synchronized (this) {

                if (!prestamo.isDevuelto()) {

                    libroController.aumentarStock(
                            prestamo.getIdLibro()
                    );
                }

                prestamoController.eliminarPrestamo(
                        idPrestamo
                );
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Préstamo eliminado correctamente.",
                    "Eliminación exitosa",
                    JOptionPane.INFORMATION_MESSAGE
            );

            cargarPrestamos();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al eliminar el préstamo:\n"
                            + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

}
