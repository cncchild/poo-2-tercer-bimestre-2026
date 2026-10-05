package cl.bibliotecaEFT.vista;

import cl.bibliotecaEFT.controlador.EstudianteController;
import cl.bibliotecaEFT.dao.EstudianteDAO;
import cl.bibliotecaEFT.modelo.Estudiante;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Ventana para gestionar los estudiantes
 * registrados en el sistema de biblioteca.
 *
 * @author Cristian Contreras
 * @version 1.0
 */
public class GestionEstudiantes extends JFrame {

    private JTable tablaEstudiantes;
    private DefaultTableModel modeloTabla;

    private final EstudianteController estudianteController;

    public GestionEstudiantes() {

        EstudianteDAO estudianteDAO = new EstudianteDAO();

        estudianteController =
                new EstudianteController(estudianteDAO);

        inicializarVentana();
        inicializarComponentes();
        cargarEstudiantes();
    }

    private void inicializarVentana() {

        setTitle("BibliotecaEFT - Gestión de estudiantes");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void inicializarComponentes() {

        modeloTabla = new DefaultTableModel(
                new Object[]{
                        "ID",
                        "Nombre",
                        "RUT",
                        "Curso",
                        "Correo"
                },
                0
        );

        tablaEstudiantes =
                new JTable(modeloTabla);

        JScrollPane scrollPane =
                new JScrollPane(tablaEstudiantes);

        JButton btnAgregar =
                new JButton("Agregar");

        JButton btnEditar =
                new JButton("Editar");

        JButton btnEliminar =
                new JButton("Eliminar");

        JButton btnActualizar =
                new JButton("Actualizar");

        JButton btnVolver =
                new JButton("Volver al Home");

        JPanel panelBotones =
                new JPanel();

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnVolver);

        btnAgregar.addActionListener(
                e -> abrirAgregarEstudiante()
        );

        btnEditar.addActionListener(
                e -> editarEstudianteSeleccionado()
        );

        btnEliminar.addActionListener(
                e -> eliminarEstudianteSeleccionado()
        );

        btnActualizar.addActionListener(
                e -> cargarEstudiantes()
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

    private void cargarEstudiantes() {

        try {

            modeloTabla.setRowCount(0);

            List<Estudiante> estudiantes =
                    estudianteController.listarEstudiantes();

            for (Estudiante estudiante :
                    estudiantes) {

                modeloTabla.addRow(
                        new Object[]{
                                estudiante.getId(),
                                estudiante.getNombre(),
                                estudiante.getRut(),
                                estudiante.getCurso(),
                                estudiante.getCorreo()
                        }
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al cargar los estudiantes:\n"
                            + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void abrirAgregarEstudiante() {

        AgregarEstudiante ventana =
                new AgregarEstudiante(this);

        ventana.setVisible(true);

        cargarEstudiantes();
    }

    private void editarEstudianteSeleccionado() {

        int fila =
                tablaEstudiantes.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar un estudiante.",
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

            Estudiante estudiante =
                    estudianteController.buscarPorId(id);

            if (estudiante == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "No se encontró el estudiante.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            EditarEstudiante ventana =
                    new EditarEstudiante(
                            this,
                            estudiante
                    );

            ventana.setVisible(true);

            cargarEstudiantes();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al obtener el estudiante:\n"
                            + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void eliminarEstudianteSeleccionado() {

        int fila =
                tablaEstudiantes.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar un estudiante.",
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

        String nombre =
                String.valueOf(
                        modeloTabla.getValueAt(
                                fila,
                                1
                        )
                );

        int respuesta =
                JOptionPane.showConfirmDialog(
                        this,
                        "¿Está seguro de eliminar al estudiante:\n"
                                + nombre + "?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }

        try {

            estudianteController.eliminarEstudiante(id);

            JOptionPane.showMessageDialog(
                    this,
                    "Estudiante eliminado correctamente.",
                    "Eliminación exitosa",
                    JOptionPane.INFORMATION_MESSAGE
            );

            cargarEstudiantes();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al eliminar el estudiante:\n"
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
}
