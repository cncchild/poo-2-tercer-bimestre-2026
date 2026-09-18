package cl.speedfast.vista;

import cl.speedfast.controlador.ControladorPedidos;
import cl.speedfast.model.Pedido;
import cl.speedfast.model.PedidoComida;
import cl.speedfast.model.PedidoEncomienda;
import cl.speedfast.model.PedidoExpress;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VentanaPrincipal extends JFrame {

    private JPanel ventanaPrincipal;
    private JLabel lblTitulo;
    private JLabel lblTipoPedido;
    private JComboBox jcIngresePedido;
    private JLabel lblListarPedidos;
    private JPanel jpListaPedidos;
    private JTable jtListarPedido;
    private JPanel jpBtn;
    private JLabel lblDireccion;
    private JTextField txtDireccion;
    private JLabel lblDistancia;
    private JTextArea txtKilometros;
    private JButton btnAgregar;
    private JButton btnLimpiar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnLogOut;
    private JPanel jpBtnCrud;
    private JPanel jpLogout;
    private JButton btnIniciarEntrega;
    private JPanel jpEntrega;

    private DefaultTableModel model;

    private final ControladorPedidos controlador;

    private int pedidoSeleccionado = -1;

    private String rol;

    public VentanaPrincipal(String rol, ControladorPedidos controlador) {

        this.rol = rol;
        this.controlador = controlador;

        setTitle("Pedidos Flash - Rol: " + rol);
        setContentPane(ventanaPrincipal);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 450);
        setLocationRelativeTo(null);
        setResizable(false);

        inicializarTabla();
        inicializarTipoPedido();
        inicializarBotones();

        cargarPedidos();
    }

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

        jtListarPedido.setAutoResizeMode(
                JTable.AUTO_RESIZE_ALL_COLUMNS
        );

        jtListarPedido.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        jtListarPedido.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                int fila = jtListarPedido.getSelectedRow();

                if (fila >= 0) {

                    pedidoSeleccionado = fila;

                    Pedido pedido =
                            controlador.obtenerPedidos().get(fila);

                    txtDireccion.setText(
                            pedido.getDireccionEntrega()
                    );

                    txtKilometros.setText(
                            String.valueOf(pedido.getDistanciaKm())
                    );

                    seleccionarTipoPedido(pedido);
                }
            }
        });
    }

    private void inicializarTipoPedido() {

        jcIngresePedido.removeAllItems();

        jcIngresePedido.addItem("Comida");
        jcIngresePedido.addItem("Encomienda");
        jcIngresePedido.addItem("Express");
    }

    private void seleccionarTipoPedido(Pedido pedido) {

        if (pedido instanceof PedidoComida) {
            jcIngresePedido.setSelectedItem("Comida");

        } else if (pedido instanceof PedidoEncomienda) {
            jcIngresePedido.setSelectedItem("Encomienda");

        } else if (pedido instanceof PedidoExpress) {
            jcIngresePedido.setSelectedItem("Express");
        }
    }

    private void cargarPedidos() {

        model.setRowCount(0);

        for (Pedido pedido : controlador.obtenerPedidos()) {

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
    }

    private void limpiarFormulario() {

        jcIngresePedido.setSelectedIndex(0);

        txtDireccion.setText("");

        txtKilometros.setText("");

        pedidoSeleccionado = -1;

        jtListarPedido.clearSelection();
    }

    private void agregarPedido() {

        String direccion =
                txtDireccion.getText().trim();

        String kilometrosTexto =
                txtKilometros.getText().trim();

        int tipo =
                jcIngresePedido.getSelectedIndex() + 1;

        if (direccion.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debes ingresar la dirección de entrega",
                    "Campo obligatorio",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (kilometrosTexto.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debes ingresar la distancia en kilómetros",
                    "Campo obligatorio",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        double distancia;

        try {

            distancia =
                    Double.parseDouble(kilometrosTexto);

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "La distancia debe ser un número válido",
                    "Distancia inválida",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (distancia <= 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "La distancia debe ser mayor a 0",
                    "Distancia inválida",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int id = obtenerNuevoId();

        controlador.registrarPedido(
                tipo,
                id,
                direccion,
                distancia
        );

        cargarPedidos();

        limpiarFormulario();

        JOptionPane.showMessageDialog(
                this,
                "Pedido #" + id +
                        " agregado correctamente"
        );
    }

    private int obtenerNuevoId() {

        int mayorId = 0;

        for (Pedido pedido : controlador.obtenerPedidos()) {

            if (pedido.getIdPedido() > mayorId) {
                mayorId = pedido.getIdPedido();
            }
        }

        return mayorId + 1;
    }

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

        String direccion =
                txtDireccion.getText().trim();

        String kilometrosTexto =
                txtKilometros.getText().trim();

        if (direccion.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debes ingresar la dirección de entrega",
                    "Campo obligatorio",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (kilometrosTexto.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debes ingresar la distancia en kilómetros",
                    "Campo obligatorio",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        double distancia;

        try {

            distancia =
                    Double.parseDouble(kilometrosTexto);

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "La distancia debe ser un número válido",
                    "Distancia inválida",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (distancia <= 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "La distancia debe ser mayor a 0",
                    "Distancia inválida",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        Pedido pedido =
                controlador.obtenerPedidos()
                        .get(pedidoSeleccionado);

        controlador.editarPedido(
                pedido.getIdPedido(),
                direccion,
                distancia
        );

        cargarPedidos();

        limpiarFormulario();

        JOptionPane.showMessageDialog(
                this,
                "Pedido #" + pedido.getIdPedido()
                        + " editado correctamente"
        );
    }

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

        Pedido pedido =
                controlador.obtenerPedidos()
                        .get(pedidoSeleccionado);

        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Deseas cancelar el pedido #"
                        + pedido.getIdPedido()
                        + "?",
                "Confirmar cancelación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (respuesta == JOptionPane.YES_OPTION) {

            controlador.cancelarPedido(
                    pedido.getIdPedido()
            );

            cargarPedidos();

            limpiarFormulario();

            JOptionPane.showMessageDialog(
                    this,
                    "Pedido cancelado correctamente"
            );
        }
    }

    private void iniciarEntrega() {

        if (pedidoSeleccionado == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debes seleccionar un pedido de la tabla",
                    "Pedido no seleccionado",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        Pedido pedido =
                controlador.obtenerPedidos()
                        .get(pedidoSeleccionado);

        if (pedido.getEstado()
                != cl.speedfast.model.EstadoPedido.PENDIENTE) {

            JOptionPane.showMessageDialog(
                    this,
                    "El pedido no está pendiente.",
                    "Entrega no disponible",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int idPedido = pedido.getIdPedido();

        JOptionPane.showMessageDialog(
                this,
                "Iniciando entrega del pedido #" + idPedido
        );

        // Evita iniciar dos veces el mismo pedido.
        btnIniciarEntrega.setEnabled(false);

        // IMPORTANTE:
        // Aquí se procesa SOLO el pedido seleccionado.
        controlador.iniciarEntrega(
                idPedido,
                pedidoActualizado -> {

                    SwingUtilities.invokeLater(() -> {

                        cargarPedidos();

                        if (pedidoActualizado.getEstado()
                                == cl.speedfast.model.EstadoPedido.ENTREGADO) {

                            JOptionPane.showMessageDialog(
                                    this,
                                    "Pedido #"
                                            + pedidoActualizado.getIdPedido()
                                            + " entregado correctamente."
                            );

                            configurarPermisos();
                        }
                    });
                }
        );
    }
    private void inicializarBotones() {

        btnAgregar.addActionListener(
                e -> agregarPedido()
        );

        btnEditar.addActionListener(
                e -> editarPedido()
        );

        btnEliminar.addActionListener(
                e -> eliminarPedido()
        );

        btnLimpiar.addActionListener(
                e -> limpiarFormulario()
        );

        btnLogOut.addActionListener(
                e -> cerrarSesion()
        );
        btnIniciarEntrega.addActionListener(
                e -> iniciarEntrega()
        );
        configurarPermisos();
    }
    private void configurarPermisos() {

        boolean esAdministrador =
                rol.equalsIgnoreCase("administrador");

        btnEditar.setEnabled(esAdministrador);
        btnEliminar.setEnabled(esAdministrador);
        btnIniciarEntrega.setEnabled(esAdministrador);
    }
    private void cerrarSesion() {

        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Deseas cerrar sesión y volver al Login?",
                "Cerrar sesión",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (respuesta == JOptionPane.YES_OPTION) {

            Login login = new Login(
                    new cl.speedfast.controlador.ControladorUsuarios(),
                    controlador
            );

            login.setVisible(true);

            this.dispose();
        }
    }
}