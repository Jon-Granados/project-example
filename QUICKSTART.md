# Guía de Inicio Rápido (Quick Start Guide) - EsportsManager 2026

Esta guía está diseñada para que los estudiantes del curso **IF0001** puedan configurar, compilar y ejecutar el proyecto EsportsManager 2026 en pocos minutos.

---

## 1. Requisitos Previos

Antes de comenzar, asegúrese de tener instalado lo siguiente en su computadora:

*   **Java JDK 17 o superior:** Puede verificar si está instalado abriendo su terminal (PowerShell o Símbolo del Sistema) y ejecutando:
    ```bash
    java -version
    javac -version
    ```
    Ambos comandos deben mostrar una versión igual o superior a la `17`. Si no se reconocen, debe descargar e instalar el JDK (por ejemplo, desde [Adoptium Temurin](https://adoptium.net/)) y configurar las variables de entorno (`JAVA_HOME` y `Path`).
*   **Editor de Texto o IDE:** Puede utilizar cualquier editor de código como [Visual Studio Code](https://code.visualstudio.com/), [IntelliJ IDEA](https://www.jetbrains.com/idea/), o incluso un editor de texto plano con la terminal.
*   **Git (Opcional):** Para el control de versiones del proyecto.

---

## 2. Descarga del Proyecto

Si tiene Git instalado, puede clonar el repositorio usando la terminal:
```bash
git clone <url-del-repositorio>
cd Project_help
```
Si descargó el proyecto como un archivo `.zip`, descomprímalo en una carpeta accesible (por ejemplo, en sus Documentos) y abra la terminal dentro de esa carpeta.

---

## 3. Compilación y Ejecución desde la Terminal

El proyecto no requiere de herramientas de construcción complejas como Maven o Gradle, lo que facilita entender el funcionamiento interno del compilador de Java (`javac`).

### Paso A: Compilar el Código
Ejecute el siguiente comando desde la raíz del proyecto (`Project_help`) para compilar todos los archivos `.java` de forma simultánea y guardar los binarios `.class` en la carpeta `bin`:

```powershell
# En Windows (PowerShell o CMD) o Unix (Linux/macOS)
javac -d bin src/cr/ac/ucr/if0001/Main.java src/cr/ac/ucr/if0001/model/*.java src/cr/ac/ucr/if0001/view/*.java src/cr/ac/ucr/if0001/controller/*.java
```

### Paso B: Ejecutar la Aplicación
Una vez compilado, inicie la aplicación indicando la carpeta de binarios (`-cp bin` para indicar el *ClassPath*) y la clase principal con su nombre completamente calificado:

```powershell
java -cp bin cr.ac.ucr.if0001.Main
```

### Paso C: Ejecutar las Pruebas Automatizadas
Para verificar el funcionamiento matemático del modelo (sin interfaz gráfica), puede ejecutar el probador de pruebas unitarias:

```powershell
java -cp bin cr.ac.ucr.if0001.TestRunner
```

---

## 4. Guía de Uso del Simulador

Al iniciar el programa con `java -cp bin cr.ac.ucr.if0001.Main`, se desplegará un menú interactivo en español. A continuación, el flujo recomendado para probar el simulador:

1.  **Opción 1 - Configurar Torneo:** Permite definir el nombre del torneo y el número de equipos participantes.
2.  **Opción 2 - Ver Equipos y Jugadores:** Muestra la lista de equipos creados automáticamente con sus jugadores ofensivos y defensivos, sus estadísticas y niveles de habilidad.
3.  **Opción 3 - Simular Partidas:** Ejecuta los partidos del torneo en formato todos contra todos (Round Robin), calculando las victorias y derrotas mediante un algoritmo basado en probabilidades y la fuerza de los jugadores.
4.  **Opción 4 - Mostrar Tabla de Posiciones:** Muestra la tabla de clasificación ordenada mediante el algoritmo *Bubble Sort*, aplicando desempates detallados en cascada.
5.  **Opción 5 - Ver Matriz de Enfrentamientos:** Muestra una matriz bidimensional con los resultados detallados de cada partido e incluye la suma de la diagonal principal.
6.  **Opción 6 - Registrar Campeón e Historial:** Guarda al ganador del torneo en el archivo físico `campeones.txt`.
7.  **Opción 7 - Mostrar Campeones Históricos:** Lee el archivo `campeones.txt` y muestra en consola los ganadores de torneos anteriores.
8.  **Opción 8 - Salir:** Cierra la aplicación de manera limpia.

---

## 5. Solución de Problemas Comunes (FAQ)

### ¿Error: 'javac' no se reconoce como un comando interno o externo?
Esto significa que el JDK no está instalado o que su ruta no está agregada a la variable de entorno `Path` de su sistema.
*   **Solución:** Instale el JDK 17+ y asegúrese de que el directorio `bin` del JDK (ej. `C:\Program Files\Eclipse Foundation\jdk-17.x.x\bin`) esté en el `Path` de las variables de entorno de Windows. Reinicie la consola después de este cambio.

### ¿Los caracteres especiales (tildes, eñes, colores ANSI) se ven mal en la terminal de Windows?
Las terminales antiguas de Windows (CMD clásico) a veces no admiten la codificación UTF-8 ni los códigos de escape ANSI de forma predeterminada.
*   **Solución:** Use **PowerShell** en lugar de CMD. Si sigue teniendo problemas en Windows 10/11, ejecute el siguiente comando en su PowerShell antes de iniciar el programa para forzar la compatibilidad con UTF-8:
    ```powershell
    [Console]::InputEncoding = [System.Text.Encoding]::UTF8
    [Console]::OutputEncoding = [System.Text.Encoding]::UTF8
    ```
    O bien, pruebe a ejecutar el programa dentro de la terminal integrada de **Visual Studio Code** o **IntelliJ IDEA**, las cuales tienen compatibilidad completa nativa.
