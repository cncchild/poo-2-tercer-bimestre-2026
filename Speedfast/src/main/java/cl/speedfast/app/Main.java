package cl.speedfast.ui;


import cl.speedfast.model.Pedido;
import cl.speedfast.model.PedidoComida;
import cl.speedfast.model.PedidoEncomienda;
import cl.speedfast.model.PedidoExpress;

public class Main {

    public static void main(String[] args) {

        Pedido comida = new PedidoComida(
                1,
                "Av. Alemania 123",
                4
        );

        Pedido encomienda = new PedidoEncomienda(
                2,
                "Av. Los Pinos 456",
                7
        );

        Pedido express = new PedidoExpress(
                3,
                "Av. Chile 789",
                8
        );

        Pedido[] pedidos = {
                comida,
                encomienda,
                express
        };

        // Polimorfismo y sobrescritura
        for (Pedido pedido : pedidos) {

            pedido.mostrarResumen();

            System.out.println(
                    "Tiempo estimado: "
                            + pedido.calcularTiempoEntrega()
                            + " minutos"
            );

            System.out.println("----------------------");
        }


    }
}