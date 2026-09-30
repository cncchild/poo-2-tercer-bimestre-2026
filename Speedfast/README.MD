# Semana 1 a 9 – Programación Orientada a Objetos II

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


## Semana 5

Durante la Semana 5 se amplió el sistema **SpeedFast** incorporando **sincronización de procesos concurrentes**, permitiendo que varios repartidores trabajen simultáneamente sobre una zona de carga compartida.

El objetivo principal fue controlar el acceso concurrente a los pedidos para evitar que dos repartidores retiren el mismo pedido al mismo tiempo.

### Zona de carga compartida

Se creó la clase `ZonaDeCarga`, ubicada en el paquete `data`.

Esta clase representa el recurso compartido entre los distintos repartidores y almacena los pedidos pendientes mediante una colección `List<Pedido>`.

```java
private List<Pedido> pedidos;
```

La zona de carga permite:

* Agregar pedidos.
* Retirar pedidos pendientes.
* Eliminar pedidos cancelados.
* Consultar si está vacía.
* Mostrar los pedidos disponibles.

### Sincronización

Para proteger el acceso concurrente a la lista de pedidos se utilizaron métodos `synchronized`.

```java
public synchronized void agregarPedido(Pedido pedido)
```

y

```java
public synchronized Pedido retirarPedido()
```

La sincronización permite que solamente un hilo a la vez pueda modificar la zona de carga.

Al retirar un pedido, este se elimina inmediatamente de la colección antes de ser procesado por el repartidor.

De esta manera se evita que otro hilo pueda retirar nuevamente el mismo pedido.

El flujo es:

```text
Pedido PENDIENTE
       ↓
Zona de carga
       ↓
Repartidor retira el pedido
       ↓
Pedido eliminado de la zona
       ↓
EN_REPARTO
       ↓
Procesamiento de la entrega
       ↓
ENTREGADO
```

### Repartidor

La clase `Repartidor` implementa la interfaz:

```java
public class Repartidor implements Runnable
```

Cada repartidor recibe una referencia a la misma instancia de `ZonaDeCarga`.

```java
private String nombre;
private ZonaDeCarga zonaDeCarga;
```

Esto permite que varios repartidores trabajen sobre un recurso compartido.

Dentro del método `run()`, cada repartidor intenta retirar un pedido de la zona de carga.

```text
Retirar pedido
      ↓
¿Existe pedido?
   ↓          ↓
  Sí          No
   ↓           ↓
EN_REPARTO   Finalizar
   ↓
Simular entrega
   ↓
ENTREGADO
```

Si no existen pedidos pendientes, el repartidor finaliza su ejecución.

### Ejecución concurrente

Para administrar los hilos se utiliza `ExecutorService` con un pool de tres hilos:

```java
ExecutorService executor =
        Executors.newFixedThreadPool(3);
```

Se ejecutan tres repartidores:

```text
Juan
Camila
Pedro
```

Los tres utilizan la misma zona de carga.

```text
                 Zona de Carga
                /      |       \
               /       |        \
           Juan      Camila     Pedro
            ↓          ↓          ↓
         Pedido      Pedido     Pedido
```

Cada repartidor puede retirar y procesar distintos pedidos de manera concurrente.

### Simulación de las entregas

Para representar el tiempo de procesamiento de cada entrega se utiliza:

```java
Thread.sleep(tiempoEspera);
```

El tiempo de espera se genera aleatoriamente:

```java
int tiempoEspera =
        1000 + (int) (Math.random() * 3000);
```

Esto permite observar cómo los repartidores avanzan a diferentes velocidades y cómo sus mensajes aparecen intercalados en la consola.

### Finalización de los procesos

Una vez enviadas las tareas al `ExecutorService`, se utiliza:

```java
executor.shutdown();
```

para indicar que no se agregarán nuevas tareas.

Posteriormente:

```java
executor.awaitTermination(
        1,
        TimeUnit.MINUTES
);
```

permite esperar la finalización de los repartidores.

También se controla `InterruptedException`, restaurando el estado de interrupción del hilo cuando corresponde.

### Prueba de concurrencia

Para comprobar el funcionamiento se registraron varios pedidos y posteriormente se ejecutaron tres repartidores.

Un ejemplo de la ejecución obtenida es:

```text
[Repartidor - Juan] Retirando pedido #1...
[Repartidor - Camila] Retirando pedido #2...
[Repartidor - Pedro] Retirando pedido #3...

[Repartidor - Pedro] Estado: EN_REPARTO
[Repartidor - Juan] Estado: EN_REPARTO
[Repartidor - Camila] Estado: EN_REPARTO

[Repartidor - Camila] Entregando pedido #2...

[Repartidor - Juan] Estado: ENTREGADO

[Repartidor - Camila] Retirando pedido #5...
```

El orden de los mensajes puede variar en cada ejecución debido al funcionamiento de los hilos.

La prueba permite comprobar que:

* Existen varios hilos trabajando simultáneamente.
* Los repartidores comparten la misma zona de carga.
* Un pedido no es retirado dos veces.
* Los pedidos cambian correctamente de estado.
* Los repartidores pueden procesar más de un pedido.
* La zona de carga queda vacía cuando todos los pedidos fueron procesados.

### Control de errores de concurrencia

La sincronización de `ZonaDeCarga` evita conflictos entre los repartidores.

El método `retirarPedido()` busca solamente pedidos en estado `PENDIENTE` y elimina el pedido dentro del mismo bloque sincronizado.

Además, cuando no existen pedidos disponibles, el método retorna `null` y el repartidor finaliza su ejecución.

Esto evita ciclos infinitos y accesos incorrectos al recurso compartido.

### Conceptos incorporados en Semana 5

* Concurrencia.
* Sincronización.
* Métodos `synchronized`.
* Recursos compartidos.
* Interfaz `Runnable`.
* `ExecutorService`.
* `Executors.newFixedThreadPool()`.
* `shutdown()`.
* `awaitTermination()`.
* `InterruptedException`.
* Control de acceso a colecciones.
* Procesamiento concurrente.
* Estados de los pedidos.
* Simulación de tiempos de procesamiento.
* Control de errores de concurrencia.

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

                │   ├── HistorialPedidos.java

                │   └── ZonaDeCarga.java

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

                │   ├── EstadoPedido.java

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

   ↓

