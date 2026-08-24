package cl.estacionCentral.util;

import cl.estacionCentral.model.Bus;
import cl.estacionCentral.model.Metro;
import cl.estacionCentral.model.Transporte;
import cl.estacionCentral.model.Tren;

public class GestorCarga {

    public void procesarCarga(int opcion, double monto) {

        Transporte transporte =     seleccionarTransporte(opcion);

        if (transporte == null) {

            System.out.println(
                    "No fue posible seleccionar el transporte."
            );

            return;
        }

        transporte.realizarCarga(monto);
    }

    private Transporte seleccionarTransporte(int opcion) {

        switch (opcion) {

            case 1:
                return new Bus();

            case 2:
                return new Metro();

            case 3:
                return new Tren();

            default:
                return null;
        }
    }
}