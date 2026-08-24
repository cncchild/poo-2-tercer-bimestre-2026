package cl.speedfast.ui;

import cl.speedfast.data.GestorPedidos;

import java.util.Scanner;

public class MenuPedidos {

    private Scanner scanner;
    private GestorPedidos gestorPedidos;

    public MenuPedidos() {

        scanner = new Scanner(System.in);
        gestorPedidos = new GestorPedidos();
    }

    public void iniciar() {

        boolean continuar = true;

        while (continuar) {

            mostrarMenu();

            int opcion = leerEntero(
                    "Seleccione una opción: "
            );

            if (opcion == 4) {

                continuar = false;

            } else if (opcion >= 1 && opcion <= 3) {

                registrarPedido(opcion);

            } else {

                mostrarError(
                        "Opción no válida."
                );
            }
        }

        System.out.println(
                "\nGracias por utilizar SpeedFast."
        );
    }

    private void mostrarMenu() {

        System.out.println("\n================================");
        System.out.println("          SPEEDFAST");
        System.out.println("       GESTIÓN DE PEDIDOS");
        System.out.println("================================");
        System.out.println("1. Registrar pedido de comida");
        System.out.println("2. Registrar pedido de encomienda");
        System.out.println("3. Registrar pedido express");
        System.out.println("4. Salir");
        System.out.println("================================");
    }

    private void registrarPedido(int opcion) {

        int id = leerEntero(
                "Ingrese ID del pedido: "
        );

        String direccion = leerTexto(
                "Ingrese dirección de entrega: "
        );

        double distancia = leerDouble(
                "Ingrese distancia en km: "
        );

        gestorPedidos.registrarPedido(
                opcion,
                id,
                direccion,
                distancia
        );
    }

    private int leerEntero(String mensaje) {

        while (true) {

            try {

                System.out.print(mensaje);

                return Integer.parseInt(
                        scanner.nextLine()
                );

            } catch (NumberFormatException e) {

                mostrarError(
                        "Debe ingresar un número entero."
                );
            }
        }
    }

    private double leerDouble(String mensaje) {

        while (true) {

            try {

                System.out.print(mensaje);

                double valor = Double.parseDouble(
                        scanner.nextLine()
                );

                if (valor <= 0) {

                    mostrarError(
                            "La distancia debe ser mayor que cero."
                    );

                    continue;
                }

                return valor;

            } catch (NumberFormatException e) {

                mostrarError(
                        "Debe ingresar una distancia válida."
                );
            }
        }
    }

    private String leerTexto(String mensaje) {

        while (true) {

            System.out.print(mensaje);

            String texto = scanner.nextLine().trim();

            if (!texto.isEmpty()) {

                return texto;
            }

            mostrarError(
                    "El campo no puede estar vacío."
            );
        }
    }

    private void mostrarError(String mensaje) {

        System.out.println(
                "[ERROR] " + mensaje
        );
    }
}