package cl.bibliotecaEFT.vista;

import cl.bibliotecaEFT.controlador.LibroController;
import cl.bibliotecaEFT.dao.CategoriaDAO;
import cl.bibliotecaEFT.dao.LibroDAO;
import cl.bibliotecaEFT.modelo.Categoria;
import cl.bibliotecaEFT.modelo.Libro;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana modal que permite editar los datos de un libro
 * previamente registrado en el sistema.
 *
 * @author Cristian Contreras Child
 * @version 1.0
 */
public class EditarLibro extends JDialog {

    private final Libro libro;

    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JTextField txtIsbn;
    private JTextField txtEditorial;
    private JTextField txtStock;

    private JComboBox<Categoria> comboCategoria;

    private final LibroController libroController;

    /**
     * Constructor de la ventana de edición de libros.
     *
     * @param ventana ventana principal desde donde se abre el diálogo.
     * @param libro libro seleccionado cuyos datos serán modificados.
     */
    public EditarLibro(JFrame ventana, Libro libro) {

        this.libro = libro;

        LibroDAO libroDAO = new LibroDAO();
        CategoriaDAO categoriaDAO = new CategoriaDAO();

        libroController = new LibroController(
                libroDAO,
                categoriaDAO
        );

        inicializarVentana(ventana);
        inicializarComponentes();
        cargarDatos();
        cargarCategorias();
    }

    /**
     * Configura las propiedades principales de la ventana,
     * incluyendo título, tamaño, posición, modalidad
     * y comportamiento de redimensionamiento.
     *
     * @param ventana ventana principal utilizada para posicionar el diálogo.
     */
    private void inicializarVentana(JFrame ventana) {

        setTitle("BibliotecaEFT - Editar libro");
        setSize(450, 350);
        setLocationRelativeTo(ventana);
        setModal(true);
        setResizable(false);
    }

    /**
     * Inicializa y organiza los componentes gráficos de la ventana,
     * incluyendo los campos de edición, selector de categoría
     * y botones de acción.
     */
    private void inicializarComponentes() {

        JPanel panel = new JPanel(
                new GridLayout(7, 2, 10, 10)
        );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        15, 20, 15, 20
                )
        );

        JLabel lblTitulo = new JLabel("Título:");
        JLabel lblAutor = new JLabel("Autor:");
        JLabel lblIsbn = new JLabel("ISBN:");
        JLabel lblEditorial = new JLabel("Editorial:");
        JLabel lblStock = new JLabel("Stock:");
        JLabel lblCategoria = new JLabel("Categoría:");

        txtTitulo = new JTextField();
        txtAutor = new JTextField();
        txtIsbn = new JTextField();
        txtEditorial = new JTextField();
        txtStock = new JTextField();

        comboCategoria = new JComboBox<>();

        JButton btnGuardar = new JButton("Guardar cambios");
        JButton btnCancelar = new JButton("Cancelar");

        panel.add(lblTitulo);
        panel.add(txtTitulo);

        panel.add(lblAutor);
        panel.add(txtAutor);

        panel.add(lblIsbn);
        panel.add(txtIsbn);

        panel.add(lblEditorial);
        panel.add(txtEditorial);

        panel.add(lblStock);
        panel.add(txtStock);

        panel.add(lblCategoria);
        panel.add(comboCategoria);

        panel.add(btnGuardar);
        panel.add(btnCancelar);

        btnGuardar.addActionListener(
                e -> actualizarLibro()
        );

        btnCancelar.addActionListener(
                e -> cancelar()
        );

        add(panel);
    }

    /**
     * Carga en los campos del formulario los datos actuales
     * del libro seleccionado para su edición.
     */
    private void cargarDatos() {

        txtTitulo.setText(libro.getTitulo());
        txtAutor.setText(libro.getAutor());
        txtIsbn.setText(libro.getIsbn());
        txtEditorial.setText(libro.getEditorial());
        txtStock.setText(
                String.valueOf(libro.getStock())
        );
    }

    /**
     * Obtiene las categorías disponibles desde la base de datos
     * y selecciona automáticamente la categoría actual del libro.
     */
    private void cargarCategorias() {

        try {

            comboCategoria.removeAllItems();

            for (Categoria categoria :
                    libroController.listarCategorias()) {

                comboCategoria.addItem(categoria);

                if (categoria.getId() ==
                        libro.getIdCategoria()) {

                    comboCategoria.setSelectedItem(
                            categoria
                    );
                }
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al cargar las categorías:\n"
                            + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Valida los datos ingresados, actualiza la información
     * del libro seleccionado y guarda los cambios mediante
     * el controlador correspondiente.
     */
    private void actualizarLibro() {

        String titulo =
                txtTitulo.getText().trim();

        String autor =
                txtAutor.getText().trim();

        String isbn =
                txtIsbn.getText().trim();

        String editorial =
                txtEditorial.getText().trim();

        String stockTexto =
                txtStock.getText().trim();

        if (titulo.isEmpty()
                || autor.isEmpty()
                || isbn.isEmpty()
                || editorial.isEmpty()
                || stockTexto.isEmpty()
                || comboCategoria.getSelectedItem() == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe completar todos los campos.",
                    "Datos incompletos",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            int stock =
                    Integer.parseInt(stockTexto);

            if (stock < 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "El stock no puede ser negativo.",
                        "Dato inválido",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            Categoria categoria =
                    (Categoria)
                            comboCategoria.getSelectedItem();

            libro.setTitulo(titulo);
            libro.setAutor(autor);
            libro.setIsbn(isbn);
            libro.setEditorial(editorial);
            libro.setStock(stock);
            libro.setIdCategoria(
                    categoria.getId()
            );

            libroController.actualizarLibro(
                    libro
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Libro actualizado correctamente.",
                    "Actualización exitosa",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "El stock debe ser un número entero.",
                    "Dato inválido",
                    JOptionPane.WARNING_MESSAGE
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al actualizar el libro:\n"
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