Semana 5

   ↓

Sincronización de procesos,
recursos compartidos, synchronized,
control de concurrencia y
procesamiento seguro de pedidos
```
## Semana 6

Durante la Semana 6 se amplió el sistema **SpeedFast** incorporando una **interfaz gráfica de usuario (GUI)** desarrollada con **Java Swing**, permitiendo reemplazar la interacción principal por consola por una aplicación de escritorio con ventanas, formularios, botones y tablas.

Además, se reorganizó el proyecto utilizando una estructura basada en **Modelo-Vista-Controlador (MVC)**, separando la lógica de negocio, el acceso y gestión de los datos, los controladores y la interfaz gráfica.

### Interfaz gráfica

Se desarrolló una interfaz gráfica utilizando componentes de **Java Swing** y ventanas basadas en `JFrame`.

La ventana principal permite gestionar los pedidos registrados mediante una interfaz visual.

Entre las funcionalidades disponibles se encuentran:

* Registrar pedidos.
* Visualizar pedidos mediante una tabla.
* Editar información de pedidos.
* Cancelar pedidos.
* Iniciar entregas.
* Cerrar sesión.
* Actualizar automáticamente la información mostrada.

La información de los pedidos se presenta mediante un `JTable`, utilizando `DefaultTableModel` para cargar y actualizar dinámicamente los datos.

### Sistema de inicio de sesión

Se incorporó una ventana de inicio de sesión mediante la clase `Login`.

El sistema permite validar las credenciales antes de acceder a la ventana principal.

Se incorporaron diferentes roles de usuario:

* Administrador.
* Usuario.

El rol determina las operaciones que pueden realizarse desde la interfaz.

El administrador puede utilizar funcionalidades adicionales como:

* Editar pedidos.
* Cancelar pedidos.
* Iniciar entregas.

El usuario puede consultar y registrar pedidos, manteniendo restringidas determinadas operaciones administrativas.

### Modelo Vista Controlador

Durante esta semana se reorganizó el proyecto utilizando una estructura basada en el patrón **MVC (Modelo-Vista-Controlador)**.

La estructura general quedó organizada de la siguiente manera:

```text
Modelo
   ↓
Entidades y lógica de negocio

Controlador
   ↓
Coordina las operaciones

Vista
   ↓
Interfaz gráfica
```

El objetivo de esta separación es evitar que la interfaz gráfica contenga directamente toda la lógica de negocio.

### Modelo

El paquete `model` mantiene las clases relacionadas con los pedidos y sus estados.

Entre ellas se encuentran:

* `Pedido`
* `PedidoComida`
* `PedidoEncomienda`
* `PedidoExpress`
* `EstadoPedido`
* `Usuario`

La clase abstracta `Pedido` continúa concentrando los atributos y comportamientos comunes de los distintos tipos de pedidos.

Se mantiene el uso de herencia y polimorfismo desarrollado durante las semanas anteriores.

### Controladores

Se incorporó el paquete `controlador`, encargado de conectar la interfaz gráfica con la lógica de gestión de los datos.

Se desarrollaron:

* `ControladorPedidos`
* `ControladorUsuarios`

`ControladorPedidos` permite realizar operaciones como:

* Registrar pedidos.
* Obtener la lista de pedidos.
* Editar pedidos.
* Cancelar pedidos.
* Buscar pedidos.
* Procesar pedidos.
* Iniciar la entrega de un pedido específico.

De esta manera, `VentanaPrincipal` no necesita acceder directamente a `GestorPedidos` para realizar las operaciones.

El flujo principal queda:

```text
VentanaPrincipal
       ↓
ControladorPedidos
       ↓
GestorPedidos
       ↓
Pedido
```

### Registro de pedidos

La ventana principal permite registrar nuevos pedidos mediante un formulario gráfico.

El usuario puede ingresar:

* Dirección de entrega.
* Distancia en kilómetros.
* Tipo de pedido.

El tipo de pedido se selecciona mediante un `JComboBox`.

Las opciones corresponden a:

```text
1. Pedido de comida
2. Pedido de encomienda
3. Pedido express
```

El sistema genera automáticamente el identificador del nuevo pedido utilizando los pedidos registrados.

Antes de registrar el pedido se validan los datos ingresados, evitando direcciones vacías y distancias inválidas.

### Tabla de pedidos

Los pedidos registrados se muestran mediante un `JTable`.

La tabla contiene información como:

```text
ID
Dirección
Tipo
Distancia
Tiempo
Repartidor
Estado
```

El tiempo de entrega se obtiene mediante el método:

```java
pedido.calcularTiempoEntrega();
```

De esta manera se mantiene la lógica específica de cada tipo de pedido mediante polimorfismo.

Al seleccionar una fila de la tabla, la información del pedido se carga en los campos de la interfaz para facilitar su edición y gestión.

### Edición y cancelación

La interfaz permite modificar la información de un pedido seleccionado.

Se pueden actualizar:

* Dirección.
* Distancia.

La cancelación utiliza las reglas de negocio existentes en la clase `Pedido`, evitando cancelar pedidos que ya se encuentran en estados que no permiten dicha operación.

La lógica de estas operaciones continúa centralizada en `GestorPedidos` y es utilizada mediante `ControladorPedidos`.

### Inicio de entrega individual

Una de las principales modificaciones de esta semana fue incorporar la posibilidad de iniciar la entrega de **un pedido específico seleccionado desde la tabla**.

Anteriormente, `procesarPedidos()` permitía que los repartidores trabajaran sobre todos los pedidos pendientes disponibles en la zona de carga.

Para la interfaz gráfica se incorporó el método:

```java
iniciarEntrega(int id, Consumer<Pedido> notificador)
```

Este método permite seleccionar un pedido determinado y comenzar solamente su proceso de entrega.

El flujo es:

```text
Seleccionar pedido
       ↓
Iniciar entrega
       ↓
