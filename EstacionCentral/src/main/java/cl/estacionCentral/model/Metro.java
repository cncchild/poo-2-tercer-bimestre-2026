package cl.estacionCentral.model;

public class Metro extends Transporte {

    public Metro() {
        super("METRO");
    }

    @Override
    protected double obtenerMontoMinimo() {
        return 1800;
    }

    // Metodo propio de Metro
    public void verificarCombinacion() {
        System.out.println(
                "METRO: verificando si corresponde realizar una combinación."
        );
    }

    @Override
    protected void ejecutarAccionAdicional() {
        verificarCombinacion();
    }
}