package cl.speedfast.model;

import cl.speedfast.interfaces.Cancelable;
import cl.speedfast.interfaces.Despachable;
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
    private EstadoPedido estado;

    /**
     * Constructor de la clase Pedido.
     *
     * Inicializa el pedido con estado PENDIENTE y sin
     * un repartidor asignado.
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
        this.estado = EstadoPedido.PENDIENTE;
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
     * Modifica la dirección de entrega del pedido.
     *
     * @param direccionEntrega nueva dirección de entrega
     */
    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    /**
     * Modifica la distancia de entrega del pedido.
     *
     * @param distanciaKm nueva distancia en kilómetros
     */
    public void setDistanciaKm(double distanciaKm) {
        this.distanciaKm = distanciaKm;
    }

    /**
     * Obtiene el nombre del repartidor asignado.
     *
     * @return nombre del repartidor o null si no existe asignación
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
     * Obtiene el estado actual del pedido.
     *
     * @return estado actual del pedido
     */
    public EstadoPedido getEstado() {
        return estado;
    }

    /**
     * Modifica el estado del pedido.
     *
     * @param nuevoEstado nuevo estado del pedido
     */
    public void setEstado(EstadoPedido nuevoEstado) {
        this.estado = nuevoEstado;
    }

    /**
     * Template Method que define el flujo general
     * para procesar un pedido.
     *
     * El proceso obtiene el resumen, calcula el tiempo
     * de entrega, asigna un repartidor y finaliza
     * el procesamiento.
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
    }

    /**
     * Implementación de la interfaz Despachable.
     *
     * Cambia el estado del pedido a ENTREGADO.
     */
    @Override
    public void despachar() {
        estado = EstadoPedido.ENTREGADO;
    }

    /**
     * Implementación de la interfaz Cancelable.
     *
     * Cancela el pedido cuando su estado permite realizar
     * dicha operación.
     *
     * Los pedidos en reparto, entregados o ya cancelados
     * no pueden volver a cancelarse.
     */
    @Override
    public void cancelar() {

        if (estado == EstadoPedido.EN_REPARTO) {
            return;
        }

        if (estado == EstadoPedido.ENTREGADO) {
            return;
        }

        if (estado == EstadoPedido.CANCELADO) {
            return;
        }

        estado = EstadoPedido.CANCELADO;
    }

    /**
     * Implementación de la interfaz Rastreable.
     *
     * El historial de pedidos es administrado por
     * la clase HistorialPedidos.
     */
    @Override
    public void verHistorial() {
        // El historial se administra mediante HistorialPedidos.
    }

    /**
     * Muestra la información básica del pedido.
     *
     * La información del pedido se encuentra disponible
     * mediante sus métodos getter y mediante la interfaz Swing.
     */
    public void mostrarResumen() {
        // La visualización actual se realiza mediante Swing.
    }

    /**
     * Obtiene el tipo específico de pedido.
     *
     * Cada subclase debe implementar este método.
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
     * Mantiene el cálculo del tiempo dentro del flujo
     * definido por el Template Method.
     *
     * @param tiempo tiempo estimado en minutos
     */
    private void mostrarTiempo(int tiempo) {
        // La información actualmente se gestiona mediante Swing.
    }

    /**
     * Cambia el estado del pedido a EN_REPARTO.
     */
    public void marcarEnReparto() {
        estado = EstadoPedido.EN_REPARTO;
    }

    /**
     * Cambia el estado del pedido a ENTREGADO.
     */
    public void marcarEntregado() {
        estado = EstadoPedido.ENTREGADO;
    }

    /**
     * Finaliza el procesamiento del pedido.
     *
     * Este método forma parte del flujo definido por
     * el Template Method.
     */
    private void finalizarPedido() {
        // El mensaje de finalización ya no se muestra por consola.
    }

    /**
     * Obtiene una representación textual del pedido.
     *
     * @return información del pedido en formato de texto
     */
    @Override
    public String toString() {
        return "Pedido{" +
                "idPedido=" + idPedido +
                ", direccionEntrega='" + direccionEntrega + '\'' +
                ", distanciaKm=" + distanciaKm +
                ", repartidor='" + repartidor + '\'' +
                ", estado=" + estado +
                '}';
    }
}