package cl.speedfast.data;

import java.util.ArrayList;
import java.util.List;

import cl.speedfast.model.Pedido;

/**
 * Gestiona el historial de pedidos registrados en SpeedFast.
 *
 * Permite agregar pedidos, buscar un pedido por su identificador
 * y consultar el historial de pedidos registrados.
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
     * Obtiene la lista de pedidos registrados en el historial.
     *
     * @return lista que contiene los pedidos registrados
     */
    public List<Pedido> getHistorial() {
        return historial;
    }

    /**
     * Mantiene el método de consulta del historial para compatibilidad
     * con las funcionalidades desarrolladas en semanas anteriores.
     *
     * La visualización actual de los pedidos se realiza mediante
     * la interfaz gráfica de Swing.
     */
    public void mostrarHistorial() {
        // La visualización se realiza actualmente mediante Swing.
    }
}