package cl.speedfast.model;

/**
 * Representa un pedido de comida.
 *
 * Hereda los atributos y métodos de la clase Pedido
 * y sobrescribe la asignación del repartidor.
 */
public class PedidoComida extends Pedido {

    public PedidoComida(int idPedido, String direccionEntrega, double distanaciaKm) {
        super(idPedido, direccionEntrega, distanaciaKm);
    }

    /**
     * Sobrescribe la versión sobrecargada del método.

     */
    @Override
    public int calcularTiempoEntrega() {

        return (int) (15 + 2 * getDistanciaKm());
    }
}
