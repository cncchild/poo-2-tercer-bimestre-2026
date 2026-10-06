package cl.bibliotecaEFT.vista;

import cl.bibliotecaEFT.controlador.EstudianteController;
import cl.bibliotecaEFT.dao.EstudianteDAO;
import cl.bibliotecaEFT.modelo.Estudiante;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana modal que permite editar los datos de un estudiante
 * previamente registrado en el sistema.
 *
 * @author Cristian Contreras Child
 * @version 1.0
 */
public class EditarEstudiante extends JDialog {

    private final Estudiante estudiante;

    private JTextField txtNombre;
    private JTextField txtRut;
    private JTextField txtCurso;
    private JTextField txtCorreo;

    private final EstudianteController estudianteController;

    /**
     * Constructor de la ventana para editar un estudiante.
     *
     * @param ventana ventana principal desde donde se abre el diálogo.
     * @param estudiante estudiante cuyos datos serán modificados.
     */
    public EditarEstudiante(
            JFrame ventana,
            Estudiante estudiante) {

        this.estudiante = estudiante;

        EstudianteDAO estudianteDAO =
                new EstudianteDAO();

        estudianteController =
                new EstudianteController(
                        estudianteDAO
                );

        inicializarVentana(ventana);
        inicializarComponentes();
        cargarDatos();
    }

    /**
     * Configura las propiedades principales de la ventana,
     * incluyendo título, tamaño, posición, modalidad y redimensionamiento.
     *
     * @param ventana ventana principal utilizada para posicionar el diálogo.
     */
    private void inicializarVentana(JFrame ventana) {

        setTitle(
                "BibliotecaEFT - Editar estudiante"
        );

        setSize(450, 300);
        setLocationRelativeTo(ventana);
        setModal(true);
        setResizable(false);
    }

    /**
     * Inicializa y organiza los componentes gráficos de la ventana,
     * incluyendo los campos de edición y los botones de acción.
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
                new JButton("Guardar cambios");

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
                e -> actualizarEstudiante()
        );

        btnCancelar.addActionListener(
                e -> cancelar()
        );

        add(panel);
    }

    /**
     * Carga en los campos del formulario los datos actuales
     * del estudiante seleccionado para su edición.
     */
    private void cargarDatos() {

        txtNombre.setText(
                estudiante.getNombre()
        );

        txtRut.setText(
                estudiante.getRut()
        );

        txtCurso.setText(
                estudiante.getCurso()
        );

        txtCorreo.setText(
                estudiante.getCorreo()
        );
    }

    /**
     * Valida los datos ingresados y actualiza la información
     * del estudiante mediante el controlador correspondiente.
     */
    private void actualizarEstudiante() {

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

            estudiante.setNombre(nombre);
            estudiante.setRut(rut);
            estudiante.setCurso(curso);
            estudiante.setCorreo(correo);

            estudianteController.actualizarEstudiante(
                    estudiante
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Estudiante actualizado correctamente.",
                    "Actualización exitosa",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al actualizar el estudiante:\n"
                            + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Solicita confirmación al usuario antes de cancelar
     * la edición y cerrar la ventana sin guardar los cambios.
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