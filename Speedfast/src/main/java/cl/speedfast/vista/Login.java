package cl.speedfast.vista;

import cl.speedfast.controlador.ControladorPedidos;
import cl.speedfast.controlador.ControladorUsuarios;
import cl.speedfast.model.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {

    private JPanel Login;
    private JLabel lblLogin;
    private JLabel lblUsuario;
    private JLabel lblContrasenia;
    private JTextField txtUsuario;
    private JPasswordField txtContrasenia;
    private JButton btnAcceder;
    private JButton btnCancelar;

    private ControladorUsuarios controladorUsuarios;
    private ControladorPedidos controladorPedidos;

    public Login(
            ControladorUsuarios controladorUsuarios,
            ControladorPedidos controladorPedidos) {

        this.controladorUsuarios = controladorUsuarios;
        this.controladorPedidos = controladorPedidos;

        setTitle("Login Pedidos Flash");
        setContentPane(Login);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(350, 250);
        setLocationRelativeTo(null);
        setResizable(false);

        btnAcceder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autenticarUsuario();
            }
        });

        btnCancelar.addActionListener(e -> salir());
    }

    private void autenticarUsuario() {

        String nombre = txtUsuario.getText().trim();

        String pass =
                new String(txtContrasenia.getPassword());

        Usuario usuario =
                controladorUsuarios.autenticar(nombre, pass);

        if (usuario != null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Bienvenido, " + usuario.getRol()
            );

            VentanaPrincipal ventana =
                    new VentanaPrincipal(
                            usuario.getRol(),
                            controladorPedidos
                    );

            ventana.setVisible(true);

            this.dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Usuario o contraseña incorrectos",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void salir() {

        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Estás seguro de que deseas cerrar la aplicación?",
                "Salir",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (respuesta == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}