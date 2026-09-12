package cl.speedfast.app;

import cl.speedfast.ui.MenuPedidos;

/**
 * Clase principal de la aplicación SpeedFast.
 *
 * Inicia el sistema mediante el menú principal,
 * desde donde se pueden registrar y gestionar pedidos.
 *
 * @author Cristian Contreras
 * @version 1.0
 */
public class Main {

    public static void main(String[] args) {

        MenuPedidos menu = new MenuPedidos();

        menu.iniciar();
    }
}