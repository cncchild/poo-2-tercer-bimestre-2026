package cl.speedfast.data;

import cl.speedfast.model.EstadoPedido;
import cl.speedfast.model.Pedido;
import cl.speedfast.model.PedidoComida;
import cl.speedfast.model.PedidoEncomienda;
import cl.speedfast.model.PedidoExpress;
import cl.speedfast.tareas.Repartidor;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Gestiona las operaciones principales relacionadas con los pedidos
 * de la aplicación SpeedFast.
 *
 * Permite registrar, cancelar y procesar pedidos,
 * además de consultar el historial y la zona de carga.
 *
 * Esta clase corresponde a la implementación utilizada
 * para el procesamiento concurrente de pedidos.
 *
 * @author Cristian Contreras
 * @version 1.0
 */
public class GestorPedidos {

    private HistorialPedidos historialPedidos;
    private ZonaDeCarga zonaDeCarga;

    /**
     * Constructor de la clase GestorPedidos.
     *
     * Inicializa el historial de pedidos y la zona de carga.
     */
    public GestorPedidos() {
        historialPedidos = new HistorialPedidos();
        zonaDeCarga = new ZonaDeCarga();
    }

    /**
     * Obtiene la lista de pedidos registrados.
     *
     * @return lista de pedidos registrados
     */
    public List<Pedido> obtenerPedidos() {
        return historialPedidos.getHistorial();
    }

    /**
     * Registra un nuevo pedido según el tipo seleccionado.
     *
     * El pedido es creado mediante polimorfismo, se asigna
     * el repartidor correspondiente según su tipo y distancia,
     * y posteriormente se almacena en el historial y en la
     * zona de carga.
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
            return;
        }

        pedido.asignarRepartidor();

        historialPedidos.agregarPedido(pedido);

        zonaDeCarga.agregarPedido(pedido);
    }

    /**
     * Crea un pedido según la opción seleccionada.
     *
     * Utiliza polimorfismo para crear la instancia correspondiente.
     *
     * @param opcion tipo de pedido
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
     * Muestra los pedidos que se encuentran actualmente
     * en la zona de carga.
     */
    public void mostrarZonaDeCarga() {
        zonaDeCarga.mostrarPedidos();
    }

    /**
     * Cancela un pedido registrado mediante su identificador.
     *
     * @param id identificador del pedido que se desea cancelar
     */
    public void cancelarPedido(int id) {

        Pedido pedido = historialPedidos.buscarPedido(id);

        if (pedido == null) {
            return;
        }

        pedido.cancelar();

        if (pedido.getEstado() == EstadoPedido.CANCELADO) {
            zonaDeCarga.eliminarPedido(id);
        }
    }

    /**
     * Muestra el historial de pedidos registrados.
     */
    public void mostrarHistorial() {
        historialPedidos.mostrarHistorial();
    }

    /**
     * Busca un pedido mediante su identificador.
     *
     * @param id identificador del pedido
     */
    public void buscarPedido(int id) {

        Pedido pedido = historialPedidos.buscarPedido(id);

        if (pedido == null) {
            return;
        }

        pedido.mostrarResumen();
    }

    /**
     * Cambia el estado de un pedido a EN_REPARTO.
     *
     * @param id identificador del pedido
     */
    public void cambiarEnRuta(int id) {

        Pedido pedido = historialPedidos.buscarPedido(id);

        if (pedido == null) {
            return;
        }

        pedido.marcarEnReparto();
    }

    /**
     * Cambia el estado de un pedido a ENTREGADO.
     *
     * @param id identificador del pedido
     */
    public void entregarPedido(int id) {

        Pedido pedido = historialPedidos.buscarPedido(id);

        if (pedido == null) {
            return;
        }

        pedido.marcarEntregado();
    }

    /**
     * Procesa los pedidos pendientes de la zona de carga
     * utilizando tres repartidores en paralelo.
     *
     * Utiliza ExecutorService para ejecutar los repartidores
     * mediante un pool de tres hilos.
     *
     * @param notificador permite informar los cambios de estado
     *                    de los pedidos durante el procesamiento
     */
    public void procesarPedidos(Consumer<Pedido> notificador) {

        if (zonaDeCarga.estaVacia()) {
            return;
        }

        ExecutorService executor =
                Executors.newFixedThreadPool(3);

        executor.execute(
                new Repartidor(
                        "Juan",
                        zonaDeCarga,
                        notificador
                )
        );

        executor.execute(
                new Repartidor(
                        "Camila",
                        zonaDeCarga,
                        notificador
                )
        );

        executor.execute(
                new Repartidor(
                        "Pedro",
                        zonaDeCarga,
                        notificador
                )
        );

        executor.shutdown();

        try {

            if (!executor.awaitTermination(
                    1,
                    TimeUnit.MINUTES)) {

                executor.shutdownNow();
            }

        } catch (InterruptedException e) {

            executor.shutdownNow();

            Thread.currentThread().interrupt();
        }
    }

    /**
     * Procesa los pedidos sin utilizar un notificador.
     *
     * Mantiene compatibilidad con las llamadas existentes
     * que no necesitan actualizar una interfaz gráfica.
     */
    public void procesarPedidos() {
        procesarPedidos(null);
    }

    /**
     * Inicia la entrega de un pedido específico.
     *
     * @param id identificador del pedido
     * @param notificador permite informar cambios de estado
     *                    del pedido
     */
    public void iniciarEntrega(
            int id,
            Consumer<Pedido> notificador) {

        Pedido pedido =
                historialPedidos.buscarPedido(id);

        if (pedido == null) {
            return;
        }

        if (pedido.getEstado()
                != EstadoPedido.PENDIENTE) {
            return;
        }

        Thread hilo = new Thread(
                new Repartidor(
                        "Juan",
                        pedido,
                        notificador
                )
        );

        hilo.start();
    }
}