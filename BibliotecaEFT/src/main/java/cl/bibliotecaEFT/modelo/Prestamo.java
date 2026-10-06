package cl.bibliotecaEFT.modelo;

import java.time.LocalDate;

/**
 * Representa un préstamo de un libro realizado por un estudiante.
 *
 * @author Cristian Contreras Child
 * @version 1.0
 */
public class Prestamo {

    private int id;
    private int idEstudiante;
    private int idLibro;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
    private boolean devuelto;

    /**
     * Constructor vacío del préstamo.
     */
    public Prestamo() {
    }

    /**
     * Constructor que permite crear un préstamo con todos sus datos.
     *
     * @param id identificador del préstamo.
     * @param idEstudiante identificador del estudiante que realiza el préstamo.
     * @param idLibro identificador del libro prestado.
     * @param fechaPrestamo fecha en que se realiza el préstamo.
     * @param fechaDevolucion fecha establecida para la devolución.
     * @param devuelto indica si el libro ya fue devuelto.
     */
    public Prestamo(int id, int idEstudiante, int idLibro,
                    LocalDate fechaPrestamo, LocalDate fechaDevolucion,
                    boolean devuelto) {
        this.id = id;
        this.idEstudiante = idEstudiante;
        this.idLibro = idLibro;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.devuelto = devuelto;
    }

    /**
     * Obtiene el identificador del préstamo.
     *
     * @return identificador del préstamo.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador del préstamo.
     *
     * @param id identificador del préstamo.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el identificador del estudiante.
     *
     * @return identificador del estudiante.
     */
    public int getIdEstudiante() {
        return idEstudiante;
    }

    /**
     * Establece el identificador del estudiante.
     *
     * @param idEstudiante identificador del estudiante.
     */
    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    /**
     * Obtiene el identificador del libro.
     *
     * @return identificador del libro.
     */
    public int getIdLibro() {
        return idLibro;
    }

    /**
     * Establece el identificador del libro.
     *
     * @param idLibro identificador del libro.
     */
    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    /**
     * Obtiene la fecha en que se realizó el préstamo.
     *
     * @return fecha del préstamo.
     */
    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    /**
     * Establece la fecha en que se realizó el préstamo.
     *
     * @param fechaPrestamo fecha del préstamo.
     */
    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    /**
     * Obtiene la fecha establecida para la devolución.
     *
     * @return fecha de devolución.
     */
    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    /**
     * Establece la fecha de devolución.
     *
     * @param fechaDevolucion fecha de devolución.
     */
    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    /**
     * Indica si el libro ya fue devuelto.
     *
     * @return {@code true} si el libro fue devuelto;
     * {@code false} en caso contrario.
     */
    public boolean isDevuelto() {
        return devuelto;
    }

    /**
     * Establece el estado de devolución del préstamo.
     *
     * @param devuelto indica si el libro fue devuelto.
     */
    public void setDevuelto(boolean devuelto) {
        this.devuelto = devuelto;
    }
}