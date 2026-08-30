# Semana 1 a 3 – Programación Orientada a Objetos II

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

```text
cl.speedfast
│
├── app
│   └── Main
│
├── data
│   └── GestorPedidos
│
├── model
│   ├── Pedido
│   ├── PedidoComida
│   ├── PedidoEncomienda
│   └── PedidoExpress
│
└── ui
    └── MenuPedidos
```
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

```text
src/
└── main/
    └── java/
        └── cl/
            └── speedfast/
                ├── app/
                │   └── Main.java
                │
                ├── data/
                │   └── GestorPedidos.java
                │
                ├── model/
                │   ├── Pedido.java
                │   ├── PedidoComida.java
                │   ├── PedidoEncomienda.java
                │   └── PedidoExpress.java
                │
                └── ui/
                    └── MenuPedidos.java
```
Tecnologías
Java
Maven
NetBeans IDE
Programación Orientada a Objetos

## Semana 3

Durante la Semana 3 se amplió el sistema **SpeedFast**, incorporando nuevas funcionalidades y profundizando en el uso de interfaces, colecciones, polimorfismo y separación de responsabilidades.

### Interfaces

Se incorporaron tres interfaces para definir comportamientos específicos de los pedidos:

* `Despachable`
* `Cancelable`
* `Rastreable`

La clase abstracta `Pedido` implementa estas interfaces:

```java
public abstract class Pedido
        implements Despachable, Cancelable, Rastreable
```

Cada interfaz representa una responsabilidad:

* `Despachable`: permite despachar un pedido.
* `Cancelable`: permite cancelar un pedido.
* `Rastreable`: permite consultar el historial de pedidos.

### Historial de pedidos

Se creó la clase `HistorialPedidos`, encargada de almacenar los pedidos registrados mediante una colección genérica `List<Pedido>`.

```java
private List<Pedido> historial;
```

La clase permite:

* Agregar pedidos.
* Buscar pedidos mediante su ID.
* Mostrar el historial de pedidos.

Esto permite mantener separada la gestión del historial de la lógica principal de los pedidos.

### Gestión de estados

Se incorporó el atributo `estado` en la clase `Pedido` para representar el ciclo de vida de cada pedido.

Los estados utilizados son:

* `EN PREPARACIÓN`
* `EN RUTA`
* `ENTREGADO`
* `CANCELADO`

El flujo principal del pedido es:

```text
EN PREPARACIÓN
       ↓
    EN RUTA
       ↓
   ENTREGADO
```

También se incorporaron validaciones para evitar cambios de estado incorrectos.

Por ejemplo, un pedido que ya se encuentra `EN RUTA` o `ENTREGADO` no puede ser cancelado.

### Asignación de repartidores

Cada tipo de pedido implementa su propia lógica de asignación de repartidor mediante sobrescritura del método:

```java
public abstract void asignarRepartidor();
```

Por ejemplo:

* `PedidoComida` asigna repartidores según la distancia.
* `PedidoEncomienda` utiliza una lógica diferente según los kilómetros.
* `PedidoExpress` utiliza un repartidor específico.

El repartidor asignado se almacena en el atributo:

```java
private String repartidor;
```

### Búsqueda de pedidos

Se incorporó la opción de buscar un pedido mediante su ID desde el menú principal.

El usuario puede ingresar el identificador y el sistema consulta el historial para encontrar el pedido correspondiente.

### Cancelación de pedidos

Se incorporó la posibilidad de cancelar pedidos desde el menú.

Antes de realizar la cancelación, el sistema verifica el estado actual del pedido, evitando cancelar pedidos que ya fueron entregados o que se encuentran en ruta.

### Actualización del menú

El menú principal de SpeedFast fue ampliado para incorporar nuevas funcionalidades:

```text
1. Registrar pedido de comida
2. Registrar pedido de encomienda
3. Registrar pedido express
4. Ver historial de pedidos
5. Cancelar pedido
6. Buscar pedido
7. Marcar pedido en ruta
8. Marcar pedido como entregado
9. Salir
```

### Estructura actual del proyecto

```text
src/
└── main/
    └── java/
        └── cl/
            └── speedfast/
                ├── app/
                │   └── Main.java
                │
                ├── data/
                │   ├── GestorPedidos.java
                │   └── HistorialPedidos.java
                │
                ├── interfaces/
                │   ├── Despachable.java
                │   ├── Cancelable.java
                │   └── Rastreable.java
                │
                ├── model/
                │   ├── Pedido.java
                │   ├── PedidoComida.java
                │   ├── PedidoEncomienda.java
                │   └── PedidoExpress.java
                │
                └── ui/
                    └── MenuPedidos.java
```

### Conceptos incorporados en Semana 3

* Interfaces.
* Implementación mediante `implements`.
* Polimorfismo.
* Sobrescritura de métodos.
* Colecciones genéricas.
* `ArrayList`.
* Separación de responsabilidades.
* Gestión de estados.
* Validación de reglas de negocio.
* Búsqueda de objetos.
* Gestión de historial.
* Encapsulamiento.
* Herencia.
* Abstracción.

### Evolución del proyecto

```text
Semana 1
   ↓
Clases, objetos, encapsulamiento,
herencia, sobrecarga y polimorfismo
   ↓
Semana 2
   ↓
Abstracción, métodos abstractos,
Template Method y organización por paquetes
   ↓
Semana 3
   ↓
Interfaces, colecciones, historial,
búsqueda, estados y reglas de negocio
```

**Tecnologías**

* Java
* Maven
* NetBeans IDE
* Programación Orientada a Objetos

**Autor**

Cristian Contreras

Duoc UC — 2026