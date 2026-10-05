package cl.bibliotecaEFT.vista;

import cl.bibliotecaEFT.controlador.LibroController;
import cl.bibliotecaEFT.dao.CategoriaDAO;
import cl.bibliotecaEFT.dao.LibroDAO;
import cl.bibliotecaEFT.modelo.Libro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Ventana para gestionar los libros registrados
 * en el sistema de biblioteca.
 *
 * @author Cristian Contreras
 * @version 1.0
 */
public class GestionLibros extends JFrame {

    private JTable tablaLibros;
    private DefaultTableModel modeloTabla;

    private final LibroController libroController;

    /**
     * Constructor de la ventana de gestión de libros.
     */
    public GestionLibros() {

        LibroDAO libroDAO = new LibroDAO();
        CategoriaDAO categoriaDAO = new CategoriaDAO();

        libroController = new LibroController(
                libroDAO,
                categoriaDAO
        );

        inicializarVentana();
        inicializarComponentes();
        cargarLibros();
    }

    /**
     * Configura la ventana.
     */
    private void inicializarVentana() {

        setTitle("BibliotecaEFT - Gestión de libros");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    /**
     * Crea los componentes gráficos.
     */
    private void inicializarComponentes() {

        modeloTabla = new DefaultTableModel(
                new Object[]{
                        "ID",
                        "Titulo",
                        "Autor",
                        "ISBN",
                        "Editorial",
                        "Stock",
                        "Categoría"
                },
                0
        );

        tablaLibros = new JTable(modeloTabla);

        JScrollPane scrollPane =
                new JScrollPane(tablaLibros);

        JButton btnAgregar =
                new JButton("Agregar");

        JButton btnEditar =
                new JButton("Editar");

        JButton btnActualizar =
                new JButton("Actualizar");

        JButton btnEliminar =
                new JButton("Eliminar");

        JButton btnVolver =
                new JButton("Volver al Home");

        btnAgregar.addActionListener(
                e -> abrirAgregarLibro()
        );

        btnEditar.addActionListener(
                e -> editarLibroSeleccionado()
        );

        btnActualizar.addActionListener(
                e -> cargarLibros()
        );

        btnEliminar.addActionListener(
                e -> eliminarLibroSeleccionado()
        );

        btnVolver.addActionListener(
                e -> volverAlHome()
        );

        JPanel panelBotones = new JPanel();

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnVolver);

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
     * Carga los libros desde la base de datos.
     */
    private void cargarLibros() {

        try {

            modeloTabla.setRowCount(0);

            List<Libro> libros =
                    libroController.listarLibros();

            for (Libro libro : libros) {

                modeloTabla.addRow(
                        new Object[]{
                                libro.getId(),
                                libro.getTitulo(),
                                libro.getAutor(),
                                libro.getIsbn(),
                                libro.getEditorial(),
                                libro.getStock(),
                                libro.getIdCategoria()
                        }
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al cargar los libros:\n"
                            + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Abre la ventana para registrar un nuevo libro.
     */
    private void abrirAgregarLibro() {

        AgregarLibro ventana =
                new AgregarLibro(this);

        ventana.setVisible(true);

        cargarLibros();
    }

    /**
     * Abre la ventana de edición del libro seleccionado.
     */
    private void editarLibroSeleccionado() {

        int fila =
                tablaLibros.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar un libro.",
                    "Selección requerida",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int id =
                (int) modeloTabla.getValueAt(
                        fila,
                        0
                );

        try {

            Libro libro =
                    libroController.buscarPorId(id);

            if (libro == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "No se encontró el libro.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            EditarLibro ventana =
                    new EditarLibro(
                            this,
                            libro
                    );

            ventana.setVisible(true);

            cargarLibros();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al obtener el libro:\n"
                            + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Elimina el libro seleccionado de la base de datos.
     */
    private void eliminarLibroSeleccionado() {

        int fila =
                tablaLibros.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar un libro.",
                    "Selección requerida",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int id =
                (int) modeloTabla.getValueAt(
                        fila,
                        0
                );

        String titulo =
                String.valueOf(
                        modeloTabla.getValueAt(
                                fila,
                                1
                        )
                );

        int respuesta =
                JOptionPane.showConfirmDialog(
                        this,
                        "¿Está seguro de eliminar el libro:\n"
                                + titulo + "?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }

        try {

            libroController.eliminarLibro(id);

            JOptionPane.showMessageDialog(
                    this,
                    "Libro eliminado correctamente.",
                    "Eliminación exitosa",
                    JOptionPane.INFORMATION_MESSAGE
            );

            cargarLibros();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al eliminar el libro:\n"
                            + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Solicita confirmación antes de volver al menú principal.
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
