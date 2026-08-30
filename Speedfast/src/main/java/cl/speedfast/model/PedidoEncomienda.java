package cl.speedfast.model;

/**
 * Representa un pedido de encomienda dentro del sistema SpeedFast.
 *
 * Hereda los atributos y comportamientos comunes de la clase
 * abstracta Pedido y define reglas específicas para la asignación
 * del repartidor y el cálculo del tiempo de entrega.
 *
 * @author Cristian Contreras
 * @version 1.0
 */
public class PedidoEncomienda extends Pedido {

    /**
     * Constructor de PedidoEncomienda.
     *
     * @param idPedido identificador del pedido
     * @param direccionEntrega dirección donde se realizará la entrega
     * @param distanaciaKm distancia de entrega en kilómetros
     */
    public PedidoEncomienda(
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
        return "Pedido Encomienda";
    }

    /**
     * Asigna automáticamente un repartidor según la distancia
     * del pedido.
     *
     * Si la distancia es menor o igual a 10 kilómetros,
     * se asigna un repartidor para recorridos normales.
     * Para distancias mayores, se asigna un repartidor para
     * recorridos de mayor distancia.
     */
    @Override
    public void asignarRepartidor() {

        if (getDistanciaKm() <= 10) {

            setRepartidor("Andres Beltran");

            System.out.println(
                    "Repartidor asignado: "
                            + getRepartidor()
            );

        } else {

            setRepartidor("Camilo Apablaza");

            System.out.println(
                    "Repartidor asignado: "
                            + getRepartidor()
            );
        }
    }

    /**
     * Calcula el tiempo estimado de entrega para un pedido
     * de encomienda.
     *
     * La fórmula considera un tiempo base de 20 minutos
     * más 1.5 minutos por cada kilómetro de distancia.
     *
     * @return tiempo estimado de entrega en minutos
     */
    @Override
    public int calcularTiempoEntrega() {

        return (int) Math.round(
                20 + 1.5 * getDistanciaKm()
        );
    }
}