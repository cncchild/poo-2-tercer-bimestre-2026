package cl.speedfast.vista;

import cl.speedfast.dao.EntregaDAO;
import cl.speedfast.dao.PedidoDAO;
import cl.speedfast.dao.RepartidorDAO;
import cl.speedfast.dao.UsuarioDAO;

import javax.swing.*;

/**
 * Ventana principal del sistema SpeedFast.
 *
 * Permite al usuario acceder a las distintas funcionalidades
 * de la aplicación según su rol, incluyendo la gestión de
 * pedidos, usuarios, repartidores y entregas.
 *
 * También permite cerrar la sesión actual y regresar
 * a la ventana de Login.
 *
 * @author Cristian Contreras
 * @version 1.0
 */
public class Home extends JFrame {

    private JPanel Home;
    private JLabel txtHomeTitulo;
    private JButton btnGestionarPedidos;
    private JButton BtnCerrarSesion;
    private JButton btnAdministrarUsuarios;
    private JButton btnAdministrarRepartidores;
    private JButton btnAdministrarEntrega;

    private final String nombreUsuario;
    private final String rol;

    private final PedidoDAO pedidoDAO;
    private final RepartidorDAO repartidorDAO;
    private final EntregaDAO entregaDAO;
    private final UsuarioDAO usuarioDAO;

    /**
     * Constructor de la ventana principal.
     *
     * Recibe los datos del usuario autenticado y los DAO
     * necesarios para acceder a las funcionalidades
     * del sistema.
     *
     * @param nombreUsuario nombre del usuario autenticado
     * @param rol rol asignado al usuario
     * @param usuarioDAO DAO encargado de gestionar los usuarios
     * @param pedidoDAO DAO encargado de gestionar los pedidos
     * @param repartidorDAO DAO encargado de gestionar los repartidores
     * @param entregaDAO DAO encargado de gestionar las entregas
     */
    public Home(
            String nombreUsuario,
            String rol,
            UsuarioDAO usuarioDAO,
            PedidoDAO pedidoDAO,
            RepartidorDAO repartidorDAO,
            EntregaDAO entregaDAO) {

        this.nombreUsuario = nombreUsuario;
        this.rol = rol;
        this.usuarioDAO = usuarioDAO;
        this.pedidoDAO = pedidoDAO;
        this.repartidorDAO = repartidorDAO;
        this.entregaDAO = entregaDAO;

        setTitle("SpeedFast - Inicio");
        setContentPane(Home);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        txtHomeTitulo.setText(
                "Bienvenido, " + nombreUsuario
        );

        btnGestionarPedidos.addActionListener(
                e -> gestionarPedidos()
        );

        btnAdministrarUsuarios.addActionListener(
                e -> administrarUsuarios()
        );

        btnAdministrarRepartidores.addActionListener(
                e -> administrarRepartidores()
        );

        btnAdministrarEntrega.addActionListener(
                e -> administrarEntregas()
        );

        BtnCerrarSesion.addActionListener(
                e -> cerrarSesion()
        );
    }

    /**
     * Abre la ventana de gestión de pedidos.
     *
     * Envía los DAO necesarios para permitir el registro,
     * edición, eliminación y gestión de entregas.
     */
    private void gestionarPedidos() {

        ListaPedidos listaPedidos =
                new ListaPedidos(
                        this,
                        rol,
                        pedidoDAO,
                        repartidorDAO,
                        entregaDAO
                );

        listaPedidos.setVisible(true);

        this.dispose();
    }

    /**
     * Abre la ventana de administración de usuarios.
     *
     * Permite consultar y gestionar los usuarios registrados
     * según los permisos correspondientes.
     */
    private void administrarUsuarios() {

        ListaUsuarios listaUsuarios =
                new ListaUsuarios(
                        this,
                        rol,
                        usuarioDAO
                );

        listaUsuarios.setVisible(true);
    }

    /**
     * Abre la ventana de administración de repartidores.
     */
    private void administrarRepartidores() {

        ListaRepartidores listaRepartidores =
                new ListaRepartidores(
                        repartidorDAO
                );

        listaRepartidores.setVisible(true);
    }

    /**
     * Abre la ventana de administración de entregas.
     */
    private void administrarEntregas() {

        ListaEntrega listaEntrega =
                new ListaEntrega(
                        entregaDAO
                );

        listaEntrega.setVisible(true);
    }

    /**
     * Solicita confirmación al usuario para cerrar la sesión.
     *
     * Si el usuario confirma, se vuelve a abrir la ventana
     * de Login y se cierra la ventana Home actual.
     */
    private void cerrarSesion() {

        int respuesta =
                JOptionPane.showConfirmDialog(
                        this,
                        "¿Deseas cerrar sesión?",
                        "Cerrar sesión",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

        if (respuesta == JOptionPane.YES_OPTION) {

            Login login = new Login(
                    usuarioDAO,
                    pedidoDAO,
                    repartidorDAO,
                    entregaDAO
            );

            login.setVisible(true);

            this.dispose();
        }
    }
}