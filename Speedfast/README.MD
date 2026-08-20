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

Durante la Semana 2 se continuó con el desarrollo del proyecto SpeedFast, modificando la estructura de la Semana 1 para implementar una **clase abstracta** y una jerarquía de clases.

La clase `Pedido` pasó a ser una clase abstracta:

public abstract class Pedido