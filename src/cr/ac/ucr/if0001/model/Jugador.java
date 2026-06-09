package cr.ac.ucr.if0001.model;

/**
 * <h1>Clase Abstracta: Jugador</h1>
 * <p><b>Resumen:</b> Esta clase representa la abstracción base de un competidor en el simulador
 * de Esports. Define los atributos comunes a todos los jugadores y establece el contrato polimórfico
 * para calcular su rendimiento en una partida.</p>
 * 
 * <h2>Concepto Principal (El 'Qué'):</h2>
 * <p>Una <b>Clase Abstracta</b> en Java (declarada con la palabra clave <code>abstract</code>) es una clase
 * parcial que no puede ser instanciada directamente en la memoria RAM (no es posible hacer <code>new Jugador()</code>).
 * Su propósito es servir como una <i>superclase</i> o plantilla común para estructurar una jerarquía de herencia. Permite
 * agrupar atributos y comportamientos comunes mientras define <b>métodos abstractos</b> (métodos sin cuerpo)
 * que las subclases concretas están obligadas a implementar (sobreescribir) bajo sus propias reglas.</p>
 * 
 * <h2>Analogía (La Intuición):</h2>
 * <p>Imaginen que están diseñando una base de datos para una veterinaria. El concepto de "Animal" es demasiado abstracto;
 * no existe un "Animal" genérico caminando por el mundo, siempre es un perro, un gato o un ave en concreto. Así,
 * <code>Jugador</code> es la idea abstracta de un participante de Esports, pero en la realidad, cada jugador tiene un rol
 * especializado dentro del juego (por ejemplo, atacante o defensor).</p>
 * 
 * <h2>Trampas Comunes (Gotchas):</h2>
 * <ul>
 *   <li><b>Intentar Instanciar la Clase Abstracta:</b> Escribir <code>Jugador j = new Jugador("Carlos", 80);</code>
 *   provocará un error en tiempo de compilación. Las clases abstractas solo existen para ser heredadas por clases concretas.</li>
 *   <li><b>Olvidar Implementar los Métodos Abstractos:</b> Si una subclase hereda de una clase abstracta y no implementa
 *   el método abstracto <code>calculatePerformance()</code>, el compilador obligará a declarar la subclase también como abstracta.</li>
 * </ul>
 * 
 * <h2>Mini-Reto:</h2>
 * <p><b>Detente y Piensa:</b> Si agregamos un método concreto (con llaves <code>{}</code>) en esta clase abstracta,
 * ¿las subclases están obligadas a sobreescribirlo? <i>Respuesta: No, los métodos concretos se heredan automáticamente,
 * permitiendo la reutilización del código. Solo los métodos abstractos exigen sobreescritura obligatoria.</i></p>
 */
public abstract class Jugador {

    // Atributos privados: Aplicación estricta de Encapsulamiento.
    // Solo son accesibles directamente por esta clase.
    private String name;
    private int skillLevel; // Nivel de habilidad general en el rango [1, 100]
    private int pointsScored; // Estadísticas acumulativas de puntos ganados

    /**
     * Constructor para inicializar los atributos comunes de un Jugador.
     * Es invocado por las subclases concretas mediante la llamada 'super()'.
     *
     * @param name Nombre del jugador (no debe ser nulo o vacío).
     * @param skillLevel Nivel de habilidad del jugador (debe estar entre 1 y 100).
     * @throws IllegalArgumentException si los datos no cumplen con las validaciones de rango y formato.
     */
    public Jugador(String name, int skillLevel) {
        setName(name);
        setSkillLevel(skillLevel);
        this.pointsScored = 0; // Inicia en cero puntos acumulados
    }

    // =========================================================================
    // Métodos Accesores (Getters) y Mutadores (Setters) - Programación Defensiva
    // =========================================================================

    public String getName() {
        return this.name;
    }

    /**
     * Asigna el nombre del jugador realizando una validación defensiva.
     *
     * @param name El nombre a asignar.
     * @throws IllegalArgumentException si el nombre es nulo o contiene solo espacios en blanco.
     */
    public void setName(String name) {
        if (name == null || name.strip().isEmpty()) {
            throw new IllegalArgumentException("El nombre del jugador es obligatorio y no puede estar vacío.");
        }
        this.name = name;
    }

    public int getSkillLevel() {
        return this.skillLevel;
    }

    /**
     * Asigna el nivel de habilidad validando que se encuentre dentro del rango válido de 1 a 100.
     *
     * @param skillLevel Nivel de habilidad a asignar.
     * @throws IllegalArgumentException si el valor es menor que 1 o mayor que 100.
     */
    public void setSkillLevel(int skillLevel) {
        if (skillLevel < 1 || skillLevel > 100) {
            throw new IllegalArgumentException("El nivel de habilidad debe estar estrictamente en el rango de 1 a 100.");
        }
        this.skillLevel = skillLevel;
    }

    public int getPointsScored() {
        return this.pointsScored;
    }

    /**
     * Incrementa de manera controlada los puntos anotados por el jugador durante el torneo.
     *
     * @param points Puntos a sumar (deben ser mayores o iguales a cero).
     * @throws IllegalArgumentException si los puntos a sumar son negativos.
     */
    public void addPoints(int points) {
        if (points < 0) {
            throw new IllegalArgumentException("No se pueden sumar puntos negativos.");
        }
        this.pointsScored += points;
    }

    // =========================================================================
    // Métodos Polimórficos Contractuales
    // =========================================================================

    /**
     * Método Abstracto. Define el contrato para que cada tipo de jugador
     * calcule su capacidad real de desempeño en base a su nivel base y su especialización.
     * No posee cuerpo (implementación) en esta clase.
     *
     * @return El rendimiento calculado como un valor decimal de punto flotante de doble precisión (double).
     */
    public abstract double calculatePerformance();
}
