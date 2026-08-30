package cl.speedfast.model;

/**
 * Representa un pedido de comida dentro del sistema SpeedFast.
 *
 * Hereda los atributos y comportamientos comunes de la clase
 * abstracta Pedido y define reglas específicas para la asignación
 * del repartidor y el cálculo del tiempo de entrega.
 *
 * @author Cristian Contreras
 * @version 1.0
 */
public class PedidoComida extends Pedido {

    /**
     * Constructor de PedidoComida.
     *
     * @param idPedido identificador del pedido
     * @param direccionEntrega dirección donde se realizará la entrega
     * @param distanaciaKm distancia de entrega en kilómetros
     */
    public PedidoComida(
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
        return "Pedido Comida";
    }

    /**
     * Asigna automáticamente un repartidor según la distancia
     * del pedido.
     *
     * Si la distancia es menor o igual a 5 kilómetros,
     * se asigna un repartidor local. Para distancias mayores,
     * se asigna un repartidor destinado a recorridos largos.
     */
    @Override
    public void asignarRepartidor() {

        if (getDistanciaKm() <= 5) {

            setRepartidor("Veronica Trigo");

            System.out.println(
                    "Repartidor local asignado: "
                            + getRepartidor()
            );

        } else {

            setRepartidor("Pedro Toloza");

            System.out.println(
                    "Repartidor local asignado de larga distancia: "
                            + getRepartidor()
            );
        }
    }

    /**
     * Calcula el tiempo estimado de entrega para un pedido
     * de comida.
     *
     * La fórmula considera un tiempo base de 15 minutos
     * más 2 minutos por cada kilómetro de distancia.
     *
     * @return tiempo estimado de entrega en minutos
     */
    @Override
    public int calcularTiempoEntrega() {

        return (int) (15 + 2 * getDistanciaKm());
    }
}