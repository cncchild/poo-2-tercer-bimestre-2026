# Semana 1 y Semana 2 – Programación Orientada a Objetos II

## Descripción

Esta carpeta contiene el desarrollo de las actividades correspondientes a la **Semana 1 y Semana 2** de la asignatura Programación Orientada a Objetos II.

Durante estas semanas se trabajan conceptos fundamentales de la programación orientada a objetos, aplicados al desarrollo del proyecto **SpeedFast**.

## Semana 1

Durante la Semana 1 se desarrolló la estructura inicial del proyecto SpeedFast, creando una clase base `Pedido` y tres clases derivadas:

- `PedidoComida`
- `PedidoEncomienda`
- `PedidoExpress`

Se trabajaron principalmente los conceptos de:

- Clases y objetos
- Encapsulamiento
- Herencia
- Sobrescritura de métodos
- Sobrecarga de métodos
- Polimorfismo

La clase `Pedido` contenía los atributos comunes:

- `idPedido`
- `direccionEntrega`
- `tipoPedido`

Además, se implementaron los métodos `asignarRepartidor()` y `asignarRepartidor(String nombre)`.

## Semana 2

Durante la Semana 2 se actualizó la estructura del proyecto para profundizar en la abstracción, herencia, polimorfismo y organización del código mediante paquetes.

Actualización de Pedido

La clase Pedido pasó a ser una clase abstracta:

public abstract class Pedido

Se incorporó el atributo:

private double distanciaKm;

y el método abstracto:

public abstract int calcularTiempoEntrega();

De esta manera, cada tipo de pedido puede definir su propio cálculo del tiempo de entrega.

También se incorporó el método procesarPedido(), que utiliza el patrón Template Method para establecer un flujo común:

Mostrar resumen
      ↓
Calcular tiempo de entrega
      ↓
Mostrar tiempo
      ↓
Finalizar pedido
Nuevas clases y organización

Se incorporaron nuevas clases para separar las responsabilidades del programa:

src/main/java/cl/speedfast/

app
  Main.java

data
  GestorPedidos.java

model
  Pedido.java
  PedidoComida.java
  PedidoEncomienda.java
  PedidoExpress.java

ui
  MenuPedidos.java

Main

La clase Main quedó encargada de iniciar la aplicación mediante MenuPedidos:

MenuPedidos menu = new MenuPedidos();
menu.iniciar();
MenuPedidos

Se creó un menú de consola que permite:

Registrar pedidos de comida.
Registrar pedidos de encomienda.
Registrar pedidos express.
Ingresar ID, dirección y distancia.
Validar los datos ingresados.
Salir del sistema.
GestorPedidos

La clase GestorPedidos centraliza el registro de los pedidos y utiliza polimorfismo mediante una referencia de tipo Pedido.

Según la opción seleccionada, crea un objeto de:

PedidoComida
PedidoEncomienda
PedidoExpress

y posteriormente ejecuta las operaciones definidas en la clase abstracta Pedido.

Conceptos incorporados en Semana 2
Clase abstracta.
Método abstracto.
Herencia.
Polimorfismo.
Sobrescritura.
Encapsulamiento.
Separación de responsabilidades.
Organización mediante paquetes.
Validación de datos.
Patrón Template Method.
Estructura del proyecto

src
    main
        java
            cl
                speedfast
app
    Main.java
data
    GestorPedidos.java
model
    Pedido.java
    PedidoComida.java
    PedidoEncomienda.java
    PedidoExpress.java
ui
    MenuPedidos.java

    
Tecnologías
Java
Maven
NetBeans IDE
Programación Orientada a Objetos
Autor

Cristian Contreras

Duoc UC — 2026