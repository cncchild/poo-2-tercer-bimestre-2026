package cl.speedfast.vista;

import cl.speedfast.dao.EntregaDAO;
import cl.speedfast.model.Entrega;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

/**
 * Ventana utilizada para administrar las entregas
 * registradas en el sistema SpeedFast.
 *
 * Permite listar, agregar, editar y eliminar entregas.
 * La creación efectiva de una entrega se realiza desde
 * la gestión de pedidos.
 *
 * @author Cristian Contreras
 * @version 1.0
 */
public class ListaEntrega extends JFrame {

    private JPanel panel1;
    private JLabel txtAdministradorEntregas;
    private JButton btnCerrarAministradorEntregas;
    private JButton btnAgregarEntrega;
    private JButton btnEliminarEntrega;
    private JButton btnEditarEntrega;
    private JLabel txtListaEntregas;
    private JScrollPane jspListaEntregas;
    private JTable jtListaEntregas;

    private final EntregaDAO entregaDAO;

    private DefaultTableModel model;

    private int entregaSeleccionado = -1;

    /**
     * Constructor de la ventana de administración
     * de entregas.
     *
     * @param entregaDAO objeto encargado de gestionar
     *                   las entregas en la base de datos
     */
    public ListaEntrega(EntregaDAO entregaDAO) {

        this.entregaDAO = entregaDAO;

        setTitle("Administrar entregas");
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        inicializarTabla();
        inicializarBotones();
        cargarEntregas();
    }

    /**
     * Inicializa la tabla utilizada para mostrar
     * las entregas registradas.
     *
     * Configura las columnas, selección de filas,
     * ordenamiento y evita la edición directa de las celdas.
     */
    private void inicializarTabla() {

        String[] columnas = {
                "ID",
                "Pedido",
                "Repartidor",
                "Fecha",
                "Hora"
        };

        model = new DefaultTableModel(columnas, 0) {

            @Override
            public boolean isCellEditable(
                    int row,
                    int column) {
                return false;
            }
        };

        jtListaEntregas.setModel(model);
        jtListaEntregas.getTableHeader().setVisible(true);
        jtListaEntregas.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );
        jtListaEntregas.setAutoCreateRowSorter(true);

        jtListaEntregas.getSelectionModel()
                .addListSelectionListener(e -> {

                    int fila =
                            jtListaEntregas.getSelectedRow();

                    if (fila >= 0) {

                        entregaSeleccionado =
                                jtListaEntregas
                                        .convertRowIndexToModel(fila);
                    }
                });
    }

    /**
     * Carga todas las entregas registradas en la base
     * de datos y las muestra en la tabla.
     *
     * Si ocurre un error durante la consulta, se informa
     * al usuario mediante un mensaje.
     *
     * @throws SQLException si ocurre un error al acceder
     *                      a la base de datos
     */
    public void cargarEntregas() {

        model.setRowCount(0);

        try {

            List<Entrega> entregas =
                    entregaDAO.listarTodos();

            for (Entrega entrega : entregas) {

                model.addRow(new Object[]{
                        entrega.getId(),
                        entrega.getIdPedido(),
                        entrega.getIdRepartidor(),
                        entrega.getFecha(),
                        entrega.getHora()
                });
            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudieron cargar las entregas:\n"
                            + ex.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Inicializa los eventos asociados a los botones
     * de la ventana.
     *
     * Configura las acciones para agregar, editar,
     * eliminar y cerrar la ventana.
     */
    private void inicializarBotones() {

        btnAgregarEntrega.addActionListener(
                e -> agregarEntrega()
        );

        btnEditarEntrega.addActionListener(
                e -> editarEntrega()
        );

        btnEliminarEntrega.addActionListener(
                e -> eliminarEntrega()
        );

        btnCerrarAministradorEntregas.addActionListener(
                e -> cerrarVentana()
        );
    }

    /**
     * Solicita confirmación antes de cerrar la ventana
     * de administración de entregas.
     */
    private void cerrarVentana() {

        int respuesta =
                JOptionPane.showConfirmDialog(
                        this,
                        "¿Deseas volver al Home?",
                        "Cerrar ventana",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

        if (respuesta == JOptionPane.YES_OPTION) {
            dispose();
        }
    }

    /**
     * Informa al usuario que la creación de entregas
     * se realiza desde la gestión de pedidos.
     *
     * Este método no registra directamente una entrega.
     */
    private void agregarEntrega() {

        JOptionPane.showMessageDialog(
                this,
                "La creación de entregas se realiza desde el Home, administrar Pedidos.",
                "Agregar entrega",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * Permite editar la fecha y hora de una entrega
     * seleccionada.
     *
     * Valida los datos ingresados y actualiza la información
     * mediante EntregaDAO.
     */
    private void editarEntrega() {

        if (entregaSeleccionado == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debes seleccionar una entrega.",
                    "Entrega no seleccionada",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            List<Entrega> entregas =
                    entregaDAO.listarTodos();

            if (entregaSeleccionado >= entregas.size()) {
                return;
            }

            Entrega entrega =
                    entregas.get(entregaSeleccionado);

            String nuevaFecha =
                    JOptionPane.showInputDialog(
                            this,
                            "Ingrese la nueva fecha (AAAA-MM-DD):",
                            entrega.getFecha().toString()
                    );

            if (nuevaFecha == null) {
                return;
            }

            nuevaFecha = nuevaFecha.trim();

            if (nuevaFecha.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "La fecha no puede estar vacía.",
                        "Dato inválido",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            String nuevaHora =
                    JOptionPane.showInputDialog(
                            this,
                            "Ingrese la nueva hora (HH:MM:SS):",
                            entrega.getHora().toString()
                    );

            if (nuevaHora == null) {
                return;
            }

            nuevaHora = nuevaHora.trim();

            if (nuevaHora.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "La hora no puede estar vacía.",
                        "Dato inválido",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            entrega.setFecha(
                    java.time.LocalDate.parse(nuevaFecha)
            );

            entrega.setHora(
                    java.time.LocalTime.parse(nuevaHora)
            );

            entregaDAO.actualizar(entrega);

            cargarEntregas();

            entregaSeleccionado = -1;

            JOptionPane.showMessageDialog(
                    this,
                    "Entrega actualizada correctamente.",
                    "Editar entrega",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (java.time.format.DateTimeParseException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "La fecha o la hora no tienen un formato válido.",
                    "Dato inválido",
                    JOptionPane.WARNING_MESSAGE
            );

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo actualizar la entrega:\n"
                            + ex.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Elimina la entrega seleccionada de la base de datos.
     *
     * Solicita confirmación al usuario antes de realizar
     * la eliminación y posteriormente actualiza la tabla.
     */
    private void eliminarEntrega() {

        if (entregaSeleccionado == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debes seleccionar una entrega.",
                    "Entrega no seleccionada",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            List<Entrega> entregas =
                    entregaDAO.listarTodos();

            if (entregaSeleccionado >= entregas.size()) {
                return;
            }

            Entrega entrega =
                    entregas.get(entregaSeleccionado);

            int respuesta =
                    JOptionPane.showConfirmDialog(
                            this,
                            "¿Deseas eliminar la entrega "
                                    + entrega.getId()
                                    + "?",
                            "Confirmar eliminación",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );

            if (respuesta != JOptionPane.YES_OPTION) {
                return;
            }

            entregaDAO.eliminar(
                    entrega.getId()
            );

            cargarEntregas();

            entregaSeleccionado = -1;

            JOptionPane.showMessageDialog(
                    this,
                    "Entrega eliminada correctamente.",
                    "Eliminar entrega",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo eliminar la entrega:\n"
                            + ex.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}