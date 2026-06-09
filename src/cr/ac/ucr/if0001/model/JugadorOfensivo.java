package cr.ac.ucr.if0001.model;

/**
 * <h1>Clase Concreta: JugadorOfensivo</h1>
 * <p><b>Resumen:</b> Subclase de Jugador que representa a un competidor enfocado en la fase
 * de ataque. Introduce un atributo propio de ataque (bonificación) y redefine la lógica
 * de cálculo de rendimiento para ponderar dicha especialidad.</p>
 * 
 * <h2>Concepto Principal (El 'Qué'):</h2>
 * <p>La <b>Herencia</b> (palabra clave <code>extends</code>) es un pilar de la Programación Orientada
 * a Objetos que permite a una clase adquirir los atributos y métodos de otra clase (la superclase o clase padre).
 * La subclase puede añadir nuevos atributos y métodos particulares para modelar la especialización.
 * Además, mediante la <b>Sobreescritura de Métodos</b> (anotación <code>@Override</code>), la subclase
 * reemplaza el comportamiento de un método heredado (en este caso, el método abstracto de la superclase)
 * por una lógica específica propia de su naturaleza.</p>
 * 
 * <h2>Analogía (La Intuición):</h2>
 * <p>Si la superclase <code>Jugador</code> es un auto genérico con ruedas y volante, la subclase 
 * <code>JugadorOfensivo</code> es un carro de carreras deportivo que hereda todo lo del auto básico, pero
 * añade un motor turbocargado (el <i>ataque</i>) para correr más rápido.</p>
 * 
 * <h2>Trampas Comunes (Gotchas):</h2>
 * <ul>
 *   <li><b>Omitir la llamada al Constructor Padre:</b> Dado que la clase padre <code>Jugador</code> no tiene
 *   un constructor por defecto (sin parámetros), la clase hija está obligada a invocar explícitamente a
 *   <code>super(name, skillLevel)</code> en la primera línea de su propio constructor. Omitirlo causará un error
 *   de compilación de inmediato.</li>
 *   <li><b>Tipos de Sobreescritura Inválidos:</b> Al sobreescribir un método, su nombre, tipo de retorno y
 *   parámetros deben ser idénticos al de la clase padre. La anotación <code>@Override</code> sirve como un
 *   seguro en compilación que nos avisa si cometemos un error de tipografía en el nombre del método.</li>
 * </ul>
 * 
 * <h2>Mini-Reto:</h2>
 * <p><b>Detente y Piensa:</b> Si declaramos un atributo en la clase padre como <code>private</code>,
 * ¿puede la clase hija acceder a él directamente por su nombre? <i>Respuesta: No, el encapsulamiento estricto lo impide.
 * Para acceder a un atributo privado del padre desde la hija, se debe usar su getter público (ej. <code>getName()</code>).</i></p>
 */
public class JugadorOfensivo extends Jugador {

    // Atributo específico de la clase hija (Encapsulado)
    private double attackBonus; // Multiplicador de bono ofensivo en el rango [1.0, 2.0]

    /**
     * Constructor para instanciar un Jugador Ofensivo.
     * Invoca al constructor de la clase base mediante 'super()'.
     *
     * @param name Nombre del jugador.
     * @param skillLevel Nivel de habilidad básico [1, 100].
     * @param attackBonus Multiplicador de bono ofensivo (debe estar en el rango [1.0, 2.0]).
     * @throws IllegalArgumentException si el bono de ataque se sale del rango permitido.
     */
    public JugadorOfensivo(String name, int skillLevel, double attackBonus) {
        // Invocación explícita al constructor de la superclase (Jugador)
        super(name, skillLevel);
        setAttackBonus(attackBonus);
    }

    // =========================================================================
    // Getters y Setters Propios - Validación Defensiva
    // =========================================================================

    public double getAttackBonus() {
        return this.attackBonus;
    }

    /**
     * Establece el multiplicador de bono ofensivo con validación de límites.
     *
     * @param attackBonus Multiplicador ofensivo a asignar.
     * @throws IllegalArgumentException si el valor es menor a 1.0 o mayor a 2.0.
     */
    public void setAttackBonus(double attackBonus) {
        if (attackBonus < 1.0 || attackBonus > 2.0) {
            throw new IllegalArgumentException("El bono de ataque debe ser un valor multiplicador entre 1.0 y 2.0.");
        }
        this.attackBonus = attackBonus;
    }

    // =========================================================================
    // Sobreescritura de Métodos (Polimorfismo)
    // =========================================================================

    /**
     * Sobreescritura del método abstracto de la clase base.
     * Calcula el rendimiento final multiplicando la habilidad base por el bono ofensivo.
     * La anotación @Override le indica al compilador que este método reemplaza al del padre.
     *
     * @return El rendimiento real ofensivo.
     */
    @Override
    public double calculatePerformance() {
        // Accedemos a la habilidad base usando el getter heredado del padre,
        // y lo ponderamos por nuestro atributo exclusivo de bonificación.
        return this.getSkillLevel() * this.attackBonus;
    }
}
