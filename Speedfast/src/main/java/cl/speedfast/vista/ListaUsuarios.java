package cl.speedfast.vista;

import cl.speedfast.controlador.ControladorUsuarios;
import cl.speedfast.dao.UsuarioDAO;
import cl.speedfast.model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ListaUsuarios extends JFrame {

    private JPanel formListaUsuarios;
    private JLabel lblTitulo;
    private JButton btnCerrarListaUsuarios;
    private JButton btnAgregarUsuario;
    private JButton btnEditarUsuario;
    private JButton btnEliminarUsuario;
    private JPanel jpTable;
    private JTable jtableUsuarios;
    private JLabel txtTituloUsuarios;
    private JButton btnRecargarUsuario;
    private JComboBox<String> jcbEditorRol;

    private DefaultTableModel model;

    private final String rol;
    private final ControladorUsuarios controladorUsuarios;
    private final UsuarioDAO usuarioDAO;
    private final Home home;

    private int usuarioSeleccionado = -1;

    public ListaUsuarios(
            Home home,
            String rol,
            ControladorUsuarios controladorUsuarios) {

        this.home = home;
        this.rol = rol;
        this.controladorUsuarios = controladorUsuarios;
        this.usuarioDAO = new UsuarioDAO();

        setTitle("SpeedFast - Usuarios");
        setContentPane(formListaUsuarios);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        inicializarTabla();
        inicializarBotones();
        cargarUsuarios();
    }

    private void inicializarTabla() {

        String[] columnas = {
                "Usuario",
                "Rol"
        };

        model = new DefaultTableModel(columnas, 0) {

            @Override
            public boolean isCellEditable(
                    int row,
                    int column) {

                return false;
            }
        };

        jtableUsuarios.setModel(model);

        jtableUsuarios.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        jtableUsuarios.getSelectionModel()
                .addListSelectionListener(e -> {

                    if (!e.getValueIsAdjusting()) {

                        usuarioSeleccionado =
                                jtableUsuarios.getSelectedRow();
                    }
                });
    }

    public void cargarUsuarios() {

        model.setRowCount(0);

        List<Usuario> usuarios =
                usuarioDAO.listarTodos();

        for (Usuario usuario : usuarios) {

            model.addRow(
                    new Object[]{
                            usuario.getNombreUsuario(),
                            usuario.getRol()
                    }
            );
        }

        usuarioSeleccionado = -1;
    }

    private void inicializarBotones() {

        btnAgregarUsuario.addActionListener(
                e -> agregarUsuario()
        );

        btnEditarUsuario.addActionListener(
                e -> editarUsuario()
        );

        btnEliminarUsuario.addActionListener(
                e -> eliminarUsuario()
        );
        btnRecargarUsuario.addActionListener(
                e -> cargarUsuarios()
        );

        btnCerrarListaUsuarios.addActionListener(
                e -> volverAlHome()
        );

        configurarPermisos();
    }

    private void configurarPermisos() {

        boolean esAdministrador =
                rol.equalsIgnoreCase("administrador");

        btnAgregarUsuario.setEnabled(esAdministrador);
        btnEditarUsuario.setEnabled(esAdministrador);
        btnEliminarUsuario.setEnabled(esAdministrador);
    }

    private void agregarUsuario() {

        RegistroUsuarios registro =
                new RegistroUsuarios(
                        this,
                        controladorUsuarios
                );

        registro.setVisible(true);
    }

    private void editarUsuario() {

        if (usuarioSeleccionado == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debes seleccionar un usuario de la tabla",
                    "Usuario no seleccionado",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String nombreUsuario =
                model.getValueAt(
                        usuarioSeleccionado,
                        0
                ).toString();

        List<Usuario> usuarios =
                usuarioDAO.listarTodos();

        Usuario usuarioSeleccionadoObj = null;

        for (Usuario usuario : usuarios) {

            if (usuario.getNombreUsuario()
                    .equalsIgnoreCase(nombreUsuario)) {

                usuarioSeleccionadoObj = usuario;
                break;
            }
        }

        if (usuarioSeleccionadoObj == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se encontró el usuario",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        EditarUsuarios editarUsuarios =
                new EditarUsuarios(
                        this,
                        usuarioDAO,
                        usuarioSeleccionadoObj
                );

        editarUsuarios.setVisible(true);
    }
    private void eliminarUsuario() {

        if (usuarioSeleccionado == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debes seleccionar un usuario de la tabla",
                    "Usuario no seleccionado",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String nombreUsuario =
                model.getValueAt(
                        usuarioSeleccionado,
                        0
                ).toString();

        int respuesta =
                JOptionPane.showConfirmDialog(
                        this,
                        "¿Deseas eliminar al usuario \""
                                + nombreUsuario
                                + "\"?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }

        boolean eliminado =
                usuarioDAO.eliminar(nombreUsuario);

        if (eliminado) {

            cargarUsuarios();

            JOptionPane.showMessageDialog(
                    this,
                    "Usuario eliminado correctamente",
                    "Usuario eliminado",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo eliminar el usuario",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    private void volverAlHome() {

        int respuesta =
                JOptionPane.showConfirmDialog(
                        this,
                        "¿Deseas volver al Home?",
                        "Volver al inicio",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

        if (respuesta == JOptionPane.YES_OPTION) {




            home.setVisible(true);

            this.dispose();
        }
    }

}