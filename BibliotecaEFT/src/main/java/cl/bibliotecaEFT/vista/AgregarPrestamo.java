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
import cl.bibliotecaEFT.tareas.ProcesarPrestamo;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Ventana modal para registrar un nuevo préstamo.
 *
 * @author Cristian Contreras
 * @version 1.0
 */
public class AgregarPrestamo extends JDialog {

    private JComboBox<Estudiante> comboEstudiante;
    private JComboBox<Libro> comboLibro;
    private JTextField txtFechaPrestamo;
    private JTextField txtFechaDevolucion;

    private final EstudianteController estudianteController;
    private final LibroController libroController;
    private final PrestamoController prestamoController;

    public AgregarPrestamo(JFrame parent) {

        super(parent, "Registrar préstamo", true);

        estudianteController =
                new EstudianteController(new EstudianteDAO());

        libroController =
                new LibroController(
                        new LibroDAO(),
                        new CategoriaDAO()
                );

        prestamoController =
                new PrestamoController(new PrestamoDAO());

        inicializarVentana();
        inicializarComponentes();
        cargarDatos();
    }

    private void inicializarVentana() {

        setSize(450, 300);
        setLocationRelativeTo(getParent());
        setResizable(false);
    }

    private void inicializarComponentes() {

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        panel.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        comboEstudiante = new JComboBox<>();
        comboLibro = new JComboBox<>();

        txtFechaPrestamo = new JTextField(
                LocalDate.now().toString()
        );

        txtFechaDevolucion = new JTextField();
        txtFechaDevolucion.setEditable(false);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        panel.add(new JLabel("Estudiante:"));
        panel.add(comboEstudiante);

        panel.add(new JLabel("Libro:"));
        panel.add(comboLibro);

        panel.add(new JLabel("Fecha préstamo:"));
        panel.add(txtFechaPrestamo);

        panel.add(new JLabel("Fecha devolución:"));
        panel.add(txtFechaDevolucion);

        panel.add(btnGuardar);
        panel.add(btnCancelar);

        btnGuardar.addActionListener(e -> registrarPrestamo());
        btnCancelar.addActionListener(e -> cancelar());

        add(panel);
    }

    private void cargarDatos() {

        try {

            List<Estudiante> estudiantes =
                    estudianteController.listarEstudiantes();

            for (Estudiante estudiante : estudiantes) {
                comboEstudiante.addItem(estudiante);
            }

            List<Libro> libros =
                    libroController.listarLibros();

            for (Libro libro : libros) {
                comboLibro.addItem(libro);
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al cargar datos:\n"
                            + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void registrarPrestamo() {

        Estudiante estudiante =
                (Estudiante) comboEstudiante.getSelectedItem();

        Libro libro =
                (Libro) comboLibro.getSelectedItem();

        String fechaPrestamoTexto =
                txtFechaPrestamo.getText().trim();

        String fechaDevolucionTexto =
                txtFechaDevolucion.getText().trim();

        if (estudiante == null || libro == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar un estudiante y un libro.",
                    "Datos incompletos",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (fechaPrestamoTexto.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar la fecha del préstamo.",
                    "Datos incompletos",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {
            LocalDate fechaPrestamo =
                    LocalDate.parse(fechaPrestamoTexto);

            LocalDate fechaDevolucion =
                    fechaPrestamo.plusDays(7);

            txtFechaDevolucion.setText(
                    fechaDevolucion.toString()
            );


            if (libro.getStock() <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "El libro seleccionado no tiene stock disponible.",
                        "Sin stock",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            Prestamo prestamo = new Prestamo(
                    0,
                    estudiante.getId(),
                    libro.getId(),
                    fechaPrestamo,
                    fechaDevolucion,
                    false
            );

            ProcesarPrestamo tarea =
                    new ProcesarPrestamo(
                            prestamo,
                            new PrestamoDAO(),
                            new LibroDAO()
                    );

            Thread hilo = new Thread(tarea);

            hilo.start();

            JOptionPane.showMessageDialog(
                    this,
                    "El préstamo está siendo procesado.",
                    "Procesando",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

        } catch (DateTimeParseException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Las fechas deben tener el formato:\n"
                            + "AAAA-MM-DD",
                    "Fecha inválida",
                    JOptionPane.WARNING_MESSAGE
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al registrar el préstamo:\n"
                            + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    private void cancelar() {

        int respuesta =
                JOptionPane.showConfirmDialog(
                        this,
                        "¿Está seguro de que desea cancelar?\n"
                                + "Los cambios no se guardarán.",
                        "Confirmar cancelación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }

        dispose();
    }
}
