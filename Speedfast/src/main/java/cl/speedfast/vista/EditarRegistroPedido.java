package cl.speedfast.vista;

import cl.speedfast.dao.PedidoDAO;
import cl.speedfast.model.Pedido;

import javax.swing.*;
import java.sql.SQLException;

/**
 * Ventana utilizada para editar los datos de un pedido
 * registrado en el sistema SpeedFast.
 *
 * Permite modificar la dirección y la distancia del pedido
 * y guardar los cambios en la base de datos mediante PedidoDAO.
 *
 * @author Cristian Contreras
 * @version 1.0
 */
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

    /**
     * Constructor de la ventana de edición de pedidos.
     *
     * @param listaPedidos ventana que contiene la lista de pedidos
     * @param pedidoDAO objeto encargado de actualizar el pedido
     *                  en la base de datos
     * @param pedido pedido que será editado
     */
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

        xButton.addActionListener(
                e -> cancelarEdicion()
        );
    }

    /**
     * Carga en el formulario los datos actuales
     * del pedido seleccionado.
     *
     * Se cargan el tipo, la dirección y la distancia
     * registrada para el pedido.
     */
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

    /**
     * Valida los datos ingresados y actualiza el pedido
     * en la base de datos.
     *
     * Verifica que la dirección no esté vacía y que la
     * distancia sea un número válido mayor que cero.
     *
     * Si la actualización se realiza correctamente,
     * se recarga la lista de pedidos.
     */
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

    /**
     * Solicita confirmación al usuario antes de cerrar
     * la ventana sin guardar los cambios.
     */
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