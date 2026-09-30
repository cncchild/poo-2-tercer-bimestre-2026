package cl.speedfast.vista;

import cl.speedfast.dao.UsuarioDAO;
import cl.speedfast.model.Usuario;

import javax.swing.*;

/**
 * Ventana utilizada para registrar nuevos usuarios
 * en el sistema SpeedFast.
 *
 * Permite ingresar nombre de usuario, contraseña y rol,
 * utilizando UsuarioDAO para guardar la información
 * en la base de datos.
 *
 * @author Cristian Contreras
 * @version 1.0
 */
public class RegistroUsuarios extends JDialog {

    private JPanel formRegistroUsuarios;
    private JLabel txtRegistroTitulo;
    private JTextField jtfNombre;
    private JLabel txtPassword;
    private JPasswordField jtfContrasenia;
    private JComboBox comboBox1;
    private JLabel txtRol;
    private JButton registrarUsuarioButton;
    private JButton xButton;
    private JLabel txtLogo;

    private final UsuarioDAO usuarioDAO;

    /**
     * Constructor de la ventana de registro de usuarios.
     *
     * @param padre ventana principal desde la cual se abre el diálogo
     * @param usuarioDAO DAO encargado de gestionar los usuarios
     */
    public RegistroUsuarios(
            JFrame padre,
            UsuarioDAO usuarioDAO) {

        this.usuarioDAO = usuarioDAO;

        setTitle("Registrar usuario");
        setContentPane(formRegistroUsuarios);
        setModal(true);
        setSize(400, 350);
        setLocationRelativeTo(padre);
        setResizable(false);

        cargarRoles();

        registrarUsuarioButton.addActionListener(
                e -> registrarUsuario()
        );

        xButton.addActionListener(
                e -> dispose()
        );
    }

    /**
     * Carga los roles disponibles en el ComboBox.
     */
    private void cargarRoles() {

        comboBox1.removeAllItems();

        comboBox1.addItem("usuario");
        comboBox1.addItem("administrador");
    }

    /**
     * Valida los datos ingresados y registra
     * el nuevo usuario mediante UsuarioDAO.
     */
    private void registrarUsuario() {

        String nombreUsuario =
                jtfNombre.getText().trim();

        String contrasenia =
                new String(
                        jtfContrasenia.getPassword()
                );

        String rol =
                comboBox1.getSelectedItem().toString();

        if (nombreUsuario.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debes ingresar un nombre de usuario.",
                    "Campo obligatorio",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (contrasenia.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debes ingresar una contraseña.",
                    "Campo obligatorio",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        Usuario usuario =
                new Usuario(
                        nombreUsuario,
                        contrasenia,
                        rol
                );

        boolean registrado =
                usuarioDAO.guardar(usuario);

        if (registrado) {

            JOptionPane.showMessageDialog(
                    this,
                    "Usuario registrado correctamente.",
                    "Registro exitoso",
                    JOptionPane.INFORMATION_MESSAGE
            );

            limpiarCampos();

            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "El nombre de usuario ya existe.",
                    "Usuario existente",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    /**
     * Limpia los campos del formulario.
     */
    private void limpiarCampos() {

        jtfNombre.setText("");
        jtfContrasenia.setText("");
        comboBox1.setSelectedIndex(0);
    }
}