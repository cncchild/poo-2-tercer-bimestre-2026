package cl.speedfast.ui;

import cl.speedfast.data.GestorPedidos;

import java.util.Scanner;

/**
 * Clase encargada de gestionar la interacción con el usuario
 * mediante un menú de opciones para el sistema SpeedFast.
 *
 * Permite registrar pedidos de comida, encomienda y express,
 * consultar el historial y cancelar pedidos.
 *
 * Utiliza GestorPedidos para delegar la gestión de las
 * operaciones relacionadas con los pedidos.
 *
 * @author Cristian Contreras
 * @version 1.0
 */
public class MenuPedidos {

    private Scanner scanner;
    private GestorPedidos gestorPedidos;

    /**
     * Constructor de la clase MenuPedidos.
     *
     * Inicializa el Scanner para la entrada de datos
     * y crea una instancia de GestorPedidos.
     */
    public MenuPedidos() {

        scanner = new Scanner(System.in);
        gestorPedidos = new GestorPedidos();
    }

    /**
     * Inicia el menú principal del sistema SpeedFast.
     *
     * Mantiene el menú activo hasta que el usuario
     * selecciona la opción de salir.
     */

    public void iniciar() {

        boolean continuar = true;

        while (continuar) {

            mostrarMenu();

            int opcion = leerEntero(
                    "Seleccione una opción: "
            );

            if (opcion >= 1 && opcion <= 3) {

                registrarPedido(opcion);

            } else if (opcion == 4) {

                gestorPedidos.mostrarHistorial();

            } else if (opcion == 5) {

                cancelarPedido();

            } else if (opcion == 6) {

                buscarPedido();

            } else if (opcion == 7) {

                marcarEnRuta();

            } else if (opcion == 8) {

                marcarEntregado();

            } else if (opcion == 9) {

                continuar = false;

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

    /**
     * Muestra las opciones disponibles en el menú principal.
     */
    private void mostrarMenu() {

        System.out.println("\n================================");
        System.out.println("          SPEEDFAST");
        System.out.println("       GESTIÓN DE PEDIDOS");
        System.out.println("================================");
        System.out.println("1. Registrar pedido de comida");
        System.out.println("2. Registrar pedido de encomienda");
        System.out.println("3. Registrar pedido express");
        System.out.println("4. Ver historial de pedidos");
        System.out.println("5. Cancelar pedido");
        System.out.println("6. Buscar pedido");
        System.out.println("7. Marcar pedido en ruta");
        System.out.println("8. Marcar pedido como entregado");
        System.out.println("9. Salir");
        System.out.println("================================");
    }

    /**
     * Solicita al usuario los datos necesarios para registrar
     * un pedido y los envía al gestor de pedidos.
     *
     * @param opcion tipo de pedido seleccionado por el usuario
     */
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

    /**
     * Solicita el ID de un pedido y solicita a GestorPedidos
     * que realice su cancelación.
     */
    private void cancelarPedido() {

        int id = leerEntero(
                "Ingrese ID del pedido a cancelar: "
        );

        gestorPedidos.cancelarPedido(id);
    }

    /**
     * Solicita y valida un número entero ingresado por el usuario.
     *
     * @param mensaje mensaje que se muestra al usuario
     * @return número entero ingresado
     */
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

    /**
     * Solicita y valida una distancia ingresada por el usuario.
     *
     * La distancia debe ser un número mayor que cero.
     *
     * @param mensaje mensaje que se muestra al usuario
     * @return distancia ingresada en kilómetros
     */
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

    /**
     * Solicita y valida un texto ingresado por el usuario.
     *
     * No permite que el campo quede vacío.
     *
     * @param mensaje mensaje que se muestra al usuario
     * @return texto ingresado por el usuario
     */
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
    /**
     * Muestra un mensaje del pedido a buscar.
     */
    private void buscarPedido() {

        int id = leerEntero(
                "Ingrese ID del pedido a buscar: "
        );

        gestorPedidos.buscarPedido(id);
    }
    /**
     * Muestra un mensaje de error al usuario.
     *
     * @param mensaje descripción del error
     */
    private void mostrarError(String mensaje) {

        System.out.println(
                "[ERROR] " + mensaje
        );
    }
    private void marcarEnRuta() {

        int id = leerEntero(
                "Ingrese ID del pedido: "
        );

        gestorPedidos.cambiarEnRuta(id);
    }
    private void marcarEntregado() {

        int id = leerEntero(
                "Ingrese ID del pedido: "
        );

        gestorPedidos.entregarPedido(id);
    }
}