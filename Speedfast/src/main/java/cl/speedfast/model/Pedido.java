package cl.speedfast.model;

import cl.speedfast.interfaces.Despachable;
import cl.speedfast.interfaces.Cancelable;
import cl.speedfast.interfaces.Rastreable;

/**
 * Clase abstracta que representa un pedido dentro del sistema SpeedFast.
 *
 * Define los atributos y comportamientos comunes de los distintos
 * tipos de pedidos y establece métodos abstractos que deben ser
 * implementados por las clases derivadas.
 *
 * También implementa las interfaces Despachable, Cancelable
 * y Rastreable.
 *
 * @author Cristian Contreras
 * @version 1.0
 */
public abstract class Pedido
        implements Despachable, Cancelable, Rastreable {

    private int idPedido;
    private String direccionEntrega;
    private double distanciaKm;
    private String repartidor;
    private String estado = "EN PREPARACION";

    /**
     * Constructor de la clase Pedido.
     *
     * @param idPedido identificador del pedido
     * @param direccionEntrega dirección donde se realizará la entrega
     * @param distanciaKm distancia de entrega en kilómetros
     */
    public Pedido(
            int idPedido,
            String direccionEntrega,
            double distanciaKm) {

        this.idPedido = idPedido;
        this.direccionEntrega = direccionEntrega;
        this.distanciaKm = distanciaKm;
        this.repartidor = null;
        this.estado = "EN PREPARACION";
    }

    /**
     * Obtiene el identificador del pedido.
     *
     * @return identificador del pedido
     */
    public int getIdPedido() {
        return idPedido;
    }

    /**
     * Obtiene la dirección de entrega.
     *
     * @return dirección de entrega
     */
    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    /**
     * Obtiene la distancia de entrega.
     *
     * @return distancia en kilómetros
     */
    public double getDistanciaKm() {
        return distanciaKm;
    }

    /**
     * Obtiene el nombre del repartidor asignado.
     *
     * @return nombre del repartidor
     */
    public String getRepartidor() {
        return repartidor;
    }

    /**
     * Permite establecer el repartidor asignado.
     *
     * @param repartidor nombre del repartidor
     */
    protected void setRepartidor(String repartidor) {
        this.repartidor = repartidor;
    }

    /**
     * Permite ver el estado del pedido.
     *
     * @param estado del pedido
     */
    public String getEstado() {
        return estado;
    }

    protected void setEstado(String estado) {
        this.estado = estado;
    }
    /**
     * Template Method que define el flujo general
     * para procesar un pedido.
     *
     * El proceso muestra el resumen, calcula el tiempo
     * de entrega, asigna un repartidor, muestra el tiempo,
     * despacha el pedido y finaliza el proceso.
     */
    public final void procesarPedido() {

        mostrarResumen();

        int tiempo = calcularTiempoEntrega();

        asignarRepartidor();

        mostrarTiempo(tiempo);

        finalizarPedido();
    }

    /**
     * Asigna automáticamente un repartidor.
     *
     * Este método debe ser implementado por las clases derivadas.
     */
    public abstract void asignarRepartidor();

    /**
     * Sobrecarga del método asignarRepartidor.
     *
     * Permite asignar manualmente un repartidor mediante su nombre.
     *
     * @param nombre nombre del repartidor
     */
    public void asignarRepartidor(String nombre) {

        setRepartidor(nombre);

        System.out.println(
                "Repartidor asignado manualmente: "
                        + nombre
        );
    }

    /**
     * Implementación de la interfaz Despachable.
     *
     * Indica que el pedido fue despachado correctamente.
     */
    @Override
    public void despachar() {

        estado = "ENTREGADO";

        System.out.println(
                "Pedido despachado correctamente."
        );
    }

    /**
     * Implementación de la interfaz Cancelable.
     *
     * Cancela el pedido y muestra un mensaje indicando
     * el tipo de pedido cancelado.
     */
    @Override
    public void cancelar() {

        if (estado.equals("EN RUTA")) {

            System.out.println(
                    "[ERROR] El pedido ya está en ruta y no puede cancelarse."
            );

            return;
        }

        if (estado.equals("ENTREGADO")) {

            System.out.println(
                    "[ERROR] El pedido ya fue entregado y no puede cancelarse."
            );

            return;
        }

        if (estado.equals("CANCELADO")) {

            System.out.println(
                    "[ERROR] El pedido ya se encuentra cancelado."
            );

            return;
        }

        estado = "CANCELADO";

        System.out.println(
                "Pedido cancelado exitosamente."
        );
    }

    /**
     * Implementación de la interfaz Rastreable.
     *
     * El historial es administrado por la clase HistorialPedidos.
     */
    @Override
    public void verHistorial() {

        System.out.println(
                "El historial será gestionado por HistorialPedidos."
        );
    }

    /**
     * Muestra la información básica del pedido.
     */
    public void mostrarResumen() {

        System.out.println("\n==============================");

        System.out.println(
                "[" + obtenerTipoPedido() + "]"
        );

        System.out.println(
                "\nPedido #" + idPedido
        );

        System.out.println(
                "Dirección: " + direccionEntrega
        );

        System.out.println(
                "Distancia: " + distanciaKm + " km"
        );
        System.out.println(
                "Estado: " + estado
        );
    }

    /**
     * Obtiene el tipo específico de pedido.
     *
     * @return nombre del tipo de pedido
     */
    public abstract String obtenerTipoPedido();

    /**
     * Calcula el tiempo estimado de entrega.
     *
     * Cada subclase implementa su propia lógica de cálculo.
     *
     * @return tiempo estimado de entrega en minutos
     */
    public abstract int calcularTiempoEntrega();

    /**
     * Muestra el tiempo estimado de entrega.
     *
     * @param tiempo tiempo estimado en minutos
     */
    private void mostrarTiempo(int tiempo) {

        System.out.println(
                "Tiempo estimado: "
                        + tiempo
                        + " minutos"
        );
    }
    public void marcarEnRuta() {

        estado = "EN RUTA";

        System.out.println(
                "Pedido marcado como EN RUTA."
        );
    }

    public void marcarEntregado() {

        estado = "ENTREGADO";

        System.out.println(
                "Pedido marcado como ENTREGADO."
        );
    }
    /**
     * Finaliza el procesamiento del pedido.
     */
    private void finalizarPedido() {
         System.out.println(
                "Gracias por su preferencia."
        );
    }

}