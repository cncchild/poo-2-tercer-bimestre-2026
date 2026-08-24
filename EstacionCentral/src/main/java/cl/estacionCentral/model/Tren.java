package cl.estacionCentral.model;

public class Tren extends Transporte {

    public Tren() {
        super("TREN");
    }

    @Override
    protected double obtenerMontoMinimo() {
        return 4000;
    }

    // Metodo propio de Tren
    public void validarTarjetaSalida() {
        System.out.println(
                "TREN: validando la tarjeta a la salida."
        );
    }

    @Override
    protected void ejecutarAccionAdicional() {
        validarTarjetaSalida();
    }
}
