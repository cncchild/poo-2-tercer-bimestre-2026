package cl.speedfast.model;

/**
 * Representa un pedido de tipo Compra Express.
 *
 * Hereda de la clase Pedido y sobrescribe los métodos
 * asignarRepartidor() para aplicar la lógica específica
 * de este tipo de servicio.
 */
public class PedidoExpress extends Pedido {

    public PedidoExpress(int idPedido, String direccionEntrega, String tipoPedido) {
        super(idPedido, direccionEntrega, tipoPedido);
    }

    /**
     * Sobrescribe el método de la clase Pedido.
     * Los pedidos Express requieren asignar al repartidor
     * más cercano que tenga disponibilidad inmediata.
     */
    @Override
    public void asignarRepartidor() {
        System.out.println("==========================");
        System.out.println("[Pedido Express]");
        System.out.println("#" + getIdPedido());
        System.out.println("Dirección: " + getDireccionEntrega());
        System.out.println("Tipo de pedido: " + getTipoPedido());
        System.out.println("Asignando repartidor...");
        System.out.println(
                "Repartidor más cercano con disponibilidad inmediata encontrado."
        );
        System.out.println("==========================");
    }

    /**
     * Sobrescribe la versión sobrecargada del método.
     * Recibe el nombre del repartidor y realiza las
     * validaciones propias de una compra Express.
     *
     * @param nombre nombre del repartidor asignado
     */




    @Override
    public void asignarRepartidor(String nombre) {
        System.out.println(
                "Pedido #" + getIdPedido() + " asignado a " + nombre
        );
    }
}
