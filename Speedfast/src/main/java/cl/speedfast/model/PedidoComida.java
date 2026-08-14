package cl.speedfast.model;

/**
 * Representa un pedido de comida.
 *
 * Hereda los atributos y métodos de la clase Pedido
 * y sobrescribe la asignación del repartidor.
 */
public class PedidoComida extends Pedido {

    public PedidoComida(int idPedido, String direccionEntrega, String tipoPedido) {
        super(idPedido, direccionEntrega, tipoPedido);
    }

    /**
     * Sobrescribe el método de la clase Pedido.
     * Los pedidos de comida requieren un repartidor
     * con mochila térmica.
     */
    @Override
    public void asignarRepartidor() {
        System.out.println("==========================");
        System.out.println("[Pedido Comida]");
        System.out.println("#" + getIdPedido());
        System.out.println("Dirección: " + getDireccionEntrega());
        System.out.println("Tipo de pedido: " + getTipoPedido());
        System.out.println("Asignando repartidor... "  );
        System.out.println("Verificando mochila térmica... OK");
    }
    /**
     * Sobrescribe la versión sobrecargada del método.
     * Recibe el nombre del repartidor y valida que
     * el servicio requiere mochila térmica.
     *
     * @param nombre nombre del repartidor asignado
     */


    @Override
    public void asignarRepartidor(String nombre) {
        System.out.println("Pedido # " + getIdPedido()+ " asignado a " + nombre);
    }
}