Buscar pedido por ID
       ↓
Repartidor
       ↓
EN_REPARTO
       ↓
Simulación de entrega
       ↓
ENTREGADO
```

Por ejemplo, si existen tres pedidos:

```text
Pedido #1 → PENDIENTE
Pedido #2 → PENDIENTE
Pedido #3 → PENDIENTE
```

y se selecciona el pedido #2, solamente este pedido cambia de estado:

```text
Pedido #1 → PENDIENTE
Pedido #2 → EN_REPARTO → ENTREGADO
Pedido #3 → PENDIENTE
```

Esto permite diferenciar el procesamiento concurrente de la Semana 5 de la gestión individual de entregas desde la interfaz gráfica.

### Repartidor y actualización de la interfaz

La clase `Repartidor` fue reorganizada dentro del paquete:

```text
cl.speedfast.tareas
```

La clase continúa implementando:

```java
public class Repartidor implements Runnable
```

y mantiene la funcionalidad de procesamiento concurrente desarrollada anteriormente.

Además, se incorporó la posibilidad de procesar un pedido específico.

Para informar los cambios de estado a la interfaz se utiliza:

```java
Consumer<Pedido>
```

El repartidor informa los cambios cuando el pedido pasa a:

```text
PENDIENTE
   ↓
EN_REPARTO
   ↓
ENTREGADO
```

La interfaz gráfica utiliza `SwingUtilities.invokeLater()` para actualizar la tabla de manera segura dentro del hilo de eventos de Swing.

### Conservación de la concurrencia

La incorporación de la interfaz gráfica no elimina el trabajo realizado durante las semanas anteriores.

Se mantiene la funcionalidad de procesamiento concurrente mediante:

```java
ExecutorService
```

y:

```java
Executors.newFixedThreadPool(3);
```

Los tres repartidores continúan pudiendo trabajar sobre la `ZonaDeCarga` compartida.

Por otra parte, la nueva función de entrega individual permite que la interfaz seleccione específicamente qué pedido desea procesar.

De esta forma, el proyecto integra:

```text
POO
 ↓
Interfaces
 ↓
Colecciones
 ↓
Estados
 ↓
Concurrencia
 ↓
Sincronización
 ↓
Interfaz gráfica
 ↓
MVC
```

### Estructura actual del proyecto

La estructura actual del proyecto quedó organizada de la siguiente manera:

```text
src/
└── main/
    └── java/
        └── cl/
            └── speedfast/
                │
                ├── app/
                │   └── Main.java
                │
                ├── controlador/
                │   ├── ControladorPedidos.java
                │   └── ControladorUsuarios.java
                │
                ├── data/
                │   ├── GestorPedidos.java
                │   ├── HistorialPedidos.java
                │   └── ZonaDeCarga.java
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
                │   ├── EstadoPedido.java
                │   └── Usuario.java
                │
                ├── tareas/
                │   └── Repartidor.java
                │
                └── vista/
                    ├── Login.java
                    └── VentanaPrincipal.java
```

### Conceptos incorporados en Semana 6

* Java Swing.
* `JFrame`.
* Interfaces gráficas de usuario.
* `JButton`.
* `JTextField`.
* `JTextArea`.
* `JComboBox`.
* `JTable`.
* `DefaultTableModel`.
* `JOptionPane`.
* Eventos de botones.
* Selección de filas.
* Validación de datos.
* Actualización dinámica de tablas.
* Patrón Modelo-Vista-Controlador.
* Separación de responsabilidades.
* Control de acceso mediante roles.
* Inicio de sesión.
* Procesamiento individual de pedidos.
* `Consumer<Pedido>`.
* `SwingUtilities.invokeLater()`.
* Integración entre GUI y concurrencia.
* Reutilización de las clases desarrolladas en semanas anteriores.

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

   ↓

Semana 5
   ↓
Sincronización de procesos,
recursos compartidos, synchronized,
control de concurrencia y
procesamiento seguro de pedidos

   ↓

Semana 6
   ↓
Interfaz gráfica, Java Swing,
MVC, login, roles,
gestión visual de pedidos
y entrega individual
```


# Semana 7 – Conectando aplicaciones Java con bases de datos mediante JDBC

## Descripción

Durante la **Semana 7** se amplió el proyecto **SpeedFast** incorporando persistencia de datos mediante una base de datos **MySQL** y conexión desde Java utilizando **JDBC (Java Database Connectivity)**.

El objetivo fue conectar la aplicación desarrollada durante las semanas anteriores con una base de datos relacional, permitiendo almacenar, consultar y actualizar información de pedidos, repartidores y entregas.

De esta manera, SpeedFast deja de depender únicamente de información almacenada en memoria y comienza a trabajar con **datos persistentes**.

## Base de datos

Se creó la base de datos:

```text
speedfast_db
```

Las principales tablas utilizadas son:

```text
pedido
repartidor
entrega
```

Además, el proyecto incorpora la tabla:

```text
usuario
```

para gestionar el inicio de sesión y los roles de acceso.

### Tabla pedido

Almacena la información de los pedidos:

```text
id
direccion
tipo
distancia_km
estado
```

### Tabla repartidor

Almacena los repartidores disponibles:

```text
id
nombre
```

### Tabla entrega

Relaciona los pedidos con los repartidores:

```text
id
id_pedido
id_repartidor
fecha
hora
```

Las relaciones principales son:

```text
REPARTIDOR
     │
     │ 1
     │
     │ N
   ENTREGA
     │
     │ N
     │
     │ 1
   PEDIDO
```

Un repartidor puede realizar varias entregas y cada entrega queda asociada a un pedido y a un repartidor.

## Conexión JDBC

Se creó la clase:

```text
ConexionBD
```

ubicada en:

```text
cl.speedfast.conexion
```

Esta clase centraliza la conexión con MySQL mediante `DriverManager`.

La conexión utiliza:

```text
jdbc:mysql://localhost:3306/speedfast_db
```

El flujo de conexión es:

```text
Aplicación Java
      ↓
ConexionBD
      ↓
DriverManager
      ↓
MySQL
      ↓
speedfast_db
```

