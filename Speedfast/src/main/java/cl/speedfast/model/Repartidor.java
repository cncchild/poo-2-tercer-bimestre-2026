package cl.speedfast.model;

import java.util.List;

/**
 * Representa un repartidor de SpeedFast.
 *
 * Cada repartidor posee una lista de pedidos asignados
 * y ejecuta sus entregas mediante un hilo independiente.
 *
 * @author Cristian Contreras
 * @version 1.0
 */
public class Repartidor implements Runnable {

    private String nombre;
    private List<Pedido> pedidos;

    /**
     * Constructor de Repartidor.
     *
     * @param nombre nombre del repartidor
     * @param pedidos lista de pedidos asignados
     */
    public Repartidor(String nombre, List<Pedido> pedidos) {
        this.nombre = nombre;
        this.pedidos = pedidos;
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
     * Obtiene la lista de pedidos asignados.
     *
     * @return lista de pedidos
     */
    public List<Pedido> getPedidos() {
        return pedidos;
    }

    /**
     * Ejecuta las entregas asignadas al repartidor.
     */
    @Override
    public void run() {

        System.out.println(
                "\n[" + nombre + "] Comenzó sus entregas."
        );

        for (Pedido pedido : pedidos) {

            System.out.println(
                    "[" + nombre + "] Procesando pedido #"
                            + pedido.getIdPedido()
            );

            pedido.asignarRepartidor(nombre);

            pedido.marcarEnRuta();

            try {

                int tiempoEspera =
                        1000 + (int) (Math.random() * 3000);

                System.out.println(
                        "[" + nombre + "] Entregando pedido #"
                                + pedido.getIdPedido()
                                + "..."
                );

                Thread.sleep(tiempoEspera);

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();

                System.out.println(
                        "[" + nombre + "] La entrega fue interrumpida."
                );

                return;
            }

            pedido.marcarEntregado();

            System.out.println(
                    "[" + nombre + "] Pedido #"
                            + pedido.getIdPedido()
                            + " entregado."
            );
        }

        System.out.println(
                "[" + nombre + "] Finalizó todas sus entregas."
        );
    }
}