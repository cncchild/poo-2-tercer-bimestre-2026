package cl.speedfast.controlador;

import cl.speedfast.data.GestorPedidos;
import cl.speedfast.model.Pedido;

import java.util.List;

public class ControladorPedidos {

    private GestorPedidos gestorPedidos;

    public ControladorPedidos() {
        gestorPedidos = new GestorPedidos();
    }

    public void registrarPedido(
            int opcion,
            int id,
            String direccion,
            double distancia) {

        gestorPedidos.registrarPedido(
                opcion,
                id,
                direccion,
                distancia
        );
    }

    public List<Pedido> obtenerPedidos() {
        return gestorPedidos.obtenerPedidos();
    }

    public void cancelarPedido(int id) {
        gestorPedidos.cancelarPedido(id);
    }

    public void editarPedido(
            int id,
            String direccion,
            double distancia) {

        for (Pedido pedido : obtenerPedidos()) {

            if (pedido.getIdPedido() == id) {

                pedido.setDireccionEntrega(direccion);
                pedido.setDistanciaKm(distancia);

                return;
            }
        }
    }

    public void cambiarEnRuta(int id) {
        gestorPedidos.cambiarEnRuta(id);
    }

    public void entregarPedido(int id) {
        gestorPedidos.entregarPedido(id);
    }

    public void procesarPedidos(
            java.util.function.Consumer<Pedido> notificador) {

        gestorPedidos.procesarPedidos(notificador);
    }

    public void buscarPedido(int id) {
        gestorPedidos.buscarPedido(id);
    }

    public void mostrarHistorial() {
        gestorPedidos.mostrarHistorial();
    }

    public void mostrarZonaDeCarga() {
        gestorPedidos.mostrarZonaDeCarga();
    }

    // NUEVO: inicia la entrega de un pedido específico
    public void iniciarEntrega(
            int id,
            java.util.function.Consumer<Pedido> notificador) {

        gestorPedidos.iniciarEntrega(
                id,
                notificador
        );
    }
}