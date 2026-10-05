package cl.bibliotecaEFT.vista;

import cl.bibliotecaEFT.modelo.Usuario;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal del sistema de biblioteca.
 *
 * @author Cristian Contreras
 * @version 1.0
 */
public class Home extends JFrame {

    private final Usuario usuario;

    /**
     * Constructor de la ventana principal.
     *
     * @param usuario usuario que inició sesión
     */
    public Home(Usuario usuario) {

        this.usuario = usuario;

        inicializarVentana();
        inicializarComponentes();
    }

    /**
     * Configura las propiedades principales de la ventana.
     */
    private void inicializarVentana() {

        setTitle("BibliotecaEFT - Menú principal");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    /**
     * Crea y organiza los componentes gráficos.
     */
    private void inicializarComponentes() {

        JPanel panel =
                new JPanel(
                        new GridLayout(0, 1, 10, 10)
                );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 40, 20, 40
                )
        );

        JLabel lblBienvenida =
                new JLabel(
                        "Bienvenido/a, "
                                + usuario.getNombre(),
                        SwingConstants.CENTER
                );

        JLabel lblRol =
                new JLabel(
                        "Rol: "
                                + usuario.getRol(),
                        SwingConstants.CENTER
                );

        JButton btnCerrarSesion =
                new JButton("Cerrar sesión");

        panel.add(lblBienvenida);
        panel.add(lblRol);

        /*
         * Opciones disponibles según el rol.
         */
        if (usuario.getRol().equals("bibliotecario")) {

            JButton btnGestionarLibros =
                    new JButton("Gestionar libros");

            btnGestionarLibros.addActionListener(e -> {
                new GestionLibros().setVisible(true);
            });

            JButton btnGestionarEstudiantes =
                    new JButton("Gestionar estudiantes");

            btnGestionarEstudiantes.addActionListener(e -> {
                new GestionEstudiantes().setVisible(true);
            });

            JButton btnGestionarPrestamos =
                    new JButton("Gestionar préstamos");

            btnGestionarPrestamos.addActionListener(e -> {
                new GestionPrestamos().setVisible(true);
            });

            JButton btnReportes =
                    new JButton("Reportes");

            btnReportes.addActionListener(e -> {
                new Reportes().setVisible(true);
            });

            panel.add(btnGestionarLibros);
            panel.add(btnGestionarEstudiantes);
            panel.add(btnGestionarPrestamos);
            panel.add(btnReportes);

        } else if (usuario.getRol().equals("estudiante")) {

            JButton btnMisPrestamos =
                    new JButton("Mis préstamos");

            btnMisPrestamos.addActionListener(e -> {
                new MisPrestamos(usuario).setVisible(true);
            });

            panel.add(btnMisPrestamos);
        }

        panel.add(btnCerrarSesion);

        btnCerrarSesion.addActionListener(
                e -> cerrarSesion()
        );

        add(panel);
    }

    /**
     * Solicita confirmación antes de cerrar la sesión.
     */
    private void cerrarSesion() {

        int respuesta =
                JOptionPane.showConfirmDialog(
                        this,
                        "¿Está seguro de que desea cerrar sesión?",
                        "Cerrar sesión",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }

        dispose();

        new Login().setVisible(true);
    }
}
