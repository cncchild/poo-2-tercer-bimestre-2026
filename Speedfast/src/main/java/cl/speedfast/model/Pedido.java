package cl.speedfast.model;

public abstract class Pedido {

    private int idPedido;
    private String direccionEntrega;
    private double distanciaKm;

    public Pedido(
            int idPedido,
            String direccionEntrega,
            double distanciaKm) {

        this.idPedido = idPedido;
        this.direccionEntrega = direccionEntrega;
        this.distanciaKm = distanciaKm;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public double getDistanciaKm() {
        return distanciaKm;
    }

    // TEMPLATE METHOD
    public final void procesarPedido() {

        mostrarResumen();

        int tiempo = calcularTiempoEntrega();

        mostrarTiempo(tiempo);

        finalizarPedido();
    }

    // Método concreto
    public void mostrarResumen() {

        System.out.println("\n==============================");
        System.out.println("ID del pedido: " + idPedido);
        System.out.println(
                "Dirección: " + direccionEntrega
        );
        System.out.println(
                "Distancia: " + distanciaKm + " km"
        );
    }

    // Método abstracto
    public abstract int calcularTiempoEntrega();

    // Método concreto
    private void mostrarTiempo(int tiempo) {

        System.out.println(
                "Tiempo estimado: "
                        + tiempo
                        + " minutos"
        );
    }

    // Método concreto
    private void finalizarPedido() {

        System.out.println(
                "Pedido procesado correctamente."
        );

        System.out.println(
                "=============================="
        );
    }
}