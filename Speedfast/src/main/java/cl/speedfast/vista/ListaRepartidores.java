package cl.speedfast.vista;

import cl.speedfast.dao.RepartidorDAO;
import cl.speedfast.model.Repartidor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

/**
 * Ventana utilizada para administrar los repartidores
 * registrados en el sistema SpeedFast.
 *
 * Permite listar, agregar, editar y eliminar repartidores
 * almacenados en la base de datos.
 *
 * @author Cristian Contreras
 * @version 1.0
 */
public class ListaRepartidores extends JFrame {

    private JPanel panel1;
    private JButton btnCerrarAdministradorRepartidores;
    private JButton btnAgregarRepartidor;
    private JButton btnEditarRepartidor;
    private JButton btnEliminarRepartidor;
    private JLabel txtListaRepartidores;
    private JScrollPane jpListaRepartidores;
    private JTable jtListaRepartidores;

    private final RepartidorDAO repartidorDAO;

    private DefaultTableModel model;

    private int repartidorSeleccionado = -1;

    /**
     * Constructor de la ventana de administración
     * de repartidores.
     *
     * @param repartidorDAO objeto encargado de gestionar
     *                      los repartidores en la base de datos
     */
    public ListaRepartidores(RepartidorDAO repartidorDAO) {

        this.repartidorDAO = repartidorDAO;

        setTitle("Administrar Repartidores");
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        inicializarTabla();
        inicializarBotones();
        cargarRepartidores();
    }

    /**
     * Inicializa la tabla utilizada para mostrar
     * los repartidores registrados.
     *
     * Configura las columnas y permite seleccionar
     * un solo repartidor a la vez.
     */
    private void inicializarTabla() {

        String[] columnas = {
                "ID",
                "Nombre"
        };

        model = new DefaultTableModel(columnas, 0) {

            @Override
            public boolean isCellEditable(
                    int row,
                    int column) {
                return false;
            }
        };

        jtListaRepartidores.setModel(model);

        jtListaRepartidores.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        jtListaRepartidores.getSelectionModel()
                .addListSelectionListener(e -> {

                    int fila =
                            jtListaRepartidores.getSelectedRow();

                    if (fila >= 0) {
                        repartidorSeleccionado =
                                jtListaRepartidores
                                        .convertRowIndexToModel(fila);
                    }
                });
    }

    /**
     * Carga todos los repartidores registrados en la base
     * de datos y los muestra en la tabla.
     *
     * Si ocurre un error durante la consulta, se informa
     * al usuario mediante un mensaje.
     */
    public void cargarRepartidores() {

        model.setRowCount(0);

        try {

            List<Repartidor> repartidores =
                    repartidorDAO.listarTodos();

            for (Repartidor repartidor : repartidores) {

                model.addRow(new Object[]{
                        repartidor.getId(),
                        repartidor.getNombre()
                });
            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudieron cargar los repartidores:\n"
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

        btnAgregarRepartidor.addActionListener(
                e -> agregarRepartidor()
        );

        btnEditarRepartidor.addActionListener(
                e -> editarRepartidor()
        );

        btnEliminarRepartidor.addActionListener(
                e -> eliminarRepartidor()
        );

        btnCerrarAdministradorRepartidores.addActionListener(
                e -> cerrarVentana()
        );
    }

    /**
     * Solicita confirmación antes de cerrar la ventana
     * de administración de repartidores.
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
     * Solicita el nombre de un nuevo repartidor
     * y lo registra en la base de datos.
     *
     * Valida que el nombre ingresado no esté vacío.
     */
    private void agregarRepartidor() {

        String nombre = JOptionPane.showInputDialog(
                this,
                "Ingrese el nombre del repartidor:",
                "Agregar repartidor",
                JOptionPane.QUESTION_MESSAGE
        );

        if (nombre == null) {
            return;
        }

        nombre = nombre.trim();

        if (nombre.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "El nombre no puede estar vacío.",
                    "Dato inválido",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            Repartidor repartidor =
                    new Repartidor(0, nombre);

            repartidorDAO.guardar(repartidor);

            cargarRepartidores();

            JOptionPane.showMessageDialog(
                    this,
                    "Repartidor agregado correctamente.",
                    "Agregar repartidor",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo agregar el repartidor:\n"
                            + ex.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Permite editar el nombre del repartidor seleccionado.
     *
     * Valida que exista una selección y que el nuevo nombre
     * no esté vacío antes de actualizar la información
     * mediante RepartidorDAO.
     */
    private void editarRepartidor() {

        if (repartidorSeleccionado == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debes seleccionar un repartidor.",
                    "Repartidor no seleccionado",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            List<Repartidor> repartidores =
                    repartidorDAO.listarTodos();

            if (repartidorSeleccionado >= repartidores.size()) {
                return;
            }

            Repartidor repartidor =
                    repartidores.get(repartidorSeleccionado);

            String nuevoNombre =
                    JOptionPane.showInputDialog(
                            this,
                            "Ingrese el nuevo nombre:",
                            repartidor.getNombre()
                    );

            if (nuevoNombre == null) {
                return;
            }

            nuevoNombre = nuevoNombre.trim();

            if (nuevoNombre.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "El nombre no puede estar vacío.",
                        "Dato inválido",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            repartidor.setNombre(nuevoNombre);

            repartidorDAO.actualizar(repartidor);

            cargarRepartidores();

            repartidorSeleccionado = -1;

            JOptionPane.showMessageDialog(
                    this,
                    "Repartidor actualizado correctamente.",
                    "Editar repartidor",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo actualizar el repartidor:\n"
                            + ex.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Elimina el repartidor seleccionado de la base de datos.
     *
     * Solicita confirmación al usuario antes de realizar
     * la eliminación y posteriormente actualiza la tabla.
     */
    private void eliminarRepartidor() {

        if (repartidorSeleccionado == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debes seleccionar un repartidor.",
                    "Repartidor no seleccionado",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            List<Repartidor> repartidores =
                    repartidorDAO.listarTodos();

            if (repartidorSeleccionado >= repartidores.size()) {
                return;
            }

            Repartidor repartidor =
                    repartidores.get(repartidorSeleccionado);

            int respuesta =
                    JOptionPane.showConfirmDialog(
                            this,
                            "¿Deseas eliminar al repartidor "
                                    + repartidor.getNombre()
                                    + "?",
                            "Confirmar eliminación",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );

            if (respuesta != JOptionPane.YES_OPTION) {
                return;
            }

            repartidorDAO.eliminar(
                    repartidor.getId()
            );

            cargarRepartidores();

            repartidorSeleccionado = -1;

            JOptionPane.showMessageDialog(
                    this,
                    "Repartidor eliminado correctamente.",
                    "Eliminar repartidor",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo eliminar el repartidor:\n"
                            + ex.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}