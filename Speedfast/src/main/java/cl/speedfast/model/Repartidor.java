package cl.speedfast.model;

/**
 * Representa un repartidor registrado en SpeedFast.
 *
 * Esta clase corresponde a los datos almacenados
 * en la tabla repartidor de la base de datos.
 *
 * @author Cristian Contreras
 * @version 1.0
 */
public class Repartidor {

    private int id;
    private String nombre;

    /**
     * Constructor completo de la clase Repartidor.
     *
     * @param id identificador del repartidor
     * @param nombre nombre del repartidor
     */
    public Repartidor(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    /**
     * Obtiene el identificador del repartidor.
     *
     * @return identificador del repartidor
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene el nombre del repartidor.
     *
     * @return nombre del repartidor
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Modifica el nombre del repartidor.
     *
     * @param nombre nuevo nombre del repartidor
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene una representación textual del repartidor.
     *
     * @return nombre del repartidor
     */
    @Override
    public String toString() {
        return nombre;
    }
}