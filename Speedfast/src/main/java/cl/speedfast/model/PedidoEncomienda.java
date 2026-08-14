package cl.speedfast.model;

/**
 * Representa un pedido de encomienda.
 *
 * Hereda los atributos y métodos de la clase Pedido
 * y sobrescribe la asignación del repartidor.
 */
public class PedidoEncomienda extends Pedido {

    public PedidoEncomienda(int idPedido, String direccionEntrega, String tipoPedido) {
        super(idPedido, direccionEntrega, tipoPedido);
    }

    /**
     * Sobrescribe el método de la clase Pedido.
     * Las encomiendas requieren validar el peso
     * y el embalaje antes de asignar el repartidor.
     */
    @Override
    public void asignarRepartidor() {
        System.out.println("==========================");
        System.out.println("[Pedido Encomienda]");
        System.out.println("#" + getIdPedido());
        System.out.println("Dirección: " + getDireccionEntrega());
        System.out.println("Tipo de pedido: " + getTipoPedido());
        System.out.println("Asignando repartidor..." );
        System.out.println("Validando peso y embalaje... OK");

    }
    /**
     * Sobrescribe la versión sobrecargada del método.
     * Recibe el nombre del repartidor y realiza las
     * validaciones propias de una encomienda.
     *
     * @param nombre nombre del repartidor asignado
     */


    @Override
    public void asignarRepartidor(String nombre) {
        System.out.println("Pedido #" + getIdPedido() + " asignado a " + nombre);
    }

}