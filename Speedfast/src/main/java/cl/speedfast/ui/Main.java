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
                "Comida"
        );

        Pedido encomienda = new PedidoEncomienda(
                2,
                "Av. Los Pinos 456",
                "Encomienda"
        );

        Pedido express = new PedidoExpress(
                3,
                "Av. Chile 789",
                "Express"
        );

        Pedido[] pedidos = {
                comida,
                encomienda,
                express
        };

        // Polimorfismo y sobrescritura
        for (Pedido pedido : pedidos) {
            pedido.asignarRepartidor();
        }

        // Sobrecarga
        comida.asignarRepartidor("Carlos");
        encomienda.asignarRepartidor("Pedro");
        express.asignarRepartidor("Luis Díaz");
    }
}