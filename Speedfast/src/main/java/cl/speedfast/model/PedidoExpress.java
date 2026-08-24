package cl.speedfast.model;

/**
 * Representa un pedido de tipo Compra Express.
 *
 * Hereda de la clase Pedido y sobrescribe los métodos
 * asignarRepartidor() para aplicar la lógica específica
 * de este tipo de servicio.
 */
public class PedidoExpress extends Pedido {

    public PedidoExpress(int idPedido, String direccionEntrega, double distanaciaKm) {
        super(idPedido, direccionEntrega, distanaciaKm);
    }

    /**
     * Sobrescribe la versión sobrecargada del método.
     * Recibe el nombre del repartidor y realiza las
     * validaciones propias de una compra Express.
     *
     */



    @Override
    public int calcularTiempoEntrega() {

        int tiempo = 10;

        if (getDistanciaKm() > 5) {
            tiempo += 5;
        }

        return tiempo;
    }
}