## MySQL Connector/J

Se incorporó el conector JDBC de MySQL mediante Maven:

```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>26.7.0</version>
</dependency>
```

Esto permite que la aplicación Java pueda comunicarse directamente con MySQL.

## Patrón DAO

Para separar el acceso a datos de la interfaz gráfica se implementó el patrón **DAO (Data Access Object)**.

Se incorporaron las clases:

```text
PedidoDAO
RepartidorDAO
EntregaDAO
UsuarioDAO
```

La estructura general quedó:

```text
Interfaz gráfica
       ↓
      DAO
       ↓
  ConexionBD
       ↓
      JDBC
       ↓
     MySQL
```

Esta separación permite mantener las consultas SQL fuera de las ventanas Swing.

## PedidoDAO

La clase `PedidoDAO` se encarga de gestionar los pedidos almacenados en MySQL.

Entre las operaciones implementadas se encuentran:

```text
Guardar pedidos
Listar pedidos
Actualizar pedidos
Actualizar estado
Obtener siguiente ID
```

Para las consultas e inserciones se utiliza `PreparedStatement`.

Por ejemplo:

```java
PreparedStatement ps =
        conexion.prepareStatement(sql);
```

Los datos son enviados mediante parámetros para evitar construir directamente las consultas SQL con los valores ingresados por el usuario.

## RepartidorDAO

La clase `RepartidorDAO` permite consultar los repartidores almacenados en la base de datos.

La operación principal es:

```java
listarTodos()
```

El método utiliza `ResultSet` para obtener los registros y convertirlos en objetos `Repartidor`.

El flujo es:

```text
MySQL
  ↓
RepartidorDAO
  ↓
ResultSet
  ↓
List<Repartidor>
  ↓
Interfaz gráfica
```

Estos repartidores posteriormente pueden ser seleccionados al iniciar una entrega.

## EntregaDAO

La clase `EntregaDAO` permite registrar las entregas realizadas.

El método principal es:

```java
guardar(Entrega entrega)
```

La información almacenada incluye:

```text
ID del pedido
ID del repartidor
Fecha
Hora
```

La relación queda representada mediante:

```text
entrega.id_pedido
        ↓
pedido.id

entrega.id_repartidor
        ↓
repartidor.id
```

## Integración con la interfaz gráfica

La interfaz desarrollada durante la Semana 6 fue conectada con la base de datos.

El flujo para registrar un pedido es:

```text
Formulario
    ↓
Crear Pedido
    ↓
PedidoDAO
    ↓
PreparedStatement
    ↓
MySQL
    ↓
Tabla pedido
```

La ventana `ListaPedidos` consulta los datos mediante:

```java
pedidoDAO.listarTodos();
```

Los resultados son mostrados mediante un `JTable`.

La tabla presenta:

```text
ID
Dirección
Tipo
Distancia
Tiempo
Repartidor
Estado
```

## Registro de pedidos

La ventana `RegistroPedidos` permite ingresar:

```text
Dirección
Distancia
Tipo de pedido
```

El sistema crea el objeto correspondiente:

```text
PedidoComida
PedidoEncomienda
PedidoExpress
```

Luego se almacena utilizando:

```java
pedidoDAO.guardar(pedido);
```

Una vez guardado correctamente, la tabla de pedidos se actualiza.

## Inicio de entrega

Se incorporó la integración entre pedidos, repartidores y entregas.

El proceso es:

```text
Seleccionar pedido
       ↓
Iniciar entrega
       ↓
Consultar repartidores
       ↓
Seleccionar repartidor
       ↓
Asignar repartidor
       ↓
EN_REPARTO
       ↓
Registrar entrega
       ↓
Simular entrega
       ↓
ENTREGADO
```

Para obtener los repartidores se utiliza:

```java
repartidorDAO.listarTodos();
```

La entrega se registra mediante:

```java
entregaDAO.guardar(entrega);
```

El estado del pedido se actualiza mediante:

```java
pedidoDAO.actualizarEstado(
        pedido.getIdPedido(),
        EstadoPedido.EN_REPARTO
);
```

Posteriormente, una vez finalizada la simulación, el pedido pasa a:

```text
ENTREGADO
```

## Persistencia de estados

Los pedidos utilizan el enum:

```java
EstadoPedido
```

con los siguientes estados:

```text
PENDIENTE
EN_REPARTO
ENTREGADO
CANCELADO
```

El flujo normal de una entrega es:

```text
PENDIENTE
    ↓
EN_REPARTO
    ↓
ENTREGADO
```

Los cambios de estado también se almacenan en la base de datos.

## Manejo de excepciones

Las operaciones JDBC utilizan `try-catch` para controlar errores relacionados con la conexión y las consultas SQL.

También se utilizan bloques `try-with-resources` para cerrar automáticamente:

```text
Connection
PreparedStatement
ResultSet
```

En caso de producirse un error, la aplicación informa al usuario mediante mensajes de Swing.

## Estructura del proyecto

La estructura relacionada con la Semana 7 quedó organizada de la siguiente manera:

```text
src/
└── main/
    └── java/
        └── cl/
            └── speedfast/
                ├── conexion/
                │   └── ConexionBD.java
                │
                ├── dao/
                │   ├── PedidoDAO.java
                │   ├── RepartidorDAO.java
                │   ├── EntregaDAO.java
                │   └── UsuarioDAO.java
                │
                ├── model/
                │   ├── Pedido.java
                │   ├── PedidoComida.java
                │   ├── PedidoEncomienda.java
                │   ├── PedidoExpress.java
                │   ├── EstadoPedido.java
                │   ├── Repartidor.java
                │   ├── Entrega.java
                │   └── Usuario.java
                │
                └── vista/
                    ├── Login.java
                    ├── Home.java
                    ├── ListaPedidos.java
                    ├── RegistroPedidos.java
                    ├── EditarRegistroPedido.java
                    ├── ListaUsuarios.java
                    ├── RegistroUsuarios.java
                    └── EditarUsuarios.java
```

## Conceptos incorporados en Semana 7

