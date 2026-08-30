package cl.speedfast.data;

import cl.speedfast.model.Pedido;
import cl.speedfast.model.PedidoComida;
import cl.speedfast.model.PedidoEncomienda;
import cl.speedfast.model.PedidoExpress;

/**
 * Gestiona las operaciones principales relacionadas con los pedidos
 * de la aplicación SpeedFast.
 *
 * Permite registrar pedidos según su tipo, cancelar pedidos
 * y consultar el historial de pedidos registrados.
 *
 * @author Cristian Contreras
 * @version 1.0
 */
public class GestorPedidos {

    private HistorialPedidos historialPedidos;

    /**
     * Constructor de la clase GestorPedidos.
     *
     * Inicializa el gestor encargado de almacenar
     * y administrar el historial de pedidos.
     */
    public GestorPedidos() {
        historialPedidos = new HistorialPedidos();
    }

    /**
     * Registra un nuevo pedido según la opción seleccionada.
     *
     * Crea una instancia de la subclase correspondiente,
     * la agrega al historial y ejecuta el procesamiento del pedido.
     *
     * @param opcion tipo de pedido seleccionado
     * @param id identificador del pedido
     * @param direccion dirección de entrega
     * @param distancia distancia de entrega en kilómetros
     */
    public void registrarPedido(
            int opcion,
            int id,
            String direccion,
            double distancia) {

        Pedido pedido = seleccionarPedido(
                opcion,
                id,
                direccion,
                distancia
        );

        if (pedido == null) {

            System.out.println(
                    "[ERROR] Tipo de pedido no válido."
            );

            return;
        }

        // Guardar pedido en el historial
        historialPedidos.agregarPedido(pedido);

        mostrarResultado(pedido);
    }

    /**
     * Crea un pedido según la opción seleccionada.
     *
     * Utiliza polimorfismo para retornar una instancia
     * de PedidoComida, PedidoEncomienda o PedidoExpress.
     *
     * @param opcion tipo de pedido seleccionado
     * @param id identificador del pedido
     * @param direccion dirección de entrega
     * @param distancia distancia de entrega en kilómetros
     * @return pedido creado o null si la opción no es válida
     */
    private Pedido seleccionarPedido(
            int opcion,
            int id,
            String direccion,
            double distancia) {

        switch (opcion) {

            case 1:
                return new PedidoComida(
                        id,
                        direccion,
                        distancia
                );

            case 2:
                return new PedidoEncomienda(
                        id,
                        direccion,
                        distancia
                );

            case 3:
                return new PedidoExpress(
                        id,
                        direccion,
                        distancia
                );

            default:
                return null;
        }
    }

    /**
     * Procesa el pedido utilizando el Template Method
     * definido en la clase abstracta Pedido.
     *
     * @param pedido pedido que será procesado
     */
    private void mostrarResultado(Pedido pedido) {

        // Template Method
        pedido.procesarPedido();
    }

    /**
     * Cancela un pedido registrado mediante su identificador.
     *
     * @param id identificador del pedido que se desea cancelar
     */
    public void cancelarPedido(int id) {

        Pedido pedido = historialPedidos.buscarPedido(id);

        if (pedido == null) {

            System.out.println(
                    "[ERROR] No existe un pedido con ese ID."
            );

            return;
        }

        pedido.cancelar();
    }

    /**
     * Muestra el historial de pedidos registrados.
     */
    public void mostrarHistorial() {

        historialPedidos.mostrarHistorial();
    }

    public void buscarPedido(int id) {

        Pedido pedido = historialPedidos.buscarPedido(id);

        if (pedido == null) {

            System.out.println(
                    "[ERROR] No existe un pedido con ese ID."
            );

            return;
        }

        System.out.println("\n==============================");
        System.out.println("        PEDIDO ENCONTRADO");
        System.out.println("==============================");

        pedido.mostrarResumen();

        System.out.println(
                "Repartidor: " + pedido.getRepartidor()
        );
        System.out.println(
                "Estado: " + pedido.getEstado()
        );

        System.out.println("==============================");
    }
    public void cambiarEnRuta(int id) {

        Pedido pedido = historialPedidos.buscarPedido(id);

        if (pedido == null) {

            System.out.println(
                    "[ERROR] No existe un pedido con ese ID."
            );

            return;
        }

        pedido.marcarEnRuta();
    }
    
    public void entregarPedido(int id) {

        Pedido pedido = historialPedidos.buscarPedido(id);

        if (pedido == null) {

            System.out.println(
                    "[ERROR] No existe un pedido con ese ID."
            );

            return;
        }

        pedido.marcarEntregado();
    }
}