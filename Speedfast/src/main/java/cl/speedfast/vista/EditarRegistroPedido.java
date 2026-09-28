package cl.speedfast.vista;

import cl.speedfast.dao.PedidoDAO;
import cl.speedfast.model.Pedido;

import javax.swing.*;
import java.sql.SQLException;

public class EditarRegistroPedido extends JFrame {

    private JPanel formEditarRegistroPedido;
    private JLabel txtTituloEditarRegistroPedido;
    private JButton xButton;
    private JLabel txtEditarIngresePedido;
    private JComboBox cbEditarIngresePedido;
    private JLabel txtEditarIngreseDireccion;
    private JTextField jtfIngreseDireccion;
    private JLabel txtIngreseKilometros;
    private JTextField jtfIngreseKilometros;
    private JButton btnEditarPedido;

    private final ListaPedidos listaPedidos;
    private final PedidoDAO pedidoDAO;
    private final Pedido pedido;

    public EditarRegistroPedido(
            ListaPedidos listaPedidos,
            PedidoDAO pedidoDAO,
            Pedido pedido) {

        this.listaPedidos = listaPedidos;
        this.pedidoDAO = pedidoDAO;
        this.pedido = pedido;

        setTitle("SpeedFast - Editar pedido");
        setContentPane(formEditarRegistroPedido);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(450, 350);
        setLocationRelativeTo(listaPedidos);
        setResizable(false);

        cargarDatos();

        btnEditarPedido.addActionListener(
                e -> guardarCambios()
        );

        xButton.addActionListener(e -> cancelarEdicion());
    }

    // =========================================================
    // CARGAR DATOS DEL PEDIDO
    // =========================================================

    private void cargarDatos() {

        cbEditarIngresePedido.removeAllItems();

        cbEditarIngresePedido.addItem("Comida");
        cbEditarIngresePedido.addItem("Encomienda");
        cbEditarIngresePedido.addItem("Express");

        cbEditarIngresePedido.setSelectedItem(
                pedido.obtenerTipoPedido()
        );

        jtfIngreseDireccion.setText(
                pedido.getDireccionEntrega()
        );

        jtfIngreseKilometros.setText(
                String.valueOf(pedido.getDistanciaKm())
        );
    }

    // =========================================================
    // GUARDAR CAMBIOS
    // =========================================================

    private void guardarCambios() {

        String direccion =
                jtfIngreseDireccion.getText().trim();

        String kilometrosTexto =
                jtfIngreseKilometros.getText().trim();

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
                    Double.parseDouble(
                            kilometrosTexto
                    );

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "La distancia debe ser un número válido",
                    "Dato inválido",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (distancia <= 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "La distancia debe ser mayor a 0",
                    "Dato inválido",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            pedido.setDireccionEntrega(direccion);
            pedido.setDistanciaKm(distancia);

            pedidoDAO.actualizar(pedido);

            JOptionPane.showMessageDialog(
                    this,
                    "Pedido #" + pedido.getIdPedido()
                            + " editado correctamente",
                    "Pedido actualizado",
                    JOptionPane.INFORMATION_MESSAGE
            );

            listaPedidos.cargarPedidos();

            dispose();

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo actualizar el pedido:\n"
                            + ex.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // CANCELAR
    // =========================================================

    private void cancelarEdicion() {

        int respuesta =
                JOptionPane.showConfirmDialog(
                        this,
                        "¿Deseas cancelar la edición del pedido?",
                        "Cancelar edición",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

        if (respuesta == JOptionPane.YES_OPTION) {
            dispose();
        }
    }
}