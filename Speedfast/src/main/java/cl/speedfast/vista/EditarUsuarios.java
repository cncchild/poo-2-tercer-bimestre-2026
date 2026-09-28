package cl.speedfast.vista;

import cl.speedfast.dao.UsuarioDAO;
import cl.speedfast.model.Usuario;

import javax.swing.*;

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

    private void cargarRoles() {

        jcbEditorRol.removeAllItems();

        jcbEditorRol.addItem("administrador");
        jcbEditorRol.addItem("usuario");
    }

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