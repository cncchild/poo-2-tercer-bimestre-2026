package cl.speedfast.model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Representa una entrega asociada a un pedido
 * y a un repartidor.
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

    public int getId() {
        return id;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public int getIdRepartidor() {
        return idRepartidor;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHora() {
        return hora;
    }
}