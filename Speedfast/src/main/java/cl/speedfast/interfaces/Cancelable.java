package cl.speedfast.interfaces;

/**
 * Define el comportamiento que permite cancelar un pedido
 * dentro del sistema SpeedFast.
 *
 * @author Cristian Contreras
 * @version 1.0
 */
public interface Cancelable {

    /**
     * Cancela un pedido.
     */
    void cancelar();
}