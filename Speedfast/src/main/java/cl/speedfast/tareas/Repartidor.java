package cl.speedfast.tareas;

import cl.speedfast.data.ZonaDeCarga;
import cl.speedfast.model.Pedido;

import java.util.function.Consumer;

/**
 * Representa un repartidor de SpeedFast.
 *
 * Cada repartidor ejecuta sus entregas mediante
 * un hilo independiente.
 *
 * Puede trabajar con una zona de carga compartida
 * o procesar un pedido específico.
 *
 * @author Cristian Contreras
 * @version 1.0
 */
public class Repartidor implements Runnable {

    private String nombre;
    private ZonaDeCarga zonaDeCarga;
    private Pedido pedido;
    private Consumer<Pedido> notificador;

    /**
     * Constructor para trabajar con una zona de carga.
     *
     * @param nombre nombre del repartidor
     * @param zonaDeCarga zona de carga de pedidos
     */
    public Repartidor(
            String nombre,
            ZonaDeCarga zonaDeCarga) {

        this.nombre = nombre;
        this.zonaDeCarga = zonaDeCarga;
        this.pedido = null;
        this.notificador = null;
    }

    /**
     * Constructor para trabajar con una zona de carga
     * y notificar cambios de estado.
     *
     * @param nombre nombre del repartidor
     * @param zonaDeCarga zona de carga de pedidos
     * @param notificador permite avisar cuando cambia el estado
     */
    public Repartidor(
            String nombre,
            ZonaDeCarga zonaDeCarga,
            Consumer<Pedido> notificador) {

        this.nombre = nombre;
        this.zonaDeCarga = zonaDeCarga;
        this.pedido = null;
        this.notificador = notificador;
    }

    /**
     * Constructor para procesar un pedido específico.
     *
     * @param nombre nombre del repartidor
     * @param pedido pedido que será entregado
     * @param notificador permite avisar cuando cambia el estado
     */
    public Repartidor(
            String nombre,
            Pedido pedido,
            Consumer<Pedido> notificador) {

        this.nombre = nombre;
        this.zonaDeCarga = null;
        this.pedido = pedido;
        this.notificador = notificador;
    }

    /**
     * Obtiene el nombre del repartidor.
     *
     * @return nombre del repartidor
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Notifica que un pedido cambió de estado.
     *
     * @param pedido pedido modificado
     */
    private void notificarCambio(Pedido pedido) {

        if (notificador != null) {
            notificador.accept(pedido);
        }
    }

    /**
     * Ejecuta las entregas asignadas al repartidor.
     */
    @Override
    public void run() {

        // Pedido específico.
        if (pedido != null) {

            procesarPedido(pedido);

            return;
        }

        // Pedidos provenientes de la zona de carga.
        while (true) {

            Pedido pedidoZona =
                    zonaDeCarga.retirarPedido();

            if (pedidoZona == null) {
                break;
            }

            procesarPedido(pedidoZona);
        }
    }

    /**
     * Procesa la entrega de un pedido.
     *
     * @param pedido pedido que será procesado
     */
    private void procesarPedido(Pedido pedido) {

        System.out.println(
                "\n[Repartidor - " + nombre
                        + "] Retirando pedido #"
                        + pedido.getIdPedido() + "..."
        );

        pedido.asignarRepartidor(nombre);

        pedido.marcarEnReparto();

        System.out.println(
                "[Repartidor - " + nombre
                        + "] Estado: "
                        + pedido.getEstado()
        );

        // Avisar que está en reparto.
        notificarCambio(pedido);

        try {

            int tiempoEspera =
                    1000 + (int) (Math.random() * 3000);

            System.out.println(
                    "\n[Repartidor - " + nombre
                            + "] Entregando pedido #"
                            + pedido.getIdPedido() + "..."
            );

            Thread.sleep(tiempoEspera);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            System.out.println(
                    "[Repartidor - " + nombre
                            + "] Entrega interrumpida."
            );

            return;
        }

        pedido.marcarEntregado();

        System.out.println(
                "[Repartidor - " + nombre
                        + "] Estado: "
                        + pedido.getEstado()
        );

        // Avisar que la entrega terminó.
        notificarCambio(pedido);
    }
}