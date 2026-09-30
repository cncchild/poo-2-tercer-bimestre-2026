package cl.speedfast.vista;

import cl.speedfast.dao.UsuarioDAO;
import cl.speedfast.model.Usuario;

import javax.swing.*;

/**
 * Ventana utilizada para editar los datos de un usuario
 * registrado en el sistema SpeedFast.
 *
 * Permite modificar el nombre de usuario, la contraseña
 * y el rol asignado. Los cambios son almacenados mediante
 * UsuarioDAO.
 *
 * @author Cristian Contreras
 * @version 1.0
 */
public class EditarUsuarios extends JFrame {

    private JPanel panel1;
    private JLabel txtEditorUsuarios;
    private JButton btnCerrarEditorUsuarios;
    private JLabel txtNombre;
    private JTextField jtfNombre;
    private JLabel txtEditarContrasenia;
    private JPasswordField jtfcontrasenia;
    private JLabel txtEditorRol;
    private JComboBox<String> jcbEditorRol;
    private JButton btnEditarUsuario;

    private final ListaUsuarios listaUsuarios;
    private final UsuarioDAO usuarioDAO;
    private final String nombreUsuarioOriginal;

    /**
     * Constructor de la ventana de edición de usuarios.
     *
     * @param listaUsuarios ventana que contiene la lista de usuarios
     * @param usuarioDAO objeto encargado de actualizar el usuario
     *                   en la base de datos
     * @param usuario usuario que será editado
     */
    public EditarUsuarios(
            ListaUsuarios listaUsuarios,
            UsuarioDAO usuarioDAO,
            Usuario usuario) {

        this.listaUsuarios = listaUsuarios;
        this.usuarioDAO = usuarioDAO;
        this.nombreUsuarioOriginal =
                usuario.getNombreUsuario();

        setTitle("SpeedFast - Editar Usuario");
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        cargarRoles();
        cargarDatos(usuario);

        btnEditarUsuario.addActionListener(
                e -> editarUsuario()
        );

        btnCerrarEditorUsuarios.addActionListener(
                e -> cerrarEditor()
        );
    }

    /**
     * Carga los roles disponibles en el selector
     * de roles del formulario.
     */
    private void cargarRoles() {

        jcbEditorRol.removeAllItems();

        jcbEditorRol.addItem("administrador");
        jcbEditorRol.addItem("usuario");
    }

    /**
     * Carga en el formulario los datos actuales
     * del usuario seleccionado.
     *
     * @param usuario usuario cuyos datos serán cargados
     */
    private void cargarDatos(Usuario usuario) {

        jtfNombre.setText(
                usuario.getNombreUsuario()
        );

        jtfcontrasenia.setText(
                usuario.getContrasenia()
        );

        jcbEditorRol.setSelectedItem(
                usuario.getRol()
        );
    }

    /**
     * Valida y actualiza los datos del usuario.
     *
     * Comprueba que el nombre de usuario y la contraseña
     * no estén vacíos. Luego crea un usuario actualizado
     * y solicita a UsuarioDAO guardar los cambios.
     */
    private void editarUsuario() {

        String nombreUsuario =
                jtfNombre.getText().trim();

        String contrasenia =
                new String(
                        jtfcontrasenia.getPassword()
                ).trim();

        String rol =
                jcbEditorRol.getSelectedItem()
                        .toString();

        if (nombreUsuario.isEmpty()
                || contrasenia.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debes completar todos los campos",
                    "Datos incompletos",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        Usuario usuarioActualizado =
                new Usuario(
                        nombreUsuario,
                        contrasenia,
                        rol
                );

        boolean actualizado =
                usuarioDAO.actualizar(
                        nombreUsuarioOriginal,
                        usuarioActualizado
                );

        if (actualizado) {

            JOptionPane.showMessageDialog(
                    this,
                    "Usuario actualizado correctamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE
            );

            listaUsuarios.cargarUsuarios();

            this.dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo actualizar el usuario",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Solicita confirmación antes de cerrar la ventana
     * sin guardar los cambios realizados.
     */
    private void cerrarEditor() {

        int respuesta =
                JOptionPane.showConfirmDialog(
                        this,
                        "¿Deseas cancelar la edición?",
                        "Cancelar",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

        if (respuesta == JOptionPane.YES_OPTION) {
            this.dispose();
        }
    }
}