* JDBC.
* MySQL.
* `DriverManager`.
* `Connection`.
* `PreparedStatement`.
* `ResultSet`.
* `SQLException`.
* `try-catch`.
* `try-with-resources`.
* Patrón DAO.
* Persistencia de datos.
* Consultas SQL.
* Claves primarias.
* Claves foráneas.
* Relaciones entre tablas.
* Integración entre Java Swing y MySQL.
* Registro de pedidos.
* Consulta de repartidores.
* Registro de entregas.
* Actualización de estados.
* Persistencia de información.

## Evolución del proyecto

```text
Semana 1
   ↓
POO, clases, objetos, herencia,
encapsulamiento y polimorfismo

   ↓

Semana 2
   ↓
Abstracción, métodos abstractos,
Template Method y paquetes

   ↓

Semana 3
   ↓
Interfaces, colecciones,
historial y estados

   ↓

Semana 4
   ↓
Hilos, Runnable,
ExecutorService y concurrencia

   ↓

Semana 5
   ↓
Sincronización, recursos compartidos
y procesamiento concurrente seguro

   ↓

Semana 6
   ↓
Java Swing, GUI, login,
roles y gestión visual

   ↓

Semana 7
   ↓
MySQL, JDBC, DAO,
persistencia de datos,
pedidos, repartidores
y entregas
```

## Resultado de la Semana 7

Al finalizar esta semana, **SpeedFast** cuenta con una aplicación Java conectada a una base de datos MySQL mediante JDBC.

La aplicación permite:

* Registrar pedidos desde la interfaz gráfica.
* Guardar pedidos en MySQL.
* Consultar pedidos almacenados.
* Mostrar pedidos mediante `JTable`.
* Consultar repartidores.
* Seleccionar repartidores para realizar entregas.
* Registrar entregas.
* Actualizar estados de los pedidos.
* Mantener los datos almacenados de forma persistente.
* Separar el acceso a datos mediante clases DAO.

De esta forma, SpeedFast evoluciona desde una aplicación orientada a objetos con concurrencia e interfaz gráfica hacia una aplicación con **persistencia real de datos mediante MySQL y JDBC**.
```
             SPEEDFAST
                 │
                 ▼
          INTERFAZ SWING
                 │
       ┌─────────┴─────────┐
       ▼                   ▼
 RegistroPedidos      ListaPedidos
       │                   │
       ▼                   ▼
   PedidoDAO          PedidoDAO
       │                   │
       └─────────┬─────────┘
                 ▼
             ConexionBD
                 │
                 ▼
               JDBC
                 │
                 ▼
              MySQL
                 │
          speedfast_db
        ┌────────┼────────┐
        ▼        ▼        ▼
     pedido  repartidor  entrega
```
     
## Tecnologías

* Java
* Maven
* IntelliJ IDEA
* MySQL
* MySQL Connector/J
* JDBC
* Java Swing
* Programación Orientada a Objetos
* Patrón DAO
* Persistencia de datos
* Concurrencia

---

````md
# Semana 8 – Desarrollo del ciclo funcional completo, CRUD y refactorización de SpeedFast

## Descripción

Durante la **Semana 8** se completó y consolidó el desarrollo del proyecto **SpeedFast**, integrando las funcionalidades desarrolladas durante las semanas anteriores en una aplicación Java con interfaz gráfica, persistencia de datos y operaciones CRUD mediante JDBC.

El objetivo principal fue completar el ciclo funcional de la aplicación, permitiendo gestionar pedidos, repartidores, entregas y usuarios mediante una interfaz desarrollada con **Java Swing**, conectada a una base de datos **MySQL** utilizando el patrón **DAO**.

Durante esta semana también se realizó una revisión y refactorización del código para mejorar la organización, reducir dependencias innecesarias y documentar las principales clases mediante **JavaDoc**.

El flujo general de la aplicación quedó:

```text
Login
  ↓
Home
  ↓
Gestión de módulos
  ↓
DAO
  ↓
JDBC
  ↓
MySQL
````

---

## Ciclo funcional completo

Uno de los principales objetivos de la Semana 8 fue completar el ciclo funcional de SpeedFast.

El sistema permite realizar las operaciones principales sobre las entidades almacenadas en la base de datos:

```text
Crear
  ↓
Leer
  ↓
Actualizar
  ↓
Eliminar
```

Estas operaciones se aplican principalmente a:

```text
Usuarios
Pedidos
Repartidores
Entregas
```

La interfaz gráfica permite realizar estas operaciones mediante formularios, tablas, botones y ventanas de diálogo.

---

# Gestión de pedidos

La gestión de pedidos se realiza mediante las ventanas:

```text
RegistroPedidos
ListaPedidos
EditarRegistroPedido
```

y el DAO:

```text
PedidoDAO
```

### Registrar pedido

La ventana `RegistroPedidos` permite ingresar:

```text
Dirección de entrega
Distancia en kilómetros
Tipo de pedido
```

Los tipos disponibles son:

```text
COMIDA
ENCOMIENDA
EXPRESS
```

El sistema crea la instancia correspondiente:

```text
PedidoComida
PedidoEncomienda
PedidoExpress
```

manteniendo el polimorfismo desarrollado durante las primeras semanas.

Posteriormente el pedido se almacena mediante:

```java
pedidoDAO.guardar(pedido);
```

El flujo es:

```text
Formulario
    ↓
Validación
    ↓
Crear objeto Pedido
    ↓
PedidoDAO
    ↓
PreparedStatement
    ↓
