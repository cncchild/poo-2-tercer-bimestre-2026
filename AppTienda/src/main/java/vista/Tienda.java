package vista;

import modelo.CategoriaProducto;
import modelo.Producto;
import controlador.ControladorProducto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Tienda extends JFrame {

    private JPanel Tienda;
    private JTextField txtNombre;
    private JComboBox<String> cbmCategoria;
    private JSpinner jValor;
    private JSpinner jsStock;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JButton btnAgregar;
    private JButton btnEditar;
    private JLabel lblTienda;
    private JLabel lblNombre;
    private JLabel lblCategoria;
    private JLabel lblStock;
    private JLabel lblValor;
    private JLabel lblListado;
    private JScrollPane spTabla;
    private JTable tblProductos;
    private JButton btnCerrarSesion;

    private DefaultTableModel model;
    private final ControladorProducto controlador = new ControladorProducto();

    private int productoSeleccionado = -1;
    private String rol;

    public Tienda(String rol) {

        this.rol = rol;

        setTitle("Tienda App - Rol: " + rol);
        setContentPane(Tienda);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        inicializarTabla();
        inicializarBotones();
        cargarCategoria();

        jsStock.setModel(
                new SpinnerNumberModel(0, 0, 10000, 1)
        );

        jValor.setModel(
                new SpinnerNumberModel(0, 0, 100000, 10)
        );

        controlador.agregarProductosIniciales(model);

        aplicarRestriccionesPorRol(rol);
    }

    private void inicializarTabla() {

        String[] columnas = {
                "Nombre",
                "Categoria",
                "Stock",
                "Valor"
        };

        model = new DefaultTableModel(columnas, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblProductos.setModel(model);

        tblProductos.setAutoResizeMode(
                JTable.AUTO_RESIZE_ALL_COLUMNS
        );

        tblProductos.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        tblProductos.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                int fila = tblProductos.getSelectedRow();

                if (fila >= 0) {

                    productoSeleccionado = fila;

                    Producto p =
                            controlador.getProductoByIndex(fila);

                    txtNombre.setText(p.getNombre());

                    cbmCategoria.setSelectedItem(
                            p.getCategoria()
                    );

                    jsStock.setValue(p.getStock());
                    jValor.setValue(p.getValor());
                }
            }
        });
    }

    private void cargarCategoria() {

        cbmCategoria.removeAllItems();

        for (CategoriaProducto c : CategoriaProducto.values()) {
            cbmCategoria.addItem(c.toString());
        }
    }

    private void aplicarRestriccionesPorRol(String rol) {

        if (rol.equalsIgnoreCase("Vendedor")) {

            btnAgregar.setEnabled(false);
            btnEditar.setEnabled(false);
            btnEliminar.setEnabled(false);

            setTitle(getTitle() + " (Vista restringida)");
        }
    }

    private void limpiarFormulario() {

        txtNombre.setText("");
        cbmCategoria.setSelectedIndex(0);
        jsStock.setValue(0);
        jValor.setValue(0);

        productoSeleccionado = -1;

        tblProductos.clearSelection();
    }

    private void agregarProducto() {

        String nombre = txtNombre.getText().trim();

        String categoria =
                (String) cbmCategoria.getSelectedItem();

        int stock =
                (int) jsStock.getValue();

        int valor =
                (int) jValor.getValue();

        if (nombre.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debes ingresar el nombre del producto",
                    "Campo obligatorio",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (categoria == null || categoria.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debes seleccionar una categoría",
                    "Campo obligatorio",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (stock < 0 || stock > 10000) {

            JOptionPane.showMessageDialog(
                    this,
                    "El stock debe estar entre 0 y 10.000",
                    "Stock inválido",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (valor < 0 || valor > 100000) {

            JOptionPane.showMessageDialog(
                    this,
                    "El valor debe estar entre $0 y $100.000",
                    "Valor inválido",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        Producto producto =
                new Producto(nombre, categoria, stock, valor);

        controlador.agregarProducto(producto, model);

        limpiarFormulario();

        JOptionPane.showMessageDialog(
                this,
                "Producto agregado correctamente"
        );
    }
    private void editarProducto() {

        if (productoSeleccionado == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debes seleccionar un producto de la tabla",
                    "Producto no seleccionado",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String nombre = txtNombre.getText().trim();

        String categoria =
                (String) cbmCategoria.getSelectedItem();

        int stock =
                (int) jsStock.getValue();

        int valor =
                (int) jValor.getValue();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debes ingresar el nombre del producto",
                    "Campo obligatorio",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (categoria == null || categoria.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debes seleccionar una categoría",
                    "Campo obligatorio",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (stock < 0 || stock > 10000) {
            JOptionPane.showMessageDialog(
                    this,
                    "El stock debe estar entre 0 y 10.000",
                    "Stock inválido",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (valor < 0 || valor > 100000) {
            JOptionPane.showMessageDialog(
                    this,
                    "El valor debe estar entre $0 y $100.000",
                    "Valor inválido",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Producto producto = new Producto(
                nombre,
                categoria,
                stock,
                valor
        );

        controlador.editarProducto(
                productoSeleccionado,
                producto,
                model
        );

        limpiarFormulario();

        JOptionPane.showMessageDialog(
                this,
                "Producto actualizado correctamente"
        );
    }

    private void eliminarProducto() {

        if (productoSeleccionado == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debes seleccionar un producto de la tabla",
                    "Producto no seleccionado",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Estás seguro de eliminar este producto?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (respuesta == JOptionPane.YES_OPTION) {

            controlador.eliminarProducto(
                    productoSeleccionado,
                    model
            );

            limpiarFormulario();

            JOptionPane.showMessageDialog(
                    this,
                    "Producto eliminado correctamente"
            );
        }
    }

    private void inicializarBotones() {

        btnAgregar.addActionListener(e -> agregarProducto());

        btnEditar.addActionListener(e -> editarProducto());

        btnEliminar.addActionListener(e -> eliminarProducto());

        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
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
                    new controlador.ControladorUsuarios()
            );

            login.setVisible(true);
            this.dispose();
        }
    }
}