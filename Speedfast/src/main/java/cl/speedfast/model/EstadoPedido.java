package cl.speedfast.model;

/**
 * Representa los estados posibles de un pedido
 * dentro del sistema SpeedFast.
 *
 * @author Cristian Contreras
 * @version 1.0
 */
public enum EstadoPedido {

    /**
     * El pedido ha sido registrado y está pendiente
     * de iniciar su proceso de entrega.
     */
    PENDIENTE,

    /**
     * El pedido ha sido asignado a un repartidor
     * y se encuentra en proceso de entrega.
     */
    EN_REPARTO,

    /**
     * El pedido ha sido entregado correctamente
     * al destinatario.
     */
    ENTREGADO,

    /**
     * El pedido ha sido cancelado y no continuará
     * con el proceso de entrega.
     */
    CANCELADO
}