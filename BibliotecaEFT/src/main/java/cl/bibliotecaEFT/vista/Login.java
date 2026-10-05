package cl.bibliotecaEFT.vista;

import cl.bibliotecaEFT.controlador.UsuarioController;
import cl.bibliotecaEFT.dao.UsuarioDAO;
import cl.bibliotecaEFT.modelo.Usuario;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana de inicio de sesión del sistema de biblioteca.
 *
 * @author Cristian Contreras
 * @version 1.0
 */
public class Login extends JFrame {

    private JTextField txtRut;
    private JPasswordField txtContrasena;
    private JButton btnIngresar;

    private final UsuarioController usuarioController;

    /**
     * Constructor de la ventana de inicio de sesión.
     */
    public Login() {

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        usuarioController = new UsuarioController(usuarioDAO);

        inicializarVentana();
        inicializarComponentes();
    }

    /**
     * Configura las propiedades principales de la ventana.
     */
    private void inicializarVentana() {

        setTitle("BibliotecaEFT - Inicio de sesión");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    /**
     * Crea y organiza los componentes gráficos.
     */
    private void inicializarComponentes() {

        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel(
                "Sistema de Gestión de Biblioteca",
                SwingConstants.CENTER
        );

        JLabel lblRut = new JLabel("RUT:");

        JLabel lblContrasena = new JLabel("Contraseña:");

        txtRut = new JTextField(15);

        txtContrasena = new JPasswordField(15);

        btnIngresar = new JButton("Ingresar");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        panel.add(lblTitulo, gbc);

        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 1;

        panel.add(lblRut, gbc);

        gbc.gridx = 1;

        panel.add(txtRut, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;

        panel.add(lblContrasena, gbc);

        gbc.gridx = 1;

        panel.add(txtContrasena, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;

        panel.add(btnIngresar, gbc);

        btnIngresar.addActionListener(e -> iniciarSesion());

        add(panel);
    }

    /**
     * Valida las credenciales ingresadas por el usuario.
     */
    private void iniciarSesion() {

        String rut = txtRut.getText().trim();

        String contrasena = new String(
                txtContrasena.getPassword()
        );

        if (rut.isEmpty() || contrasena.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar RUT y contraseña.",
                    "Datos incompletos",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            Usuario usuario = usuarioController.buscarPorRut(rut);

            if (usuario == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Usuario no encontrado.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            if (!usuario.getContraseña().equals(contrasena)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Contraseña incorrecta.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            dispose();

            new Home(usuario).setVisible(true);

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al iniciar sesión:\n" + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}