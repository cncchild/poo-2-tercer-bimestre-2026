package cl.speedfast.vista;

import cl.speedfast.controlador.ControladorUsuarios;
import cl.speedfast.dao.EntregaDAO;
import cl.speedfast.dao.PedidoDAO;
import cl.speedfast.dao.RepartidorDAO;
import cl.speedfast.model.Usuario;

import javax.swing.*;

public class Login extends JFrame {

    private JPanel Login;
    private JLabel txtTitulo;
    private JLabel txtParrafo;
    private JTextField jNombreDejUsuarioTxt;
    private JPasswordField jContraseñaPassword;
    private JButton BtnIniciar;
    private JButton aquiButton;
    private JButton btnCerrar;

    private final ControladorUsuarios controladorUsuarios;
    private final PedidoDAO pedidoDAO;
    private final RepartidorDAO repartidorDAO;
    private final EntregaDAO entregaDAO;

    public Login(
            ControladorUsuarios controladorUsuarios,
            PedidoDAO pedidoDAO,
            RepartidorDAO repartidorDAO,
            EntregaDAO entregaDAO) {

        this.controladorUsuarios = controladorUsuarios;
        this.pedidoDAO = pedidoDAO;
        this.repartidorDAO = repartidorDAO;
        this.entregaDAO = entregaDAO;

        setTitle("SpeedFast - Login");
        setContentPane(Login);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        BtnIniciar.addActionListener(e -> iniciarSesion());

        btnCerrar.addActionListener(e -> cerrarAplicacion());

        aquiButton.addActionListener(e -> registrarUsuario());
    }

    private void iniciarSesion() {

        String nombreUsuario =
                jNombreDejUsuarioTxt.getText().trim();

        String contrasenia =
                new String(jContraseñaPassword.getPassword());

        if (nombreUsuario.isEmpty() || contrasenia.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debes ingresar usuario y contraseña",
                    "Datos incompletos",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        Usuario usuario =
                controladorUsuarios.autenticar(
                        nombreUsuario,
                        contrasenia
                );

        if (usuario != null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Bienvenido, " + usuario.getNombreUsuario()
            );

            Home home =
                    new Home(
                            usuario.getNombreUsuario(),
                            usuario.getRol(),
                            controladorUsuarios,
                            pedidoDAO,
                            repartidorDAO,
                            entregaDAO
                    );
            home.setVisible(true);

            this.dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Usuario o contraseña incorrectos",
                    "Error de inicio de sesión",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void registrarUsuario() {

        RegistroUsuarios registro =
                new RegistroUsuarios(
                        this,
                        controladorUsuarios
                );

        registro.setVisible(true);
    }
    private void cerrarAplicacion() {

        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Estás seguro de que deseas cerrar la aplicación?",
                "Cerrar",
                JOptionPane.YES_NO_OPTION
        );

        if (respuesta == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}