MySQL
```

---

## Listar pedidos

La ventana `ListaPedidos` consulta los pedidos almacenados mediante:

```java
pedidoDAO.listarTodos();
```

La información se muestra mediante un `JTable`.

Las columnas principales son:

```text
ID
Dirección
Tipo
Distancia
Tiempo
Repartidor
Estado
```

La tabla utiliza `DefaultTableModel` y permite actualizar la información después de realizar operaciones sobre los pedidos.

También se incorporó selección de filas para realizar operaciones sobre un pedido específico.

---

## Editar pedidos

La ventana:

```text
EditarRegistroPedido
```

permite modificar información de un pedido seleccionado.

Los datos que pueden actualizarse incluyen:

```text
Dirección
Distancia
```

La modificación se realiza mediante:

```java
pedidoDAO.actualizar(pedido);
```

De esta manera, los cambios realizados en la interfaz también quedan almacenados en MySQL.

---

## Eliminación de pedidos

Se incorporaron dos comportamientos relacionados con la eliminación de pedidos.

### Cancelación lógica

Un pedido puede cambiar a:

```text
CANCELADO
```

mediante las reglas de negocio definidas en la clase `Pedido`.

Esto permite conservar el registro del pedido en la base de datos.

### Eliminación física

También se incorporó la posibilidad de eliminar físicamente un pedido mediante:

```java
pedidoDAO.eliminar(id);
```

Antes de eliminar se solicita confirmación al usuario mediante `JOptionPane`.

---

# Estados de los pedidos

Se mantiene el enum:

```java
EstadoPedido
```

con los siguientes estados:

```text
PENDIENTE
EN_REPARTO
ENTREGADO
CANCELADO
```

El flujo normal de una entrega es:

```text
PENDIENTE
    ↓
EN_REPARTO
    ↓
ENTREGADO
```

Los cambios de estado se almacenan en la base de datos mediante:

```java
pedidoDAO.actualizarEstado(
    id,
    EstadoPedido.EN_REPARTO
);
```

Esto permite mantener sincronizado el estado mostrado en la interfaz con el estado almacenado en MySQL.

---

# Inicio de una entrega

Se completó el proceso de inicio de entrega desde la lista de pedidos.

El usuario selecciona un pedido y presiona:

```text
Iniciar Entrega
```

El sistema verifica que exista un pedido seleccionado y que pueda iniciar una nueva entrega.

Posteriormente consulta los repartidores disponibles mediante:

```java
repartidorDAO.listarTodos();
```

Los repartidores se muestran en un `JComboBox`.

El usuario selecciona el repartidor que realizará la entrega.

El proceso completo es:

```text
Seleccionar pedido
       ↓
Iniciar entrega
       ↓
Consultar repartidores
       ↓
Seleccionar repartidor
       ↓
Asignar repartidor
       ↓
Cambiar estado a EN_REPARTO
       ↓
Crear Entrega
       ↓
Guardar Entrega
       ↓
Simular entrega
       ↓
Cambiar estado a ENTREGADO
```

---

# Gestión de repartidores

La gestión de repartidores se realiza mediante:

```text
ListaRepartidores
RepartidorDAO
```

El sistema permite:

```text
Registrar repartidor
Listar repartidores
Editar repartidor
Eliminar repartidor
```

La información almacenada corresponde principalmente a:

```text
ID
Nombre
```

### Registrar repartidor

Se solicita el nombre mediante un formulario de diálogo.

Después de validar que el nombre no esté vacío, se crea un objeto:

```java
Repartidor
```

y se guarda mediante:

```java
repartidorDAO.guardar(repartidor);
```

### Editar repartidor

El usuario selecciona un registro de la tabla y modifica su nombre.

El cambio se guarda mediante:

```java
repartidorDAO.actualizar(repartidor);
```

### Eliminar repartidor

Se selecciona un repartidor y se solicita confirmación antes de ejecutar:

```java
repartidorDAO.eliminar(id);
```

---

# Gestión de entregas

La gestión de entregas se realiza mediante:

```text
ListaEntrega
EntregaDAO
```

La tabla muestra:

```text
ID
Pedido
Repartidor
Fecha
Hora
```

Las entregas se crean como parte del proceso:

```text
ListaPedidos
    ↓
Iniciar Entrega
    ↓
Seleccionar repartidor
    ↓
Crear Entrega
    ↓
EntregaDAO
```

La ventana `ListaEntrega` permite:

```text
Listar entregas
Editar entregas
Eliminar entregas
```

También mantiene un botón de agregar entrega que informa al usuario que la creación se realiza desde la gestión de pedidos.

El mensaje mostrado es:

```text
La creación de entregas se realiza desde el Home, administrar Pedidos.
```

Esto evita crear entregas sin asociarlas correctamente a un pedido y un repartidor.

---

# CRUD de entregas

Las operaciones de entrega utilizan:

```java
EntregaDAO
```

Las funcionalidades implementadas son:

```text
Guardar
Listar
Actualizar
Eliminar
```

Cada entrega contiene:

```text
ID
ID del pedido
ID del repartidor
Fecha
Hora
```

Las relaciones se mantienen mediante las claves foráneas:

```text
entrega.id_pedido
       ↓
pedido.id

entrega.id_repartidor
       ↓
repartidor.id
```

---

# Gestión de usuarios

Durante esta semana también se completó la gestión de usuarios mediante:

```text
UsuarioDAO
ListaUsuarios
RegistroUsuarios
EditarUsuarios
```

Las operaciones disponibles son:

```text
Registrar
Listar
Editar
Eliminar
Autenticar
```

Los usuarios contienen:

```text
Nombre de usuario
Contraseña
Rol
```

Los roles disponibles son:

```text
usuario
administrador
```

---

# Inicio de sesión

La ventana `Login` utiliza directamente:

```java
UsuarioDAO
```

para autenticar las credenciales.

El proceso es:

```text
Ingresar usuario
       ↓
Ingresar contraseña
       ↓
Validar campos
       ↓
UsuarioDAO.autenticar()
       ↓
Consultar MySQL
       ↓
Usuario encontrado
       ↓
Abrir Home
```

Si las credenciales no son válidas, el sistema informa al usuario mediante `JOptionPane`.

---

# Refactorización de usuarios

Durante la Semana 8 se comenzó a eliminar la dependencia de la clase:

```text
ControladorUsuarios
```

Anteriormente la comunicación tenía una capa adicional:

```text
Login
  ↓
ControladorUsuarios
  ↓
UsuarioDAO
  ↓
MySQL
```

La estructura fue simplificada a:

```text
Login
  ↓
UsuarioDAO
  ↓
MySQL
```

La misma modificación se aplicó a las ventanas relacionadas con usuarios.

La estructura actual es:

```text
Login
  ↓
