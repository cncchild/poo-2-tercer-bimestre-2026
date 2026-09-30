package cl.speedfast.model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Representa una entrega asociada a un pedido
 * y a un repartidor.
 *
 * Contiene la información de fecha y hora
 * en que se registra la entrega.
 *
 * @author Cristian Contreras
 * @version 1.0
 */
public class Entrega {

    private int id;
    private int idPedido;
    private int idRepartidor;
    private LocalDate fecha;
    private LocalTime hora;

    /**
     * Constructor de la clase Entrega.
     *
     * @param id identificador de la entrega
     * @param idPedido identificador del pedido asociado
     * @param idRepartidor identificador del repartidor asignado
     * @param fecha fecha de la entrega
     * @param hora hora de la entrega
     */
    public Entrega(
            int id,
            int idPedido,
            int idRepartidor,
            LocalDate fecha,
            LocalTime hora) {

        this.id = id;
        this.idPedido = idPedido;
        this.idRepartidor = idRepartidor;
        this.fecha = fecha;
        this.hora = hora;
    }

    /**
     * Obtiene el identificador de la entrega.
     *
     * @return identificador de la entrega
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene el identificador del pedido asociado.
     *
     * @return identificador del pedido
     */
    public int getIdPedido() {
        return idPedido;
    }

    /**
     * Obtiene el identificador del repartidor asignado.
     *
     * @return identificador del repartidor
     */
    public int getIdRepartidor() {
        return idRepartidor;
    }

    /**
     * Obtiene la fecha de la entrega.
     *
     * @return fecha de la entrega
     */
    public LocalDate getFecha() {
        return fecha;
    }

    /**
     * Obtiene la hora de la entrega.
     *
     * @return hora de la entrega
     */
    public LocalTime getHora() {
        return hora;
    }

    /**
     * Modifica el identificador del pedido asociado.
     *
     * @param idPedido nuevo identificador del pedido
     */
    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    /**
     * Modifica el identificador del repartidor asignado.
     *
     * @param idRepartidor nuevo identificador del repartidor
     */
    public void setIdRepartidor(int idRepartidor) {
        this.idRepartidor = idRepartidor;
    }

    /**
     * Modifica la fecha de la entrega.
     *
     * @param fecha nueva fecha de la entrega
     */
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    /**
     * Modifica la hora de la entrega.
     *
     * @param hora nueva hora de la entrega
     */
    public void setHora(LocalTime hora) {
        this.hora = hora;
    }
}