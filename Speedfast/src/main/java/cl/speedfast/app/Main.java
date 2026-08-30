package cl.speedfast.app;

import cl.speedfast.ui.MenuPedidos;

/**
 * Clase principal de la aplicación SpeedFast.
 *
 * Se encarga de iniciar el sistema y ejecutar
 * el menú principal de gestión de pedidos.
 *
 * @author Cristian Contreras
 * @version 1.0
 */
public class Main {

    /**
     * Método principal que inicia la aplicación.
     *
     * Crea una instancia de MenuPedidos y comienza
     * la ejecución del sistema mediante el método iniciar().
     *
     * @param args argumentos de la línea de comandos
     */
    public static void main(String[] args) {

        MenuPedidos menu = new MenuPedidos();

        menu.iniciar();
    }
}