Home
  ↓
ListaUsuarios
  ↓
RegistroUsuarios / EditarUsuarios
  ↓
UsuarioDAO
  ↓
MySQL
```

Esto reduce el acoplamiento y evita mantener una clase intermediaria que ya no es necesaria.

---

# Administración de usuarios

Desde `Home` se puede acceder a la gestión de usuarios.

La ventana `ListaUsuarios` permite:

```text
Ver usuarios
Agregar usuarios
Editar usuarios
Eliminar usuarios
```

La información se muestra mediante una tabla Swing.

Las operaciones se realizan directamente mediante `UsuarioDAO`.

---

# Control de acceso mediante roles

El sistema mantiene el control de acceso según el rol del usuario.

El rol se obtiene desde la base de datos durante la autenticación y se utiliza posteriormente para configurar las opciones disponibles en la interfaz.

El flujo es:

```text
Login
  ↓
UsuarioDAO
  ↓
Usuario
  ↓
Nombre + Rol
  ↓
Home
  ↓
Permisos
```

Esto permite mantener diferentes funcionalidades según el tipo de usuario.

---

# Validación de datos

Durante esta semana se revisaron las validaciones utilizadas en los formularios.

Entre las validaciones implementadas se encuentran:

```text
Campos obligatorios
Dirección no vacía
Distancia válida
Nombre de usuario obligatorio
Contraseña obligatoria
Nombre de repartidor obligatorio
Selección de registros antes de editar
Selección de registros antes de eliminar
Confirmación antes de eliminar
Fechas válidas
Horas válidas
```

Cuando se detecta un dato incorrecto se utiliza:

```java
JOptionPane.showMessageDialog(...)
```

para informar al usuario.

---

# Manejo de excepciones

Las operaciones relacionadas con JDBC utilizan manejo de excepciones mediante:

```java
SQLException
```

Además, se utiliza `try-with-resources` para administrar recursos JDBC.

Los principales recursos controlados son:

```text
Connection
PreparedStatement
ResultSet
```

Esto permite cerrar automáticamente los recursos después de realizar las operaciones con la base de datos.

---

# PreparedStatement y ResultSet

Los DAO utilizan `PreparedStatement` para ejecutar las consultas SQL.

Ejemplo:

```java
PreparedStatement ps =
        conexion.prepareStatement(sql);
```

Los resultados de las consultas se obtienen mediante:

```java
ResultSet
```

Los registros recuperados desde MySQL son convertidos nuevamente en objetos Java.

El flujo es:

```text
MySQL
  ↓
ResultSet
  ↓
DAO
  ↓
Objeto Java
  ↓
Interfaz gráfica
```

---

# Separación de responsabilidades

La arquitectura del proyecto quedó organizada separando las principales responsabilidades.

```text
MODEL
  ↓
Entidades y lógica de negocio

DAO
  ↓
Acceso a la base de datos

CONEXION
  ↓
Conexión JDBC con MySQL

VISTA
  ↓
Interfaz gráfica Swing
```

Esta separación permite modificar una parte del sistema sin tener que modificar directamente las demás.

---

# Documentación mediante JavaDoc

Durante la Semana 8 también se realizó una revisión de la documentación del código.

Se incorporaron comentarios JavaDoc en las principales clases y métodos del proyecto.

La documentación incluye:

```text
Descripción de clases
Descripción de constructores
Parámetros
Valores de retorno
Excepciones
Responsabilidad de los métodos
```

Entre las clases documentadas se encuentran:

```text
ConexionBD
Pedido
PedidoComida
PedidoEncomienda
PedidoExpress
EstadoPedido
Repartidor
Entrega
Usuario

PedidoDAO
RepartidorDAO
EntregaDAO
UsuarioDAO

ListaPedidos
RegistroPedidos
EditarRegistroPedido
ListaUsuarios
RegistroUsuarios
EditarUsuarios
ListaRepartidores
ListaEntrega
Home
Login
```

La documentación permite comprender con mayor facilidad la responsabilidad de cada componente del sistema.

---

# Limpieza y refactorización del proyecto

Se realizó una revisión de clases correspondientes a etapas anteriores del proyecto.

La aplicación actualmente utiliza como flujo principal:

```text
Main
  ↓
Login
  ↓
Home
  ↓
Vistas Swing
  ↓
DAO
  ↓
MySQL
```

Las clases antiguas que ya no participan del flujo principal fueron revisadas para determinar si podían ser eliminadas sin afectar el funcionamiento actual.

También se realizó una búsqueda global de referencias antes de eliminar clases para evitar errores de compilación.

---

# Estructura actual del proyecto

La estructura principal del proyecto quedó organizada de la siguiente manera:

```text
src/

└── main/

    └── java/

        └── cl/

            └── speedfast/

                ├── app
                     ---Main.java
                │
                ├── conexion/
                │   └── ConexionBD.java
                │
                ├── dao/
                │   ├── PedidoDAO.java
                │   ├── RepartidorDAO.java
                │   ├── EntregaDAO.java
                │   └── UsuarioDAO.java
                │
                ├── model/
                │   ├── Pedido.java
                │   ├── PedidoComida.java
                │   ├── PedidoEncomienda.java
                │   ├── PedidoExpress.java
                │   ├── EstadoPedido.java
                │   ├── Repartidor.java
                │   ├── Entrega.java
                │   └── Usuario.java
                │
                ├── interfaces/
                │   ├── Despachable.java
                │   ├── Cancelable.java
                │   └── Rastreable.java
                │
                ├── data/
                │   ├── GestorPedidos.java
                │   ├── HistorialPedidos.java
                │   └── ZonaDeCarga.java
                │
                ├── tareas/
                │   └── Repartidor.java
                │
                └── vista/
                    ├── Login.java
                    ├── Home.java
                    ├── ListaPedidos.java
                    ├── RegistroPedidos.java
                    ├── EditarRegistroPedido.java
                    ├── ListaUsuarios.java
                    ├── RegistroUsuarios.java
                    ├── EditarUsuarios.java
                    ├── ListaRepartidores.java
                    └── ListaEntrega.java
