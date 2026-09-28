package cl.speedfast.vista;

import cl.speedfast.dao.EntregaDAO;
import cl.speedfast.dao.PedidoDAO;
import cl.speedfast.dao.RepartidorDAO;
import cl.speedfast.controlador.ControladorUsuarios;
import javax.swing.*;

public class Home extends JFrame {

    private JPanel Home;
    private JLabel txtHomeTitulo;
    private JButton btnGestionarPedidos;
    private JButton BtnCerrarSesion;
    private JButton btnAdministrarUsuarios;

    private final String nombreUsuario;
    private final String rol;

    private final PedidoDAO pedidoDAO;
    private final RepartidorDAO repartidorDAO;
    private final EntregaDAO entregaDAO;
    private final ControladorUsuarios controladorUsuarios;
    

    public Home(
            String nombreUsuario,
            String rol,
            ControladorUsuarios controladorUsuarios,
            PedidoDAO pedidoDAO,
            RepartidorDAO repartidorDAO,
            EntregaDAO entregaDAO) {

        this.nombreUsuario = nombreUsuario;
        this.rol = rol;
        this.controladorUsuarios = controladorUsuarios;
        this.pedidoDAO = pedidoDAO;
        this.repartidorDAO = repartidorDAO;
        this.entregaDAO = entregaDAO;

        setTitle("SpeedFast - Inicio");
        setContentPane(Home);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        txtHomeTitulo.setText("Bienvenido, " + nombreUsuario);

        btnGestionarPedidos.addActionListener(e -> gestionarPedidos());

        btnAdministrarUsuarios.addActionListener(e -> administrarUsuarios()
        );

        BtnCerrarSesion.addActionListener(e -> cerrarSesion());
    }

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
    private void administrarUsuarios() {

        ListaUsuarios listaUsuarios =
                new ListaUsuarios(
                        this,
                        rol,
                        controladorUsuarios
                );

        listaUsuarios.setVisible(true);
    }

    private void cerrarSesion() {

        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Deseas cerrar sesión?",
                "Cerrar sesión",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (respuesta == JOptionPane.YES_OPTION) {
            System.out.println(
                    "Controlador usuarios: "
                            + controladorUsuarios
            );
            Login login = new Login(
                    controladorUsuarios,
                    pedidoDAO,
                    repartidorDAO,
                    entregaDAO
            );

            login.setVisible(true);

            this.dispose();
        }
    }
}