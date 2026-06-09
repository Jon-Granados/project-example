# Guía de Modificación ("Cómo Hacer...") - EsportsManager 2026

Esta guía práctica contiene tutoriales paso a paso dirigidos a los estudiantes del curso **IF0001** para modificar y expandir el código fuente del proyecto. Estas actividades simulan las tareas típicas de laboratorio, tareas programadas y exámenes prácticos del curso.

---

## 1. Cómo Agregar un Nuevo Tipo de Jugador (Herencia y Polimorfismo)

El proyecto utiliza herencia para modelar la clase abstracta `Jugador` y sus subclases `JugadorOfensivo` y `JugadorDefensivo`. Si desea agregar un nuevo rol, por ejemplo, un **Jugador de Soporte** (`JugadorSoporte`), siga estos pasos:

### Paso 1: Crear la nueva clase
Cree el archivo `JugadorSoporte.java` en la ruta `src/cr/ac/ucr/if0001/model/`. Esta clase debe extender de `Jugador` e implementar el método abstracto `calcularHabilidadEfectiva()`.

```java
package cr.ac.ucr.if0001.model;

/**
 * Clase que representa un jugador especializado en soporte y curación.
 */
public class JugadorSoporte extends Jugador {
    private double asistenciaBonus;

    public JugadorSoporte(String name, int skillLevel, double asistenciaBonus) {
        super(name, skillLevel);
        if (asistenciaBonus < 0) {
            throw new IllegalArgumentException("El bono de asistencia no puede ser negativo.");
        }
        this.asistenciaBonus = asistenciaBonus;
    }

    public double getAsistenciaBonus() {
        return asistenciaBonus;
    }

    @Override
    public double calcularHabilidadEfectiva() {
        // La habilidad efectiva depende de su nivel base más un bono multiplicador de asistencia
        return getSkillLevel() * (1.0 + asistenciaBonus);
    }

    @Override
    public String toString() {
        return super.toString() + " [Soporte | Bono Asist: " + asistenciaBonus + "]";
    }
}
```

