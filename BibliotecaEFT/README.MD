# 📚 BibliotecaEFT

Sistema de Gestión de Biblioteca Escolar desarrollado en Java como proyecto final de la asignatura **Programación Orientada a Objetos II**.

El proyecto integra programación orientada a objetos, arquitectura MVC, operaciones CRUD, acceso a datos mediante DAO, conexión JDBC con MySQL, patrón Singleton, programación concurrente mediante `Thread` y `synchronized`, además de autenticación y diferenciación de funcionalidades según el rol del usuario.

## 🔗 Repositorio

[Repositorio BibliotecaEFT](https://github.com/cncchild/poo-2-tercer-bimestre-2026/tree/main/BibliotecaEFT)

## 🎯 Objetivo del proyecto

Desarrollar una aplicación de escritorio que permita gestionar los principales procesos de una biblioteca escolar, facilitando la administración de libros, estudiantes, usuarios, categorías y préstamos.

El sistema permite mantener la información almacenada de manera persistente en una base de datos MySQL y acceder a ella mediante una aplicación desarrollada en Java.

## 🛠️ Tecnologías utilizadas

* Java 23
* Java Swing
* JDBC
* MySQL
* Maven
* IntelliJ IDEA
* GitHub
* MySQL Connector/J

## 🏗️ Arquitectura del proyecto

El proyecto se encuentra organizado mediante paquetes que separan las responsabilidades principales de la aplicación:

```text
BibliotecaEFT
└── src
    └── main
        └── java
            └── cl.bibliotecaEFT
                ├── app
                ├── controlador
                ├── modelo
                ├── dao
                ├── conexion
                ├── tareas
                └── vista
```

### Modelo

Contiene las clases que representan las principales entidades del sistema:

* `Usuario`
* `Estudiante`
* `Libro`
* `Categoria`
* `Prestamo`

### Controladores

Coordinan las operaciones realizadas desde la interfaz gráfica:

* `UsuarioController`
* `EstudianteController`
* `LibroController`
* `PrestamoController`

### DAO

Las clases DAO se encargan del acceso y persistencia de información en la base de datos:

* `UsuarioDAO`
* `EstudianteDAO`
* `LibroDAO`
* `CategoriaDAO`
* `PrestamoDAO`

### Conexión

La clase `DatabaseConnection` centraliza la conexión entre Java y MySQL utilizando el patrón **Singleton**.

### Tareas

El paquete contiene `ProcesarPrestamo`, clase que implementa `Runnable` y permite procesar el registro de préstamos mediante un `Thread`.

### Vista

Contiene las interfaces gráficas desarrolladas mediante Java Swing, entre ellas:

* `Login`
* `Home`
* `GestionLibros`
* `GestionEstudiantes`
* `GestionPrestamos`
* `AgregarLibro`
* `EditarLibro`
* `AgregarEstudiante`
* `EditarEstudiante`
* `AgregarPrestamo`
* `MisPrestamos`
* `Reportes`

## 🗄️ Base de datos

La aplicación utiliza una base de datos MySQL denominada:

```text
biblioteca
```

Las principales tablas utilizadas son:

```text
biblioteca
├── usuarios
├── estudiantes
├── libros
├── categorias
└── prestamos
```

### Relaciones principales

* Una categoría puede tener varios libros.
* Un estudiante puede tener varios préstamos.
* Un libro puede estar asociado a varios préstamos.
* Los préstamos relacionan estudiantes y libros.
* Los usuarios mantienen la información necesaria para la autenticación y los roles del sistema.

## 🔄 Operaciones CRUD

El sistema implementa operaciones **CRUD (Create, Read, Update, Delete)** para administrar la información principal de la biblioteca.

### Crear

Permite registrar:

* Libros
* Estudiantes
* Préstamos
* Usuarios
* Categorías

### Leer / Consultar

Permite:

* Listar libros
* Consultar estudiantes
* Consultar préstamos
* Consultar usuarios
* Consultar categorías
* Revisar información mediante reportes

### Actualizar

Permite:

* Editar libros
* Editar estudiantes
* Actualizar información de usuarios
* Registrar devoluciones
* Actualizar el estado de los préstamos

### Eliminar

Permite eliminar registros cuando las restricciones de integridad de la base de datos lo permiten.

## 👥 Autenticación y roles

El sistema incorpora autenticación de usuarios y diferenciación de funcionalidades según el rol.

### Bibliotecario

Puede realizar operaciones de administración como:

* Gestionar libros
* Gestionar estudiantes
* Gestionar préstamos
* Consultar reportes

### Estudiante

Puede acceder a funcionalidades relacionadas con sus préstamos, incluyendo:

* Consultar préstamos
* Registrar devoluciones
* Consultar la información disponible para su rol

## 🧩 Patrón MVC

El proyecto utiliza el patrón **Modelo-Vista-Controlador (MVC)** para separar responsabilidades.

```text
Vista
  ↓
Controlador
  ↓
Modelo / DAO
  ↓
Base de datos
```

La **Vista** corresponde a las interfaces gráficas, los **Controladores** coordinan las acciones y el **Modelo** representa las entidades del sistema.

El patrón DAO complementa esta arquitectura encargándose de las operaciones de persistencia.

## 🔌 DAO + JDBC + Singleton

Los DAO utilizan **JDBC** para ejecutar las operaciones SQL sobre MySQL.

```text
Vista
  ↓
Controlador
  ↓
DAO
  ↓
DatabaseConnection
  ↓
JDBC
  ↓
MySQL
```

La clase `DatabaseConnection` utiliza el patrón **Singleton**, permitiendo centralizar la creación y reutilización de la conexión a la base de datos.

Para las consultas se utilizan elementos de JDBC como:

* `Connection`
* `PreparedStatement`
* `ResultSet`
* `SQLException`

Además, se utiliza `try-with-resources` para administrar correctamente los recursos asociados a las operaciones de base de datos.

## 🧵 Thread y synchronized

El sistema incorpora programación concurrente para el procesamiento de préstamos.

Cuando se registra un préstamo, se crea una instancia de `ProcesarPrestamo`, que implementa `Runnable`, y posteriormente se ejecuta mediante un `Thread`.

```text
AgregarPrestamo
      ↓
ProcesarPrestamo
      ↓
Runnable
      ↓
Thread
      ↓
synchronized
      ↓
Control de stock
      ↓
Registrar préstamo
```

El uso de `synchronized` permite proteger las operaciones relacionadas con el stock, evitando modificaciones simultáneas que puedan generar inconsistencias.

## 📦 Control de stock

El sistema controla el stock disponible de los libros durante las operaciones de préstamo y devolución.

Al registrar un préstamo:

```text
Stock disponible
      ↓
Validación
      ↓
Disminución del stock
      ↓
Registro del préstamo
```

Al realizar una devolución:

```text
Devolución
      ↓
Actualización del préstamo
      ↓
Aumento del stock
```

Esto permite mantener sincronizada la información entre los préstamos registrados y la disponibilidad de los libros.

## 🖥️ Interfaz gráfica

La aplicación fue desarrollada utilizando **Java Swing**.

Entre los principales componentes utilizados se encuentran:

* `JFrame`
* `JPanel`
* `JTable`
* `JTextField`
* `JPasswordField`
* `JComboBox`
* `JButton`
* `JOptionPane`

La interfaz incorpora validaciones y mensajes para informar al usuario sobre las operaciones realizadas.

## 🧪 Pruebas realizadas

Durante el desarrollo se realizaron pruebas sobre las principales funcionalidades:

* Inicio de sesión.
* Autenticación según rol.
* Registro de libros.
* Consulta de libros.
* Edición de libros.
* Eliminación de registros.
* Registro de estudiantes.
* Gestión de estudiantes.
* Registro de préstamos.
* Control de stock.
* Registro de devoluciones.
* Actualización del stock.
* Consulta de préstamos.
* Conexión con MySQL.
* Operaciones mediante los DAO.
* Procesamiento mediante `Thread`.
* Control de concurrencia mediante `synchronized`.

## 📋 Conceptos aplicados

El proyecto integra los principales contenidos trabajados durante la asignatura:

* Programación Orientada a Objetos.
* Encapsulamiento.
* Herencia y clases.
* Arquitectura MVC.
* Patrón DAO.
* Patrón Singleton.
* CRUD.
* JDBC.
* MySQL.
* `PreparedStatement`.
* `ResultSet`.
* Manejo de excepciones.
* Java Swing.
* `Runnable`.
* `Thread`.
* `synchronized`.
* Persistencia de datos.
* Validación de información.
* Autenticación y roles.

## ▶️ Ejecución del proyecto

Para ejecutar el proyecto se requiere:

1. Tener instalado **JDK 23**.
2. Tener instalado **MySQL**.
3. Crear la base de datos `biblioteca`.
4. Crear las tablas necesarias.
5. Configurar las credenciales de conexión en `DatabaseConnection`.
6. Ejecutar el proyecto Maven.

La clase principal de la aplicación se encuentra en:

```text
cl.bibliotecaEFT.app.Main
```

## 👨‍💻 Autor

**Cristian Contreras**

Duoc UC — Desarrollo de Aplicaciones
Programación Orientada a Objetos II
2026
