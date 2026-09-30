package cl.speedfast.vista;

import cl.speedfast.dao.UsuarioDAO;
import cl.speedfast.model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * Ventana utilizada para administrar los usuarios
 * registrados en el sistema SpeedFast.
 *
 * Permite listar, agregar, editar y eliminar usuarios.
 * Las operaciones de persistencia se realizan directamente
 * mediante UsuarioDAO.
 *
 * Los botones de administración se habilitan únicamente
 * para usuarios con rol administrador.
 *
 * @author Cristian Contreras
 * @version 1.0
 */
public class ListaUsuarios extends JFrame {

    private JPanel formListaUsuarios;
    private JLabel lblTitulo;
    private JButton btnCerrarListaUsuarios;
    private JButton btnAgregarUsuario;
    private JButton btnEditarUsuario;
    private JButton btnEliminarUsuario;
    private JTable jtableUsuarios;
    private JLabel txtTituloUsuarios;
    private JButton btnRecargarUsuario;
    private JScrollPane jpTable;
    private JComboBox<String> jcbEditorRol;

    private DefaultTableModel model;

    private final String rol;
    private final UsuarioDAO usuarioDAO;
    private final Home home;

    private int usuarioSeleccionado = -1;

    /**
     * Constructor de la ventana de administración de usuarios.
     *
     * @param home ventana principal del sistema
     * @param rol rol del usuario que inició sesión
     * @param usuarioDAO DAO encargado de gestionar los usuarios
     */
    public ListaUsuarios(
            Home home,
            String rol,
            UsuarioDAO usuarioDAO) {

        this.home = home;
        this.rol = rol;
        this.usuarioDAO = usuarioDAO;

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

    /**
     * Configura la tabla donde se muestran los usuarios.
     *
     * Define las columnas, impide la edición directa de las celdas
     * y permite seleccionar un único usuario.
     */
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

    /**
     * Carga los usuarios registrados en la tabla.
     *
     * Obtiene los datos desde UsuarioDAO y actualiza
     * el contenido de la tabla.
     */
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

    /**
     * Configura los eventos de los botones de la ventana.
     */
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

    /**
     * Configura los permisos de los botones según el rol
     * del usuario que inició sesión.
     *
     * Los usuarios con rol administrador pueden agregar,
     * editar y eliminar usuarios.
     */
    private void configurarPermisos() {

        boolean esAdministrador =
                rol.equalsIgnoreCase("administrador");

        btnAgregarUsuario.setEnabled(esAdministrador);
        btnEditarUsuario.setEnabled(esAdministrador);
        btnEliminarUsuario.setEnabled(esAdministrador);
    }

    /**
     * Abre la ventana para registrar un nuevo usuario.
     *
     * Envía UsuarioDAO para que el registro pueda realizar
     * la operación directamente sobre la base de datos.
     */
    private void agregarUsuario() {

        RegistroUsuarios registro =
                new RegistroUsuarios(
                        this,
                        usuarioDAO
                );

        registro.setVisible(true);
    }

    /**
     * Abre la ventana para editar el usuario seleccionado.
     *
     * Busca el usuario seleccionado en la base de datos
     * y envía sus datos a la ventana de edición.
     */
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

    /**
     * Elimina el usuario seleccionado después de solicitar
     * confirmación al usuario.
     */
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

    /**
     * Solicita confirmación y vuelve a la ventana Home.
     */
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