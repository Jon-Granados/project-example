# EsportsManager 2026 - Proyecto de Referencia Académica

Este proyecto ha sido desarrollado como material complementario de estudio para el curso **IF0001 - Desarrollo de Software I** de la Universidad de Costa Rica. Su propósito es servir de guía técnica e ilustrar de manera integrada todos los conceptos abordados a lo largo del ciclo lectivo.

El proyecto está diseñado bajo una arquitectura limpia y modular utilizando el patrón de diseño **Modelo-Vista-Controlador (MVC)**, implementado de forma nativa en **Java 17+** y versionado bajo el sistema **Git**.

---

## Documentación y Guías del Proyecto

Para apoyar el aprendizaje de los estudiantes, se han creado guías de estudio específicas y documentación detallada al mismo nivel de este archivo:

*   **[Guía de Inicio Rápido (QUICKSTART.md)](QUICKSTART.md):** Instrucciones rápidas de instalación, comandos de terminal para compilar y ejecutar, y solución a problemas técnicos comunes.
*   **[Guía Práctica - Cómo Hacer... (HOWTO.md)](HOWTO.md):** Tutorial paso a paso para extender y modificar el código (por ejemplo: agregar un nuevo tipo de jugador, añadir opciones al menú de consola o alterar el algoritmo de ordenamiento).
*   **[Guía Conceptual y Temario de Estudio (STUDY_GUIDE.md)](STUDY_GUIDE.md):** Explicación teórica de los conceptos evaluados en el curso aplicados en el proyecto (arquitectura MVC, tipos de relaciones POO, manejo de memoria/referencias con copias defensivas, matrices e hilos).
*   **[Documentación Técnica Javadoc (HTML)](documentation/index.html):** Documentación interactiva de todas las clases, atributos, métodos y firmas de código del simulador.

---

## Temas del Curso Cubiertos en el Código

Para facilitar su navegación y estudio, cada archivo contiene bloques extensos de comentarios en español explicando detalladamente la teoría aplicada, la justificación de la sintaxis exigida por Java y los errores típicos a evitar (*gotchas*). Los temas demostrados son:

1. **Fundamentos de Programación en Java:**
   - Declaración de variables, tipos primitivos (`int`, `double`, `boolean`, `char`) y cadenas de texto (`String`).
   - Operadores aritméticos, lógicos y relacionales.
   - Flujo de control iterativo y selectivo (`for`, `while`, `if`, `else if`, `switch`).
   - Definición y reutilización de métodos estáticos y no estáticos con parámetros y retornos.

2. **Estructuras de Datos Lineales y Bidimensionales:**
   - **Arreglos Lineales (`[]`):** Manejo de capacidad física de tamaño fijo para coleccionar objetos (por ejemplo, los miembros de un equipo o los equipos de un torneo).
   - **Matrices Bidimensionales (`[][]`):** Uso de una matriz de resultados cruzados. Incluye una demostración didáctica de un recorrido por filas y columnas y la **suma de los valores en la diagonal principal** de una matriz cuadrada.
   - **Algoritmo de Ordenamiento Manual (Bubble Sort):** Lógica avanzada de ordenamiento burbuja sin utilizar librerías del sistema, aplicando **tres criterios de desempate en cascada** (Puntos -> Victorias -> Promedio de Habilidad).

3. **Programación Orientada a Objetos (POO):**
   - **Clases y Objetos:** Modelado del dominio del problema e instanciación de clases en memoria.
   - **Encapsulamiento y Modificadores de Acceso:** Uso estricto de atributos privados (`private`) y métodos de acceso públicos (`public`).
   - **Validación Defensiva:** Métodos mutadores (setters) que rechazan estados inválidos mediante el lanzamiento de excepciones.
   - **Relaciones entre Clases:**
     - **Composición Fuerte:** El objeto contenedor gestiona el ciclo de vida de los objetos contenidos (los jugadores nacen internamente dentro del constructor de un equipo).
     - **Agregación:** El objeto agregador almacena referencias de objetos independientes (el torneo agrupa equipos preexistentes).
     - **Asociación Débil:** Relación temporal de uso (un partido asocia dos equipos para simular un juego).
   - **Copias Defensivas:** Retorno de copias independientes de objetos y arreglos en los métodos accesores para evitar la pérdida del encapsulamiento por fuga de referencias de memoria RAM.
   - **Herencia y Polimorfismo:** Especialización de jugadores a través de una clase abstracta (`Jugador`) y clases hijas concretas (`JugadorOfensivo` y `JugadorDefensivo`).
   - **Clases Abstractas:** Definición de contratos algorítmicos comunes (`abstract class`).
   - **Sobreescritura de Métodos:** Redefinición del comportamiento de métodos heredados usando la anotación `@Override`.

