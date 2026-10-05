package cl.bibliotecaEFT.vista;

import cl.bibliotecaEFT.dao.ReporteDAO;
import cl.bibliotecaEFT.dao.EstudianteDAO;
import cl.bibliotecaEFT.modelo.Estudiante;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Ventana principal de reportes del sistema.
 *
 * @author Cristian Contreras
 * @version 1.0
 */
public class Reportes extends JFrame {

    private JTable tablaReporte;
    private DefaultTableModel modeloTabla;

    private final ReporteDAO reporteDAO;

    public Reportes() {

        reporteDAO = new ReporteDAO();

        inicializarVentana();
        inicializarComponentes();
    }

    private void inicializarVentana() {

        setTitle("BibliotecaEFT - Reportes");
        setSize(650, 400);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLocationRelativeTo(null);
    }

    private void inicializarComponentes() {

        JPanel panelSuperior =
                new JPanel();

        JLabel titulo =
                new JLabel(
                        "Reportes de Biblioteca",
                        SwingConstants.CENTER
                );

        JButton btnLibrosMasPrestados =
                new JButton("Libros más prestados");

        JButton btnHistorialEstudiante =
                new JButton("Historial de estudiante");

        JButton btnPrestamosActuales =
                new JButton(
                        "Libros actualmente en préstamo"
                );

        JButton btnCerrar =
                new JButton("volver");

        panelSuperior.add(btnLibrosMasPrestados);
        panelSuperior.add(btnHistorialEstudiante);
        panelSuperior.add(btnPrestamosActuales);
        panelSuperior.add(btnCerrar);

        modeloTabla =
                new DefaultTableModel(
                        new Object[]{
                                "Libro",
                                "Cantidad de préstamos"
                        },
                        0
                );

        tablaReporte =
                new JTable(modeloTabla);

        JScrollPane scrollPane =
                new JScrollPane(tablaReporte);

        JPanel panelTitulo =
                new JPanel(
                        new BorderLayout()
                );

        panelTitulo.add(
                titulo,
                BorderLayout.CENTER
        );

        JPanel panelNorte =
                new JPanel(
                        new BorderLayout()
                );

        panelNorte.add(
                panelTitulo,
                BorderLayout.NORTH
        );

        panelNorte.add(
                panelSuperior,
                BorderLayout.SOUTH
        );

        add(
                panelNorte,
                BorderLayout.NORTH
        );

        add(
                scrollPane,
                BorderLayout.CENTER
        );

        btnLibrosMasPrestados.addActionListener(
                e -> cargarLibrosMasPrestados()
        );

        btnHistorialEstudiante.addActionListener(
                e -> seleccionarEstudiante()
        );


        btnPrestamosActuales.addActionListener(
                e -> cargarPrestamosActuales()
        );

        btnCerrar.addActionListener(
                e -> volverAlHome()
        );

    }

    private void cargarLibrosMasPrestados() {

        try {

            modeloTabla.setRowCount(0);

            List<String[]> resultados =
                    reporteDAO.librosMasPrestados();

            for (String[] resultado : resultados) {

                modeloTabla.addRow(
                        new Object[]{
                                resultado[0],
                                resultado[1]
                        }
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al cargar el reporte:\n"
                            + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void seleccionarEstudiante() {

        try {

            EstudianteDAO estudianteDAO =
                    new EstudianteDAO();

            List<Estudiante> estudiantes =
                    estudianteDAO.listarTodos();

            if (estudiantes.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "No existen estudiantes registrados.",
                        "Historial",
                        JOptionPane.INFORMATION_MESSAGE
                );

                return;
            }

            JComboBox<Estudiante> comboEstudiantes =
                    new JComboBox<>();

            for (Estudiante estudiante : estudiantes) {
                comboEstudiantes.addItem(estudiante);
            }

            int resultado =
                    JOptionPane.showConfirmDialog(
                            this,
                            comboEstudiantes,
                            "Seleccione un estudiante",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.PLAIN_MESSAGE
                    );

            if (resultado == JOptionPane.OK_OPTION) {

                Estudiante estudianteSeleccionado =
                        (Estudiante)
                                comboEstudiantes.getSelectedItem();

                if (estudianteSeleccionado != null) {

                    cargarHistorialEstudiante(
                            estudianteSeleccionado
                    );
                }
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al seleccionar estudiante:\n"
                            + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void cargarHistorialEstudiante(
            Estudiante estudiante) {

        try {

            modeloTabla.setColumnIdentifiers(
                    new Object[]{
                            "Libro",
                            "Fecha préstamo",
                            "Fecha devolución",
                            "Estado"
                    }
            );

            modeloTabla.setRowCount(0);

            List<String[]> resultados =
                    reporteDAO.historialEstudiante(
                            estudiante.getId()
                    );

            for (String[] resultado : resultados) {

                modeloTabla.addRow(
                        new Object[]{
                                resultado[0],
                                resultado[1],
                                resultado[2],
                                resultado[3]
                        }
                );
            }

            if (resultados.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "El estudiante "
                                + estudiante.getNombre()
                                + " no tiene préstamos registrados.",
                        "Historial",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al cargar el historial:\n"
                            + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    private void cargarPrestamosActuales() {

        try {

            modeloTabla.setColumnIdentifiers(
                    new Object[]{
                            "Estudiante",
                            "Libro",
                            "Fecha préstamo",
                            "Fecha devolución"
                    }
            );

            modeloTabla.setRowCount(0);

            List<String[]> resultados =
                    reporteDAO.librosActualmenteEnPrestamo();

            for (String[] resultado : resultados) {

                modeloTabla.addRow(
                        new Object[]{
                                resultado[0],
                                resultado[1],
                                resultado[2],
                                resultado[3]
                        }
                );
            }

            if (resultados.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "No existen libros actualmente en préstamo.",
                        "Préstamos actuales",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al cargar los préstamos actuales:\n"
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
