package cl.speedfast.app;

import cl.speedfast.model.Pedido;
import cl.speedfast.model.PedidoComida;
import cl.speedfast.model.PedidoEncomienda;
import cl.speedfast.model.PedidoExpress;
import cl.speedfast.model.Repartidor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Clase principal de la aplicación SpeedFast.
 *
 * Permite simular la entrega concurrente de pedidos
 * mediante varios repartidores ejecutados como hilos.
 *
 * @author Cristian Contreras
 * @version 1.0
 */
public class Main {

    public static void main(String[] args) {

        // ==============================
        // PEDIDOS DEL REPARTIDOR 1
        // ==============================

        List<Pedido> pedidosCristian = new ArrayList<>();

        pedidosCristian.add(
                new PedidoComida(
                        101,
                        "Av. Vicuña Mackenna 1234",
                        4.5
                )
        );

        pedidosCristian.add(
                new PedidoExpress(
                        102,
                        "Av. Concha y Toro 567",
                        3.2
                )
        );

        // ==============================
        // PEDIDOS DEL REPARTIDOR 2
        // ==============================

        List<Pedido> pedidosMaria = new ArrayList<>();

        pedidosMaria.add(
                new PedidoEncomienda(
                        103,
                        "Av. La Florida 2345",
                        8.0
                )
        );

        pedidosMaria.add(
                new PedidoComida(
                        104,
                        "Av. Las Mercedes 789",
                        5.5
                )
        );

        // ==============================
        // PEDIDOS DEL REPARTIDOR 3
        // ==============================

        List<Pedido> pedidosPedro = new ArrayList<>();

        pedidosPedro.add(
                new PedidoExpress(
                        105,
                        "Av. Ejército 456",
                        2.5
                )
        );

        pedidosPedro.add(
                new PedidoEncomienda(
                        106,
                        "Av. Santa Rosa 987",
                        12.0
                )
        );

        // ==============================
        // CREAR REPARTIDORES
        // ==============================

        Repartidor repartidor1 =
                new Repartidor(
                        "Cristian",
                        pedidosCristian
                );

        Repartidor repartidor2 =
                new Repartidor(
                        "Maria",
                        pedidosMaria
                );

        Repartidor repartidor3 =
                new Repartidor(
                        "Pedro",
                        pedidosPedro
                );

        // ==============================
        // CREAR EXECUTOR SERVICE
        // ==============================

        ExecutorService executor =
                Executors.newFixedThreadPool(3);

        // ==============================
        // EJECUTAR REPARTIDORES
        // ==============================

        executor.execute(repartidor1);
        executor.execute(repartidor2);
        executor.execute(repartidor3);

        // ==============================
        // CERRAR EXECUTOR
        // ==============================

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

        System.out.println(
                "\nTodos los repartidores finalizaron sus entregas."
        );
    }
}