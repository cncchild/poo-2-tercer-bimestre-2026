package cl.bibliotecaEFT.vista;

import cl.bibliotecaEFT.controlador.EstudianteController;
import cl.bibliotecaEFT.dao.EstudianteDAO;
import cl.bibliotecaEFT.modelo.Estudiante;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana para editar un estudiante registrado.
 *
 * @author Cristian Contreras
 * @version 1.0
 */
public class EditarEstudiante extends JDialog {

    private final Estudiante estudiante;

    private JTextField txtNombre;
    private JTextField txtRut;
    private JTextField txtCurso;
    private JTextField txtCorreo;

    private final EstudianteController estudianteController;

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

    private void inicializarVentana(JFrame ventana) {

        setTitle(
                "BibliotecaEFT - Editar estudiante"
        );

        setSize(450, 300);
        setLocationRelativeTo(ventana);
        setModal(true);
        setResizable(false);
    }

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
