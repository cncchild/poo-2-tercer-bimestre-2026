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
 * Utiliza métodos sincronizados para controlar el acceso
 * concurrente a la lista de pedidos.
 *
 * @author Cristian Contreras
 * @version 1.0
 */
public class ZonaDeCarga {

    private List<Pedido> pedidos;

    /**
     * Constructor de la zona de carga.
     *
     * Inicializa la lista que almacenará los pedidos.
     */
    public ZonaDeCarga() {
        pedidos = new ArrayList<>();
    }

    /**
     * Agrega un pedido a la zona de carga.
     *
     * El método synchronized garantiza que el acceso
     * a la lista sea seguro cuando existen varios hilos.
     *
     * @param pedido pedido que será agregado
     */
    public synchronized void agregarPedido(Pedido pedido) {
        pedidos.add(pedido);
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
     * Elimina un pedido de la zona de carga mediante
     * su identificador.
     *
     * El método synchronized garantiza un acceso seguro
     * a la lista cuando existen varios hilos.
     *
     * @param id identificador del pedido que se desea eliminar
     */
    public synchronized void eliminarPedido(int id) {

        for (int i = 0; i < pedidos.size(); i++) {

            Pedido pedido = pedidos.get(i);

            if (pedido.getIdPedido() == id) {
                pedidos.remove(i);
                return;
            }
        }
    }

    /**
     * Indica si la zona de carga está vacía.
     *
     * @return true si no existen pedidos en la zona de carga,
     *         false en caso contrario
     */
    public synchronized boolean estaVacia() {
        return pedidos.isEmpty();
    }

    /**
     * Mantiene el método utilizado por las funcionalidades
     * desarrolladas en semanas anteriores.
     *
     * La visualización actual de los pedidos se realiza
     * mediante la interfaz gráfica de Swing.
     */
    public synchronized void mostrarPedidos() {
        // La visualización se realiza actualmente mediante Swing.
    }
}