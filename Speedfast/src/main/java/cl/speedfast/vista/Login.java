package cl.speedfast.vista;

import cl.speedfast.dao.EntregaDAO;
import cl.speedfast.dao.PedidoDAO;
import cl.speedfast.dao.RepartidorDAO;
import cl.speedfast.dao.UsuarioDAO;
import cl.speedfast.model.Usuario;

import javax.swing.*;

/**
 * Ventana de inicio de sesión de la aplicación SpeedFast.
 *
 * Permite autenticar a los usuarios registrados y acceder
 * a la ventana principal del sistema.
 *
 * También permite registrar un nuevo usuario y cerrar
 * la aplicación.
 *
 * @author Cristian Contreras
 * @version 1.0
 */
public class Login extends JFrame {

    private JPanel Login;
    private JLabel txtTitulo;
    private JLabel txtParrafo;
    private JTextField jNombreDejUsuarioTxt;
    private JPasswordField jContraseñaPassword;
    private JButton BtnIniciar;
    private JButton aquiButton;
    private JButton btnCerrar;

    private final UsuarioDAO usuarioDAO;
    private final PedidoDAO pedidoDAO;
    private final RepartidorDAO repartidorDAO;
    private final EntregaDAO entregaDAO;

    /**
     * Constructor de la ventana de inicio de sesión.
     *
     * Recibe los DAO necesarios para autenticar usuarios
     * y acceder posteriormente a las funcionalidades
     * del sistema.
     *
     * @param usuarioDAO DAO encargado de gestionar los usuarios
     * @param pedidoDAO DAO encargado de gestionar los pedidos
     * @param repartidorDAO DAO encargado de gestionar los repartidores
     * @param entregaDAO DAO encargado de gestionar las entregas
     */
    public Login(
            UsuarioDAO usuarioDAO,
            PedidoDAO pedidoDAO,
            RepartidorDAO repartidorDAO,
            EntregaDAO entregaDAO) {

        this.usuarioDAO = usuarioDAO;
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

    /**
     * Valida las credenciales ingresadas por el usuario
     * e inicia la sesión si son correctas.
     *
     * Verifica que el nombre de usuario y la contraseña
     * no estén vacíos antes de realizar la autenticación.
     */
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
                usuarioDAO.autenticar(
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
                            usuarioDAO,
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

    /**
     * Abre la ventana de registro de usuarios.
     *
     * Actualmente utiliza el controlador de usuarios
     * mientras se completa la migración hacia UsuarioDAO.
     */
    private void registrarUsuario() {

        RegistroUsuarios registro =
                new RegistroUsuarios(
                        this,
                        usuarioDAO
                );

        registro.setVisible(true);
    }

    /**
     * Solicita confirmación antes de cerrar la aplicación.
     *
     * Si el usuario confirma, finaliza la ejecución
     * de la aplicación.
     */
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