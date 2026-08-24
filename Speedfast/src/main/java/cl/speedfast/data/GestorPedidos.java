package cl.speedfast.data;

import cl.speedfast.model.Pedido;
import cl.speedfast.model.PedidoComida;
import cl.speedfast.model.PedidoEncomienda;
import cl.speedfast.model.PedidoExpress;

public class GestorPedidos {

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

        mostrarResultado(pedido);
    }

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

    private void mostrarResultado(Pedido pedido) {

        pedido.mostrarResumen();

        System.out.println(
                "Tiempo estimado: "
                        + pedido.calcularTiempoEntrega()
                        + " minutos"
        );

        System.out.println("==============================");
    }
}