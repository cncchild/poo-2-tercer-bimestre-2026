package cl.estacionCentral.ui;

import cl.estacionCentral.util.GestorCarga;

import java.util.Scanner;

public class MenuCarga {

    private Scanner scanner;
    private GestorCarga gestorCarga;

    public MenuCarga() {

        scanner = new Scanner(System.in);
        gestorCarga = new GestorCarga();
    }

    public void iniciar() {

        boolean continuar = true;

        while (continuar) {

            mostrarMenu();

            int opcion = leerEntero("Seleccione una opción: ");

            if (opcion == 4) {

                continuar = false;

            } else if (opcion >= 1 && opcion <= 3) {

                double monto = leerDouble(
                        "Ingrese el monto a cargar: $"
                );

                gestorCarga.procesarCarga(opcion, monto);

                continuar = preguntarContinuar();

            } else {

                System.out.println(
                        "Opción no válida. Intente nuevamente."
                );
            }
        }

        System.out.println(
                "\nGracias por utilizar la máquina de carga."
        );
    }

    private void mostrarMenu() {

        System.out.println("\n================================");
        System.out.println("        ESTACIÓN CENTRAL");
        System.out.println("       MÁQUINA DE CARGA");
        System.out.println("================================");
        System.out.println("1. Cargar saldo para Bus (carga mínima 1.000)");
        System.out.println("2. Cargar saldo para Metro (carga mínima 1.800)");
        System.out.println("3. Cargar saldo para Tren (carga mínima 4.000)");
        System.out.println("4. Salir");
        System.out.println("================================");
    }

    private boolean preguntarContinuar() {

        System.out.print(
                "\n¿Desea realizar otra carga? (S/N): "
        );

        String respuesta =
                scanner.nextLine().trim();

        return respuesta.equalsIgnoreCase("S");
    }

    private int leerEntero(String mensaje) {

        while (true) {

            try {

                System.out.print(mensaje);

                return Integer.parseInt(
                        scanner.nextLine()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Debe ingresar un número válido."
                );
            }
        }
    }

    private double leerDouble(String mensaje) {

        while (true) {

            try {

                System.out.print(mensaje);

                return Double.parseDouble(
                        scanner.nextLine()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Debe ingresar un monto válido."
                );
            }
        }
    }

}
