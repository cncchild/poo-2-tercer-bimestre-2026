package cl.speedfast.model;
import cl.speedfast.data.ZonaDeCarga;


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
    private ZonaDeCarga zonaDeCarga;

    /**
     * Constructor de Repartidor.
     *
     * @param nombre nombre del repartidor
     * @param zonaDeCarga lista de pedidos asignados
     */
    public Repartidor(String nombre, ZonaDeCarga zonaDeCarga) {

        this.nombre = nombre;
        this.zonaDeCarga = zonaDeCarga;
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
     * Ejecuta las entregas asignadas al repartidor.
     */
    @Override
    public void run() {

          while (true) {

            Pedido pedido = zonaDeCarga.retirarPedido();

            if (pedido == null) {
                break;
            }
              System.out.println(
                      "\n[Repartidor - " + nombre + "] Retirando pedido #"
                              + pedido.getIdPedido() + "..."
              );

            pedido.asignarRepartidor(nombre);

            pedido.marcarEnReparto();

              System.out.println(
                      "[Repartidor - " + nombre + "] Estado: "
                              + pedido.getEstado()
              );

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
                      "[Repartidor - " + nombre + "] Estado: "
                              + pedido.getEstado()
              );
        }

      }
}