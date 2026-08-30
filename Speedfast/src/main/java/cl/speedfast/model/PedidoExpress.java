package cl.speedfast.model;

/**
 * Representa un pedido de tipo Express dentro del sistema SpeedFast.
 *
 * Hereda los atributos y comportamientos comunes de la clase
 * abstracta Pedido y define reglas específicas para la asignación
 * del repartidor y el cálculo del tiempo de entrega.
 *
 * @author Cristian Contreras
 * @version 1.0
 */
public class PedidoExpress extends Pedido {

    /**
     * Constructor de PedidoExpress.
     *
     * @param idPedido identificador del pedido
     * @param direccionEntrega dirección donde se realizará la entrega
     * @param distanaciaKm distancia de entrega en kilómetros
     */
    public PedidoExpress(
            int idPedido,
            String direccionEntrega,
            double distanaciaKm) {

        super(idPedido, direccionEntrega, distanaciaKm);
    }

    /**
     * Obtiene el tipo de pedido.
     *
     * @return nombre del tipo de pedido
     */
    @Override
    public String obtenerTipoPedido() {
        return "Pedido Express";
    }

    /**
     * Asigna automáticamente un repartidor para el pedido Express.
     *
     * En este tipo de pedido se asigna directamente un repartidor
     * específico para realizar la entrega.
     */
    @Override
    public void asignarRepartidor() {

        setRepartidor("Cristian Contreras");

        System.out.println(
                "Repartidor asignado Express: "
                        + getRepartidor()
        );
    }

    /**
     * Calcula el tiempo estimado de entrega para un pedido Express.
     *
     * Se establece un tiempo base de 10 minutos. Si la distancia
     * supera los 5 kilómetros, se agregan 5 minutos adicionales.
     *
     * @return tiempo estimado de entrega en minutos
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