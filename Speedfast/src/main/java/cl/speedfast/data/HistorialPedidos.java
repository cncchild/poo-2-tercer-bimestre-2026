package cl.speedfast.data;

import java.util.ArrayList;
import java.util.List;

import cl.speedfast.model.Pedido;

/**
 * Gestiona el historial de pedidos registrados en SpeedFast.
 *
 * Permite agregar pedidos, buscar un pedido por su identificador
 * y mostrar el historial de entregas realizadas.
 *
 * @author Cristian Contreras
 * @version 1.0
 */
public class HistorialPedidos {

    private List<Pedido> historial;

    /**
     * Constructor de la clase HistorialPedidos.
     *
     * Inicializa la lista que almacenará los pedidos registrados.
     */
    public HistorialPedidos() {
        historial = new ArrayList<>();
    }

    /**
     * Agrega un pedido al historial.
     *
     * @param pedido pedido que será agregado al historial
     */
    public void agregarPedido(Pedido pedido) {
        historial.add(pedido);
    }

    /**
     * Busca un pedido en el historial mediante su identificador.
     *
     * @param id identificador del pedido que se desea buscar
     * @return el pedido encontrado o null si no existe
     */
    public Pedido buscarPedido(int id) {

        for (Pedido pedido : historial) {

            if (pedido.getIdPedido() == id) {
                return pedido;
            }
        }

        return null;
    }

    /**
     * Muestra en consola el historial de pedidos registrados,
     * indicando el tipo de pedido, su identificador y el repartidor asignado.
     */
    public void mostrarHistorial() {

        System.out.println("\n==============================");
        System.out.println("     HISTORIAL DE PEDIDOS");
        System.out.println("==============================");

        if (historial.isEmpty()) {

            System.out.println(
                    "No existen pedidos registrados."
            );

            return;
        }

        for (Pedido pedido : historial) {

            System.out.println(
                    pedido.obtenerTipoPedido()
                            + " #"
                            + pedido.getIdPedido()
                            + " - "
                            + pedido.getEstado()
                            + " - por "
                            + pedido.getRepartidor()
            );
        }

        System.out.println("==============================");
    }
}