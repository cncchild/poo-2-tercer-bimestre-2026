package cl.speedfast.model;


/**
 * Clase base que representa un pedido de SpeedFast.
 *
 * Contiene la información común a los distintos
 * tipos de pedidos y define el comportamiento
 * general para asignar un repartidor.
 */
public class Pedido {

    int idPedido;
    private String direccionEntrega;
    private String tipoPedido;

    public Pedido(int idPedido, String direccionEntrega, String tipoPedido) {
        this.idPedido = idPedido;
        this.direccionEntrega = direccionEntrega;
        this.tipoPedido = tipoPedido;
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

    public String getTipoPedido() {
        return tipoPedido;
    }

    public void setTipoPedido(String tipoPedido) {
        this.tipoPedido = tipoPedido;
    }

    /**
     * Método que será sobrescrito por las clases hijas
     * según el tipo de pedido.
     */
    public void asignarRepartidor() {
        System.out.println("Repartidor asignado automáticamente al pedido " + idPedido);
    }

    public void asignarRepartidor(String nombre) {
        System.out.println("Repartidor " + nombre
                + " asignado al pedido " + idPedido);

    }


}