4. **Manejo de Excepciones y Robustez:**
   - Validación de tipos y saneamiento del buffer de lectura mediante la captura de excepciones (`try-catch`) sobre objetos `Scanner`.
   - Lanzamiento explícito de excepciones de tiempo de ejecución (`IllegalArgumentException` e `IllegalStateException`) ante infracciones de la lógica de negocio.

5. **Persistencia de Archivos con Java NIO (Entrada/Salida):**
   - Representación lógica de ubicaciones físicas mediante la interfaz `java.nio.file.Path`.
   - Lectura y escritura rápida en disco utilizando los métodos estáticos de `java.nio.file.Files`.
   - Modos de apertura avanzados (`StandardOpenOption.APPEND` y `StandardOpenOption.CREATE`) para el registro acumulativo de campeones históricos en el archivo local `campeones.txt`.

6. **Interfaz de Consola Premium y Simulación Probabilística:**
   - Uso de `java.util.Random` para simular la resolución estadística de partidos basándose en los niveles de ataque y defensa de los equipos.
   - Formateo espacial riguroso de tablas mediante `System.out.printf()`.
   - Generación de tensión y dinamismo visual usando pausas de tiempo controladas mediante `Thread.sleep()`.
   - Códigos de color ANSI para embellecer los textos impresos en la consola.

---

## Estructura de Directorios del Código Fuente

El proyecto se encuentra dividido siguiendo estrictamente el patrón **Modelo-Vista-Controlador (MVC)**, el cual exige separar la lógica interna matemática de las tareas de visualización en la terminal:

```text
src/
└── cr/
    └── ac/
        └── ucr/
            └── if0001/
                ├── Main.java       # Orquestador del programa
                │
                ├── model/          # CAPA MODELO (Lógica interna, sin System.out ni Scanner)
                │   ├── Jugador.java
                │   ├── JugadorOfensivo.java
                │   ├── JugadorDefensivo.java
                │   ├── EquipoEsports.java
                │   ├── PartidaEsports.java
                │   └── TorneoEsports.java
                │
                ├── view/           # CAPA VISTA (Salida a pantalla y formateo con printf)
                │   └── VistaConsola.java
                │
                └── controller/     # CAPA CONTROLADOR (Lectura de Scanner y flujo del menú)
                    └── TorneoController.java
```

---

## Instrucciones de Compilación y Ejecución

Usted puede compilar y ejecutar este proyecto de forma directa desde la terminal o PowerShell del sistema operativo sin necesidad de un entorno gráfico.

### 1. Compilación
Abra la consola de comandos en la carpeta raíz del proyecto (`Project_help`) y ejecute:
```bash
javac -d bin src/cr/ac/ucr/if0001/Main.java src/cr/ac/ucr/if0001/model/*.java src/cr/ac/ucr/if0001/view/*.java src/cr/ac/ucr/if0001/controller/*.java
```
Esto compilará todas las clases de manera ordenada y creará los archivos binarios `.class` dentro del directorio `bin/`.

### 2. Ejecución
Para arrancar el simulador interactivo, ejecute el siguiente comando indicando la ruta de la clase principal:
```bash
java -cp bin cr.ac.ucr.if0001.Main
```

### 3. Ejecución de Pruebas Automatizadas
El proyecto incluye un arnés de pruebas automatizadas en `TestRunner.java` para verificar de forma inmediata y matemática el correcto funcionamiento del modelo:
```bash
java -cp bin cr.ac.ucr.if0001.TestRunner
```
