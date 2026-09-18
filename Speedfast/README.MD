# Semana 1 a 6 – Programación Orientada a Objetos II

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


**Autor**

Cristian Contreras

Duoc UC — 2026