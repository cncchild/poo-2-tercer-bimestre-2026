package cl.speedfast.vista;

import cl.speedfast.dao.EntregaDAO;
import cl.speedfast.dao.PedidoDAO;
import cl.speedfast.dao.RepartidorDAO;
import cl.speedfast.model.Entrega;
import cl.speedfast.model.EstadoPedido;
import cl.speedfast.model.Pedido;
import cl.speedfast.model.Repartidor;
import javax.swing.table.JTableHeader;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

public class ListaPedidos extends JFrame {

    private JPanel formListaProductos;
    private JLabel lblTitulo;
    private JLabel lblListarPedidos;
    private JTable jtListarPedido;
    private JButton btnAgregar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnLogOut;
    private JPanel jpBtnCrud;
    private JPanel jpLogout;
    private JButton btnIniciarEntrega;
    private JPanel jpEntrega;
    private JButton btnEliminarPedidoBD;
    private JScrollPane jpListaPedidos;

    private DefaultTableModel model;

    private final PedidoDAO pedidoDAO;
    private final RepartidorDAO repartidorDAO;
    private final EntregaDAO entregaDAO;

    private int pedidoSeleccionado = -1;

    private final String rol;
    private final Home home;

    public ListaPedidos(
            Home home,
            String rol,
            PedidoDAO pedidoDAO,
            RepartidorDAO repartidorDAO,
            EntregaDAO entregaDAO) {

        this.home = home;
        this.rol = rol;
        this.pedidoDAO = pedidoDAO;
        this.repartidorDAO = repartidorDAO;
        this.entregaDAO = entregaDAO;

        setTitle("Pedidos Flash - Rol: " + rol);
        setContentPane(formListaProductos);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 450);
        setLocationRelativeTo(null);
        setResizable(false);

