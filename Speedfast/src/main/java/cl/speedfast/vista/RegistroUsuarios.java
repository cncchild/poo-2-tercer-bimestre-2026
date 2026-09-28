package cl.speedfast.vista;

import cl.speedfast.controlador.ControladorUsuarios;

import javax.swing.*;

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

    private final ControladorUsuarios controladorUsuarios;

    public RegistroUsuarios(
            JFrame padre,
            ControladorUsuarios controladorUsuarios) {

        this.controladorUsuarios = controladorUsuarios;

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

    private void cargarRoles() {

        comboBox1.removeAllItems();

        comboBox1.addItem("usuario");
        comboBox1.addItem("administrador");
    }

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

        boolean registrado =
                controladorUsuarios.registrarUsuario(
                        nombreUsuario,
                        contrasenia,
                        rol
                );

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

    private void limpiarCampos() {

        jtfNombre.setText("");
        jtfContrasenia.setText("");
        comboBox1.setSelectedIndex(0);
    }
}