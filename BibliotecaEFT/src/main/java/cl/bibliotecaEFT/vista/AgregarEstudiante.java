package cl.bibliotecaEFT.vista;

import cl.bibliotecaEFT.controlador.EstudianteController;
import cl.bibliotecaEFT.dao.EstudianteDAO;
import cl.bibliotecaEFT.modelo.Estudiante;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana para registrar un nuevo estudiante.
 *
 * @author Cristian Contreras Child
 * @version 1.0
 */
public class AgregarEstudiante extends JDialog {

    private JTextField txtNombre;
    private JTextField txtRut;
    private JTextField txtCurso;
    private JTextField txtCorreo;

    private final EstudianteController estudianteController;

    /**
     * Constructor de la ventana de registro de estudiantes.
     *
     * @param ventana ventana principal utilizada para posicionar
     *                el diálogo.
     */
    public AgregarEstudiante(JFrame ventana) {

        EstudianteDAO estudianteDAO =
                new EstudianteDAO();

        estudianteController =
                new EstudianteController(
                        estudianteDAO
                );

        inicializarVentana(ventana);
        inicializarComponentes();
    }

    /**
     * Configura las propiedades principales de la ventana,
     * como título, tamaño, posición y modalidad.
     *
     * @param ventana ventana principal utilizada como referencia
     *                para posicionar el diálogo.
     */
    private void inicializarVentana(JFrame ventana) {

        setTitle("BibliotecaEFT - Agregar estudiante");
        setSize(450, 300);
        setLocationRelativeTo(ventana);
        setModal(true);
        setResizable(false);
    }

    /**
     * Inicializa los componentes gráficos de la ventana,
     * incluyendo campos de texto, etiquetas y botones.
     */
    private void inicializarComponentes() {

        JPanel panel = new JPanel(
                new GridLayout(5, 2, 10, 10)
        );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        15, 20, 15, 20
                )
        );

        JLabel lblNombre =
                new JLabel("Nombre:");

        JLabel lblRut =
                new JLabel("RUT:");

        JLabel lblCurso =
                new JLabel("Curso:");

        JLabel lblCorreo =
                new JLabel("Correo:");

        txtNombre =
                new JTextField();

        txtRut =
                new JTextField();

        txtCurso =
                new JTextField();

        txtCorreo =
                new JTextField();

        JButton btnGuardar =
                new JButton("Guardar");

        JButton btnCancelar =
                new JButton("Cancelar");

        panel.add(lblNombre);
        panel.add(txtNombre);

        panel.add(lblRut);
        panel.add(txtRut);

        panel.add(lblCurso);
        panel.add(txtCurso);

        panel.add(lblCorreo);
        panel.add(txtCorreo);

        panel.add(btnGuardar);
        panel.add(btnCancelar);

        btnGuardar.addActionListener(
                e -> guardarEstudiante()
        );

        btnCancelar.addActionListener(
                e -> cancelar()
        );

        add(panel);
    }

    /**
     * Valida los datos ingresados y registra un nuevo estudiante
     * mediante el controlador correspondiente.
     */
    private void guardarEstudiante() {

        String nombre =
                txtNombre.getText().trim();

        String rut =
                txtRut.getText().trim();

        String curso =
                txtCurso.getText().trim();

        String correo =
                txtCorreo.getText().trim();

        if (nombre.isEmpty()
                || rut.isEmpty()
                || curso.isEmpty()
                || correo.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe completar todos los campos.",
                    "Datos incompletos",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            Estudiante estudiante =
                    new Estudiante(
                            0,
                            nombre,
                            rut,
                            curso,
                            correo
                    );

            estudianteController.registrarEstudiante(
                    estudiante
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Estudiante registrado correctamente.",
                    "Registro exitoso",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al registrar el estudiante:\n"
                            + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Solicita confirmación al usuario antes de cancelar
     * el registro y cerrar la ventana.
     */
    private void cancelar() {

        int respuesta =
                JOptionPane.showConfirmDialog(
                        this,
                        "¿Está seguro de que desea cancelar?\n"
                                + "Los cambios no se guardarán.",
                        "Confirmar cancelación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }

        dispose();
    }
}