        inicializarTabla();
        inicializarBotones();
        cargarPedidos();
    }


    // =========================================================
    // TABLA
    // =========================================================
    private void inicializarTabla() {

        String[] columnas = {
                "ID",
                "Dirección",
                "Tipo",
                "Distancia",
                "Tiempo",
                "Repartidor",
                "Estado"
        };

        model = new DefaultTableModel(columnas, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        jtListarPedido.setModel(model);
        JTableHeader encabezado = jtListarPedido.getTableHeader();

        encabezado.setVisible(true);
        encabezado.setOpaque(true);

        jtListarPedido.setTableHeader(encabezado);
        // Mostrar encabezado de la tabla
        jtListarPedido.getTableHeader().setVisible(true);

        // Permitir ordenar haciendo clic en los encabezados
        jtListarPedido.setAutoCreateRowSorter(true);

        // Ajustar automáticamente las columnas
        jtListarPedido.setAutoResizeMode(
                JTable.AUTO_RESIZE_ALL_COLUMNS
        );

        // Seleccionar una sola fila
        jtListarPedido.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        // Seleccionar pedido
        jtListarPedido.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                int fila =
                        jtListarPedido.getSelectedRow();

                if (fila >= 0) {

                    pedidoSeleccionado =
                            jtListarPedido.convertRowIndexToModel(fila);
                }
            }
        });
    }

    // =========================================================
    // CARGAR PEDIDOS
    // =========================================================

    public void cargarPedidos() {

        model.setRowCount(0);

        try {

            for (Pedido pedido : pedidoDAO.listarTodos()) {

                model.addRow(new Object[]{
                        pedido.getIdPedido(),
                        pedido.getDireccionEntrega(),
                        pedido.obtenerTipoPedido(),
                        pedido.getDistanciaKm() + " km",
                        pedido.calcularTiempoEntrega() + " min",
                        pedido.getRepartidor(),
                        pedido.getEstado()
                });
            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudieron cargar los pedidos:\n"
                            + ex.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================================================
    // BOTONES
    // =========================================================

    private void inicializarBotones() {

        // Agregar pedido
        btnAgregar.addActionListener(
                e -> abrirRegistroPedido()
        );

        // Editar pedido
        btnEditar.addActionListener(
                e -> editarPedido()
        );

        // Eliminar / cancelar pedido
        btnEliminar.addActionListener(
                e -> eliminarPedido()
        );

        btnEliminarPedidoBD.addActionListener(
                e -> btnEliminarPedidoBD()
        );
        // Cerrar sesión
        btnLogOut.addActionListener(
                e -> cerrarSesion()
        );

        // Iniciar entrega
        btnIniciarEntrega.addActionListener(
                e -> iniciarEntrega()
        );


    }


    // =========================================================
    // AGREGAR PEDIDO
    // =========================================================

    private void abrirRegistroPedido() {

        RegistroPedidos registroPedidos =
                new RegistroPedidos(
                        this,
                        pedidoDAO,
                        repartidorDAO,
                        entregaDAO
                );

        registroPedidos.setVisible(true);
    }


    // =========================================================
    // EDITAR PEDIDO
    // =========================================================

    private void editarPedido() {

        if (pedidoSeleccionado == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debes seleccionar un pedido de la tabla",
                    "Pedido no seleccionado",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            List<Pedido> pedidos =
                    pedidoDAO.listarTodos();

            if (pedidoSeleccionado >= pedidos.size()) {

                JOptionPane.showMessageDialog(
                        this,
                        "No se pudo encontrar el pedido seleccionado.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            Pedido pedido =
                    pedidos.get(pedidoSeleccionado);

            EditarRegistroPedido editar =
                    new EditarRegistroPedido(
                            this,
                            pedidoDAO,
                            pedido
                    );

            editar.setVisible(true);

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo cargar el pedido:\n"
                            + ex.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // ELIMINAR PEDIDO
    // =========================================================

    private void eliminarPedido() {

        if (pedidoSeleccionado == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debes seleccionar un pedido de la tabla",
                    "Pedido no seleccionado",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            List<Pedido> pedidos =
                    pedidoDAO.listarTodos();

            Pedido pedido =
                    pedidos.get(pedidoSeleccionado);

            int respuesta =
                    JOptionPane.showConfirmDialog(
                            this,
                            "¿Deseas cancelar el pedido #"
                                    + pedido.getIdPedido()
                                    + "?",
                            "Confirmar cancelación",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );

            if (respuesta != JOptionPane.YES_OPTION) {
                return;
            }

            pedidoDAO.actualizarEstado(
                    pedido.getIdPedido(),
                    EstadoPedido.CANCELADO
            );

            cargarPedidos();

            pedidoSeleccionado = -1;

            JOptionPane.showMessageDialog(
                    this,
                    "Pedido cancelado correctamente"
            );

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo cancelar el pedido:\n"
                            + ex.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void btnEliminarPedidoBD() {

        if (pedidoSeleccionado == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debes seleccionar un pedido de la tabla",
                    "Pedido no seleccionado",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            List<Pedido> pedidos =
                    pedidoDAO.listarTodos();

            if (pedidoSeleccionado >= pedidos.size()) {

                JOptionPane.showMessageDialog(
                        this,
                        "No se pudo encontrar el pedido seleccionado.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            Pedido pedido =
                    pedidos.get(pedidoSeleccionado);

            int respuesta =
                    JOptionPane.showConfirmDialog(
                            this,
                            "¿Deseas eliminar definitivamente el pedido #"
                                    + pedido.getIdPedido()
                                    + "?",
                            "Confirmar eliminación",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );

            if (respuesta != JOptionPane.YES_OPTION) {
                return;
            }

            pedidoDAO.eliminar(
                    pedido.getIdPedido()
            );

            cargarPedidos();

            pedidoSeleccionado = -1;

            JOptionPane.showMessageDialog(
                    this,
                    "Pedido eliminado correctamente.",
                    "Eliminación",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo eliminar el pedido:\n"
                            + ex.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    // =========================================================
    // INICIAR ENTREGA
    // =========================================================

    private void iniciarEntrega() {

        if (pedidoSeleccionado == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Selecciona un pedido para iniciar la entrega.",
                    "Pedido no seleccionado",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            List<Pedido> pedidos =
                    pedidoDAO.listarTodos();

            if (pedidoSeleccionado >= pedidos.size()) {

                JOptionPane.showMessageDialog(
                        this,
                        "No se pudo encontrar el pedido seleccionado.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            Pedido pedido =
                    pedidos.get(pedidoSeleccionado);


            // Verificar estado del pedido

            if (pedido.getEstado() == EstadoPedido.ENTREGADO) {

                JOptionPane.showMessageDialog(
                        this,
                        "El pedido ya fue entregado.",
                        "Pedido no disponible",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            if (pedido.getEstado() == EstadoPedido.EN_REPARTO) {

                JOptionPane.showMessageDialog(
                        this,
                        "El pedido ya se encuentra en reparto.",
                        "Pedido no disponible",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }


            // Obtener repartidores desde MySQL

            List<cl.speedfast.model.Repartidor> repartidores =
                    repartidorDAO.listarTodos();

            if (repartidores.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "No existen repartidores registrados en la base de datos.",
                        "Sin repartidores",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }


               // Seleccionar repartidor mediante JComboBox

            JComboBox<cl.speedfast.model.Repartidor> comboRepartidores =
                    new JComboBox<>();

            for (cl.speedfast.model.Repartidor repartidor : repartidores) {

                comboRepartidores.addItem(repartidor);
            }

            int respuesta =
                    JOptionPane.showConfirmDialog(
                            this,
                            comboRepartidores,
                            "Selecciona el repartidor:",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                    );

            if (respuesta != JOptionPane.OK_OPTION) {
                return;
            }

            Repartidor repartidor =
                    (Repartidor)
                            comboRepartidores.getSelectedItem();

            if (repartidor == null) {
                return;
            }





            // Asignar repartidor

            pedido.asignarRepartidor(
                    repartidor.getNombre()
            );


            // Cambiar estado

            pedido.marcarEnReparto();


            // Actualizar pedido en MySQL

            pedidoDAO.actualizarEstado(
                    pedido.getIdPedido(),
                    EstadoPedido.EN_REPARTO
            );


            // Crear entrega

            Entrega entrega =
                    new Entrega(
                            0,
                            pedido.getIdPedido(),
                            repartidor.getId(),
                            java.time.LocalDate.now(),
                            java.time.LocalTime.now()
                    );


            // Guardar entrega

            entregaDAO.guardar(entrega);


            // Actualizar tabla

            cargarPedidos();


            JOptionPane.showMessageDialog(
                    this,
                    "Entrega iniciada correctamente.\n\n"
                            + "Pedido: "
                            + pedido.getIdPedido()
                            + "\n"
                            + "Repartidor: "
                            + repartidor.getNombre(),
                    "Entrega iniciada",
                    JOptionPane.INFORMATION_MESSAGE
            );
            simularEntrega(pedido);
        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al iniciar la entrega:\n"
                            + ex.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
// =========================================================
// SIMULAR ENTREGA
// =========================================================

    private void simularEntrega(Pedido pedido) {

        int segundos = pedido.calcularTiempoEntrega();

        new Thread(() -> {

            try {

                Thread.sleep(segundos * 1000L);

                pedidoDAO.actualizarEstado(
                        pedido.getIdPedido(),
                        EstadoPedido.ENTREGADO
                );

                SwingUtilities.invokeLater(() -> {

                    cargarPedidos();

                    JOptionPane.showMessageDialog(
                            this,
                            "El pedido #"
                                    + pedido.getIdPedido()
                                    + " ha sido entregado.",
                            "Entrega completada",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                });

            } catch (InterruptedException ex) {

                Thread.currentThread().interrupt();

            } catch (SQLException ex) {

                SwingUtilities.invokeLater(() ->
                        JOptionPane.showMessageDialog(
                                this,
                                "No se pudo actualizar el estado:\n"
                                        + ex.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        )
                );
            }
        }).start();
    }
    // =========================================================
    // CERRAR SESIÓN
    // =========================================================

    private void cerrarSesion() {

        int respuesta =
                JOptionPane.showConfirmDialog(
                        this,
                        "¿Deseas volver al Home?",
                        "Cerrar al Home",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

        if (respuesta == JOptionPane.YES_OPTION) {


            home.setVisible(true);
            this.dispose();
        }
    }
}