### Paso 2: Integrar el nuevo jugador en la generación del equipo
Vaya a [EquipoEsports.java](file:///c:/Users/jonat/OneDrive/Documentos/IF0001_I_2025/Project_help/src/cr/ac/ucr/if0001/model/EquipoEsports.java) e incorpore la instanciación de jugadores de soporte. Por ejemplo, modifique el constructor para que en la composición interna del arreglo de jugadores también se agreguen soportes:

```java
// Dentro del constructor de EquipoEsports o método inicializador:
// Ejemplo de composición mixta:
this.players[0] = new JugadorOfensivo("Ofensivo-" + name, 75, 12.5);
this.players[1] = new JugadorDefensivo("Defensivo-" + name, 80, 8.5);
this.players[2] = new JugadorSoporte("Soporte-" + name, 70, 0.15); // Nuevo
```

---

## 2. Cómo Agregar una Nueva Opción al Menú Interactivo (Patrón MVC)

El proyecto respeta el patrón **Modelo-Vista-Controlador (MVC)**. Para agregar una nueva opción al menú, como por ejemplo "Reiniciar Estadísticas del Torneo", debe realizar cambios en las tres capas:

### Paso 1: Modificar la Capa Modelo (Lógica del Negocio)
En [TorneoEsports.java](file:///c:/Users/jonat/OneDrive/Documentos/IF0001_I_2025/Project_help/src/cr/ac/ucr/if0001/model/TorneoEsports.java), agregue un método público que se encargue de limpiar las estadísticas de los equipos y reiniciar la matriz de resultados:

```java
public void reiniciarTorneo() {
    // 1. Limpiar puntos, victorias y derrotas de cada equipo
    for (EquipoEsports equipo : teams) {
        equipo.reiniciarEstadisticas(); // Deberá crear este método en EquipoEsports
    }
    // 2. Limpiar la matriz de puntuaciones
    for (int i = 0; i < teamCount; i++) {
        for (int j = 0; j < teamCount; j++) {
            scoresMatrix[i][j] = 0;
        }
    }
    // Opcional: limpiar bitácoras de partidos previos
}
```

### Paso 2: Modificar la Capa Vista (Presentación)
En [VistaConsola.java](file:///c:/Users/jonat/OneDrive/Documentos/IF0001_I_2025/Project_help/src/cr/ac/ucr/if0001/view/VistaConsola.java), actualice la impresión del menú e incorpore un método para mostrar el mensaje de confirmación del reinicio:

```java
public void mostrarMenu() {
    // ... código existente ...
    System.out.println("7. Mostrar Campeones Históricos");
    System.out.println("8. Reiniciar Torneo Actual (NUEVA)");
    System.out.println("9. Salir");
}

public void mostrarMensajeReinicioExitoso() {
    imprimirConColor("¡El torneo ha sido reiniciado! Todos los marcadores volvieron a cero.", ANSI_GREEN);
}
```

### Paso 3: Modificar la Capa Controlador (Flujo de Control)
En [TorneoController.java](file:///c:/Users/jonat/OneDrive/Documentos/IF0001_I_2025/Project_help/src/cr/ac/ucr/if0001/controller/TorneoController.java), capture la nueva opción en el bucle `switch` del menú y llame a los métodos correspondientes:

```java
// Ajustar el rango de validación en la captura de la opción:
int opcion = vista.leerOpcionValida(scanner, 1, 9);

switch (opcion) {
    // ... casos anteriores ...
    case 8:
        torneo.reiniciarTorneo();
        vista.mostrarMensajeReinicioExitoso();
        break;
    case 9:
        vista.mostrarMensajeDespedida();
        continuar = false;
        break;
}
```

---

## 3. Cómo Cambiar los Criterios de Ordenamiento (Algoritmo Bubble Sort)

La tabla de posiciones se ordena manualmente en [TorneoEsports.java](file:///c:/Users/jonat/OneDrive/Documentos/IF0001_I_2025/Project_help/src/cr/ac/ucr/if0001/model/TorneoEsports.java) con el método `ordenarTablaPosiciones()`. El algoritmo actual utiliza desempates en cascada: Puntos -> Victorias -> Promedio Habilidad.

Si desea cambiar la lógica para ordenar, por ejemplo, **únicamente por el promedio de nivel de habilidad de los jugadores del equipo** (de mayor a menor), modifique la condición de intercambio (`if`) dentro del Bubble Sort:

```java
// Buscar el método ordenarTablaPosiciones() en TorneoEsports.java
// Reemplazar la condición larga por:
double promedioA = tempTeams[j].obtenerPromedioHabilidad();
double promedioB = tempTeams[j + 1].obtenerPromedioHabilidad();

if (promedioA < promedioB) { // Orden descendente
    // Intercambiar elementos
    EquipoEsports temp = tempTeams[j];
    tempTeams[j] = tempTeams[j + 1];
    tempTeams[j + 1] = temp;
}
```

---

## 4. Cómo Añadir una Validación de Regla de Negocio (Excepciones)

Las excepciones sirven para evitar que los objetos del modelo entren en un estado inválido. Por ejemplo, si quiere validar que ningún equipo pueda registrarse en el torneo con un nombre de menos de 3 caracteres:

1.  Vaya a [EquipoEsports.java](file:///c:/Users/jonat/OneDrive/Documentos/IF0001_I_2025/Project_help/src/cr/ac/ucr/if0001/model/EquipoEsports.java).
2.  En el constructor y en el método `setName(String name)`, agregue la validación defensiva:

```java
public void setName(String name) {
    if (name == null || name.trim().length() < 3) {
        throw new IllegalArgumentException("El nombre del equipo debe tener al menos 3 caracteres reales.");
    }
    this.name = name;
}
```

3.  Al intentar crear un equipo con un nombre corto, Java lanzará automáticamente un `IllegalArgumentException`, evitando corromper la consistencia de los datos del programa.
