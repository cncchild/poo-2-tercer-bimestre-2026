package cl.speedfast.data;

import cl.speedfast.model.EstadoPedido;
import cl.speedfast.model.Pedido;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa la zona de carga compartida de SpeedFast.
 *
 * Permite agregar y retirar pedidos de forma segura
 * cuando varios repartidores trabajan simultáneamente.
 *
 * @author Cristian Contreras
 * @version 1.0
 */
public class ZonaDeCarga {

    private List<Pedido> pedidos;

    /**
     * Constructor de la zona de carga.
     */
    public ZonaDeCarga() {
        pedidos = new ArrayList<>();
    }

    /**
     * Agrega un pedido a la zona de carga.
     *
     * @param pedido pedido que será agregado
     */
    public synchronized void agregarPedido(Pedido pedido) {

        pedidos.add(pedido);

        System.out.println(
                "Pedido #" + pedido.getIdPedido()
                        + " agregado. Destino: "
                        + pedido.getDireccionEntrega()
        );
    }

    /**
     * Retira un pedido pendiente de forma segura.
     *
     * El método synchronized garantiza que solamente
     * un repartidor pueda retirar un pedido a la vez.
     *
     * @return pedido retirado o null si no existen pedidos pendientes
     */
    public synchronized Pedido retirarPedido() {

        for (int i = 0; i < pedidos.size(); i++) {

            Pedido pedido = pedidos.get(i);

            if (pedido.getEstado() == EstadoPedido.PENDIENTE) {

                pedidos.remove(i);

                return pedido;
            }
        }

        return null;
    }

    /**
     * elimina un pedido
     * @param id
     */
    public synchronized void eliminarPedido(int id) {

        for (int i = 0; i < pedidos.size(); i++) {

            Pedido pedido = pedidos.get(i);

            if (pedido.getIdPedido() == id) {
                pedidos.remove(i);

                System.out.println(
                        "Pedido #" + id
                                + " eliminado de la zona de carga."
                );

                return;
            }
        }
    }
    /**
     * Indica si la zona de carga está vacía.
     *
     * @return true si no quedan pedidos
     */
    public synchronized boolean estaVacia() {
        return pedidos.isEmpty();
    }
    /**
     * Muestra los pedidos que se encuentran actualmente
     * en la zona de carga.
     */
    public synchronized void mostrarPedidos() {

        if (pedidos.isEmpty()) {
            System.out.println("La zona de carga está vacía.");
            return;
        }

        for (Pedido pedido : pedidos) {

            System.out.println(
                    "Pedido #" + pedido.getIdPedido()
                            + " - Destino: "
                            + pedido.getDireccionEntrega()
                            + " - Estado: "
                            + pedido.getEstado()
            );
        }
    }
}
