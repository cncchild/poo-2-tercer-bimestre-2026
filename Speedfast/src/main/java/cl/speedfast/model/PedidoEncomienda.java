package cl.speedfast.model;

/**
 * Representa un pedido de encomienda.
 *
 * Hereda los atributos y métodos de la clase Pedido
 * y sobrescribe la asignación del repartidor.
 */
public class PedidoEncomienda extends Pedido {

    public PedidoEncomienda(int idPedido, String direccionEntrega, double distanaciaKm) {
        super(idPedido, direccionEntrega, distanaciaKm);
    }

    /**
     * Sobrescribe la versión sobrecargada del método.
     */

    @Override
    public int calcularTiempoEntrega() {

        return (int) Math.round(
                20 + 1.5 * getDistanciaKm()
        );
    }

}