```

---

# Flujo general de SpeedFast

El funcionamiento general de la aplicación quedó:

```text
                         SPEEDFAST
                             │
                             ▼
                           LOGIN
                             │
                             ▼
                            HOME
                             │
          ┌──────────────────┼──────────────────┐
          │                  │                  │
          ▼                  ▼                  ▼
       PEDIDOS          USUARIOS          REPARTIDORES
          │                  │                  │
          │                  │                  │
          ▼                  ▼                  ▼
      PedidoDAO         UsuarioDAO       RepartidorDAO
          │                  │                  │
          └──────────────────┼──────────────────┘
                             │
                             ▼
                         ConexionBD
                             │
                             ▼
                           JDBC
                             │
                             ▼
                       speedfast_db
                             │
              ┌──────────────┼──────────────┐
              ▼              ▼              ▼
            pedido       repartidor      entrega
```

---

# Flujo completo de una entrega

El ciclo principal de una entrega quedó:

```text
Registrar pedido
       ↓
PENDIENTE
       ↓
Seleccionar pedido
       ↓
Iniciar entrega
       ↓
Seleccionar repartidor
       ↓
Asignar repartidor
       ↓
EN_REPARTO
       ↓
Crear registro de entrega
       ↓
Guardar en MySQL
       ↓
Simular procesamiento
       ↓
ENTREGADO
```

De esta forma, un pedido queda relacionado con el repartidor que realiza la entrega y la información queda almacenada de forma persistente.

---

# Conceptos incorporados en Semana 8

* CRUD.
* Crear registros.
* Consultar registros.
* Actualizar registros.
* Eliminar registros.
* JDBC.
* MySQL.
* `PreparedStatement`.
* `ResultSet`.
* `SQLException`.
* `try-with-resources`.
* Patrón DAO.
* Persistencia de datos.
* Relaciones mediante claves foráneas.
* Java Swing.
* `JFrame`.
* `JDialog`.
* `JTable`.
* `DefaultTableModel`.
* `JTextField`.
* `JPasswordField`.
* `JComboBox`.
* `JButton`.
* `JOptionPane`.
* Validación de datos.
* Gestión de usuarios.
* Autenticación.
* Roles de usuario.
* Gestión de pedidos.
* Gestión de repartidores.
* Gestión de entregas.
* Actualización de estados.
* Refactorización.
* Reducción de acoplamiento.
* Separación de responsabilidades.
* JavaDoc.
* Integración entre GUI, DAO y MySQL.

---

# Evolución del proyecto

```text
Semana 1
   ↓
POO, clases, objetos, herencia,
encapsulamiento y polimorfismo

   ↓

Semana 2
   ↓
Abstracción, métodos abstractos,
Template Method y paquetes

   ↓

Semana 3
   ↓
Interfaces, colecciones,
historial y estados

   ↓

Semana 4
   ↓
Hilos, Runnable,
ExecutorService y concurrencia

   ↓

Semana 5
   ↓
Sincronización, recursos compartidos
y procesamiento concurrente seguro

   ↓

Semana 6
   ↓
Java Swing, GUI, login,
roles y gestión visual

   ↓

Semana 7
   ↓
MySQL, JDBC, DAO,
persistencia de datos

   ↓

Semana 8
   ↓
CRUD completo, validaciones,
gestión de usuarios,
pedidos, repartidores y entregas,
refactorización y documentación
```

---

# Resultado de la Semana 8

Al finalizar la Semana 8, **SpeedFast** cuenta con un ciclo funcional integrado que permite gestionar información mediante una interfaz gráfica conectada a una base de datos MySQL.

La aplicación permite:

* Iniciar sesión con usuarios registrados.
* Controlar funcionalidades mediante roles.
* Registrar usuarios.
* Listar usuarios.
* Editar usuarios.
* Eliminar usuarios.
* Registrar pedidos.
* Listar pedidos.
* Editar pedidos.
* Cancelar pedidos.
* Eliminar pedidos.
* Consultar repartidores.
* Registrar repartidores.
* Editar repartidores.
* Eliminar repartidores.
* Iniciar entregas.
* Asociar pedidos con repartidores.
* Registrar entregas.
* Listar entregas.
* Editar entregas.
* Eliminar entregas.
* Actualizar estados de pedidos.
* Mantener información persistente en MySQL.
* Manejar errores mediante excepciones.
* Validar datos ingresados por el usuario.
* Utilizar DAO para separar el acceso a datos.
* Documentar las principales clases mediante JavaDoc.

El proyecto integra los conocimientos desarrollados durante las ocho semanas:

```text
POO
 ↓
Herencia
 ↓
Polimorfismo
 ↓
Abstracción
 ↓
Interfaces
 ↓
Colecciones
 ↓
Concurrencia
 ↓
Sincronización
 ↓
Java Swing
 ↓
MVC
 ↓
JDBC
 ↓
MySQL
 ↓
DAO
 ↓
CRUD
 ↓
Validaciones
 ↓
Refactorización
 ↓
Documentación JavaDoc
```

De esta manera, **SpeedFast** evoluciona desde una aplicación inicial basada en conceptos de Programación Orientada a Objetos hacia una aplicación Java de escritorio con interfaz gráfica, persistencia de datos, operaciones CRUD, gestión de usuarios, pedidos, repartidores y entregas, utilizando una arquitectura separada por capas.

---

## Tecnologías

* Java
* Maven
* IntelliJ IDEA
* MySQL
* MySQL Connector/J
* JDBC
* Java Swing
* Programación Orientada a Objetos
* Patrón DAO
* CRUD
* Persistencia de datos
* Concurrencia
* Sincronización
* JavaDoc

---

## Autor

**Cristian Contreras**

Duoc UC — 2026

```

**Esta sería la versión que yo dejaría como Semana 8**, porque no solo habla de la eliminación de `ControladorUsuarios`, sino que reúne todo lo que efectivamente trabajaste en esta etapa: **CRUD completo + usuarios + pedidos + repartidores + entregas + validaciones + DAO + JDBC + refactorización + JavaDoc**.
```
