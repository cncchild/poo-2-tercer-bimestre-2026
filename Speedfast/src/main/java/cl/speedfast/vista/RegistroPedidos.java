package cl.speedfast.vista;

import cl.speedfast.dao.EntregaDAO;
import cl.speedfast.dao.PedidoDAO;
import cl.speedfast.dao.RepartidorDAO;
import cl.speedfast.model.Pedido;
import cl.speedfast.model.PedidoComida;
import cl.speedfast.model.PedidoEncomienda;
import cl.speedfast.model.PedidoExpress;

import javax.swing.*;

public class RegistroPedidos extends JFrame {

    private JPanel formRegistroPedidos;
    private JLabel textTitiloRegistrarPedido;
    private JLabel txtIngresePedido;
    private JLabel txtIngreseDireccion;
    private JTextField jtfIngreseDireccion;
    private JLabel txtIngreseKilometros;
    private JTextField jtfIngreseKilometros;
    private JButton btnGuardarPedido;
    private JComboBox<String> jcbIngresarPedido;
    private JButton btnCancelarRegistro;

    private final PedidoDAO pedidoDAO;
    private final RepartidorDAO repartidorDAO;
    private final EntregaDAO entregaDAO;

    private final ListaPedidos listaPedidos;

    public RegistroPedidos(
            ListaPedidos listaPedidos,
            PedidoDAO pedidoDAO,
            RepartidorDAO repartidorDAO,
            EntregaDAO entregaDAO) {

        this.listaPedidos = listaPedidos;
        this.pedidoDAO = pedidoDAO;
        this.repartidorDAO = repartidorDAO;
        this.entregaDAO = entregaDAO;

        setTitle("SpeedFast - Registrar pedido");
        setContentPane(formRegistroPedidos);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(450, 350);
        setLocationRelativeTo(listaPedidos);
        setResizable(false);

        // Tipos de pedido
        jcbIngresarPedido.removeAllItems();

        jcbIngresarPedido.addItem("Comida");
        jcbIngresarPedido.addItem("Encomienda");
        jcbIngresarPedido.addItem("Express");

        // Botón guardar
        btnGuardarPedido.addActionListener(
                e -> guardarPedido()
        );

        // Botón cancelar
        btnCancelarRegistro.addActionListener(e -> {

            int respuesta = JOptionPane.showConfirmDialog(
                    this,
                    "¿Deseas cancelar el registro del pedido?",
                    "Cancelar registro",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (respuesta == JOptionPane.YES_OPTION) {
                dispose();
            }
        });
    }

    // =========================================================
    // GUARDAR PEDIDO
    // =========================================================

    private void guardarPedido() {

        String direccion =
                jtfIngreseDireccion.getText().trim();

        String kilometrosTexto =
                jtfIngreseKilometros.getText().trim();

        // Validar dirección
        if (direccion.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debes ingresar la dirección de entrega",
                    "Campo obligatorio",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // Validar kilómetros
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

        } catch (NumberFormatException ex) {

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

        // Obtener ID
        int id;

        try {

            id = pedidoDAO.obtenerSiguienteId();

        } catch (java.sql.SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo obtener el siguiente ID:\n"
                            + ex.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        // Obtener tipo
        int tipo =
                jcbIngresarPedido.getSelectedIndex();

        Pedido pedido;

        switch (tipo) {

            case 0:

                pedido =
                        new PedidoComida(
                                id,
                                direccion,
                                distancia
                        );

                break;

            case 1:

                pedido =
                        new PedidoEncomienda(
                                id,
                                direccion,
                                distancia
                        );

                break;

            case 2:

                pedido =
                        new PedidoExpress(
                                id,
                                direccion,
                                distancia
                        );

                break;

            default:

                JOptionPane.showMessageDialog(
                        this,
                        "Debes seleccionar un tipo de pedido",
                        "Tipo de pedido",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
        }

        // Guardar en MySQL
        try {

            pedidoDAO.guardar(pedido);

            JOptionPane.showMessageDialog(
                    this,
                    "Pedido #" + id
                            + " agregado correctamente",
                    "Pedido registrado",
                    JOptionPane.INFORMATION_MESSAGE
            );

            // Actualizar tabla
            listaPedidos.cargarPedidos();

            // Cerrar formulario
            dispose();

        } catch (java.sql.SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo guardar el pedido:\n"
                            + ex.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}