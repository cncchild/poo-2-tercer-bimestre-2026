package cl.estacionCentral.model;

public abstract class Transporte {

    private String nombre;

    public Transporte(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    // TEMPLATE METHOD
    public final void realizarCarga(double monto) {

        iniciarProceso();
        mostrarInformacion();

        if (validarMonto(monto)) {

            procesarCarga(monto);

            // Paso que cada transporte puede personalizar
            ejecutarAccionAdicional();

            finalizarProceso();

        } else {

            mostrarError();
        }
    }

    // Metodo concreto
    private void iniciarProceso() {
        System.out.println("\n--- INICIANDO CARGA ---");
    }

    // Metodo concreto
    public void mostrarInformacion() {

        System.out.println("Transporte seleccionado: " + nombre);

        System.out.println(
                "Carga mínima: $" + obtenerMontoMinimo()
        );
    }

    // Metodo abstracto
    protected abstract double obtenerMontoMinimo();

    // Metodo concreto
    protected boolean validarMonto(double monto) {

        return monto >= obtenerMontoMinimo();
    }


    // Metodo concreto
    protected void ejecutarAccionAdicional() {
        System.out.println(
                "Sin Acción adicional"
        );
    }

    // Metodo concreto
    private void procesarCarga(double monto) {

        System.out.println("Validando monto...");
        System.out.println("Procesando carga...");

        System.out.println(
                "Saldo cargado: $" + monto
        );
    }

    // Metodo concreto
    private void finalizarProceso() {

        System.out.println(
                "Carga realizada correctamente."
        );

        System.out.println(
                "--- OPERACIÓN FINALIZADA ---"
        );
    }

    // Metodo concreto
    private void mostrarError() {

        System.out.println(
                "[ERROR] El monto mínimo para "
                        + nombre
                        + " es $"
                        + obtenerMontoMinimo()
        );
    }
}