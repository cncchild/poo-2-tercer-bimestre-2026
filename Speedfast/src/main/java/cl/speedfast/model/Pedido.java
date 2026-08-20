package cl.speedfast.model;

/**
 * Clase abstracta que representa un pedido de SpeedFast.
 *
 * Contiene la información común a los distintos
 * tipos de pedidos.
 */
public abstract class Pedido {

    private int idPedido;
    private String direccionEntrega;
    private double distanciaKm;

    public Pedido(int idPedido, String direccionEntrega,
                  double distanciaKm) {

        this.idPedido = idPedido;
        this.direccionEntrega = direccionEntrega;
        this.distanciaKm = distanciaKm;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public double getDistanciaKm() {
        return distanciaKm;
    }

    public void setDistanciaKm(double distanciaKm) {
        this.distanciaKm = distanciaKm;
    }

    /**
     * Muestra los datos básicos del pedido.
     */
    public void mostrarResumen() {

        System.out.println("ID del pedido: " + idPedido);
        System.out.println(
                "Dirección de entrega: " + direccionEntrega
        );
        System.out.println(
                "Distancia: " + distanciaKm + " km"
        );
    }

    /**
     * Calcula el tiempo estimado de entrega.
     *
     * Cada clase hija implementa su propia lógica.
     *
     * @return tiempo estimado en minutos
     */
    public abstract int calcularTiempoEntrega();
}