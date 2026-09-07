# Semana 1 a 4 – Programación Orientada a Objetos II

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
## Semana 4

Durante la Semana 4 se incorporó al sistema **SpeedFast** el procesamiento concurrente de pedidos mediante **hilos en Java**, permitiendo que varios repartidores puedan realizar entregas de manera simultánea.

El objetivo principal fue simular un escenario real en el que distintos repartidores trabajan al mismo tiempo, procesando sus pedidos asignados de forma independiente.

### Repartidor

Se creó la clase `Repartidor`, ubicada en el paquete `model`.

Esta clase implementa la interfaz:

```java
public class Repartidor implements Runnable
```

Cada repartidor posee:

* Nombre.
* Lista de pedidos asignados.

```java
private String nombre;
private List<Pedido> pedidos;
```

La implementación de `Runnable` permite que cada repartidor pueda ejecutarse como una tarea independiente dentro de un hilo.

### Procesamiento de pedidos

El método `run()` se encarga de recorrer la lista de pedidos asignados y procesarlos de manera secuencial.

Para cada pedido se realizan las siguientes acciones:

```text
Procesar pedido
      ↓
Asignar repartidor
      ↓
Marcar pedido EN RUTA
      ↓
Simular entrega
      ↓
Esperar tiempo aleatorio
      ↓
Marcar pedido ENTREGADO
```

Durante la simulación se utiliza:

```java
Thread.sleep()
```

para representar el tiempo que demora cada entrega.

El tiempo de espera se genera de manera aleatoria, permitiendo observar que los repartidores avanzan a diferentes velocidades.

### Ejecución concurrente

En la clase `Main` se crearon tres repartidores, cada uno con dos pedidos asignados:

```text
Cristian
├── Pedido #101
└── Pedido #102

Maria
├── Pedido #103
└── Pedido #104

Pedro
├── Pedido #105
└── Pedido #106
```

Para ejecutar los repartidores de manera concurrente se utilizó `ExecutorService`:

```java
ExecutorService executor =
        Executors.newFixedThreadPool(3);
```

Posteriormente se enviaron los tres repartidores al pool de hilos:

```java
executor.execute(repartidor1);
executor.execute(repartidor2);
executor.execute(repartidor3);
```

De esta manera, los tres repartidores pueden comenzar a trabajar prácticamente al mismo tiempo.

### Finalización de los hilos

Una vez enviadas las tareas al `ExecutorService`, se utiliza:

```java
executor.shutdown();
```

para indicar que no se agregarán nuevas tareas.

Luego se utiliza:

```java
executor.awaitTermination(1, TimeUnit.MINUTES);
```

para esperar hasta que los repartidores terminen sus entregas.

Finalmente, cuando todos los repartidores han terminado, se muestra:

```text
Todos los repartidores finalizaron sus entregas.
```

### Procesamiento paralelo

La ejecución demuestra el funcionamiento de la concurrencia porque los mensajes de los distintos repartidores aparecen intercalados en la consola.

Por ejemplo:

```text
[Maria] Procesando pedido #103
[Cristian] Procesando pedido #101
[Pedro] Procesando pedido #105

[Maria] Entregando pedido #103...
[Cristian] Entregando pedido #101...
[Pedro] Entregando pedido #105...
```

Esto demuestra que los repartidores no esperan a que otro termine todas sus entregas para comenzar, sino que trabajan de manera concurrente.

Cada repartidor mantiene, al mismo tiempo, el procesamiento secuencial de su propia lista de pedidos.

### Conceptos incorporados en Semana 4

* Concurrencia.
* Hilos en Java.
* Interfaz `Runnable`.
* Método `run()`.
* `Thread.sleep()`.
* `ExecutorService`.
* `Executors.newFixedThreadPool()`.
* `shutdown()`.
* `awaitTermination()`.
* `InterruptedException`.
* Procesamiento concurrente.
* Ejecución de tareas en paralelo.
* Simulación de tiempos aleatorios.
* Procesamiento secuencial de pedidos por repartidor.

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
                │   ├── PedidoExpress.java
                │   └── Repartidor.java
                │
                └── ui/
                    └── MenuPedidos.java
```

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
   ↓
Semana 4
   ↓
Concurrencia, hilos, Runnable,
ExecutorService y procesamiento paralelo
```

## Tecnologías

* Java
* Maven
* NetBeans IDE
* Programación Orientada a Objetos
* Concurrencia y programación con hilos

```

**Autor**

Cristian Contreras

Duoc UC — 2026