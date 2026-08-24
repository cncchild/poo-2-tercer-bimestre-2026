package cl.estacionCentral.model;

public class Bus extends Transporte {

    public Bus() {
        super("BUS");
    }

    @Override
    protected double obtenerMontoMinimo() {
        return 1000;
    }
}