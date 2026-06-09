package cr.ac.ucr.if0001.model;

/**
 * <h1>Clase Concreta: JugadorDefensivo</h1>
 * <p><b>Resumen:</b> Subclase de Jugador que representa a un competidor enfocado en la fase
 * de contención o defensa. Añade un atributo exclusivo de defensa y sobreescribe
 * la lógica de cálculo del rendimiento para ajustarse a dicho rol.</p>
 * 
 * <h2>Concepto Principal (El 'Qué'):</h2>
 * <p>Al igual que la clase ofensiva, esta subclase ejemplifica el concepto de <b>Especialización</b>.
 * Reutiliza en un 100% la estructura del padre <code>Jugador</code> (nombre y habilidad base) y
 * aporta su propio comportamiento para el método abstracto <code>calculatePerformance()</code>.</p>
 * 
 * <h2>Analogía (La Intuición):</h2>
 * <p>Siguiendo con el auto deportivo, el <code>JugadorDefensivo</code> sería como una versión
 * de vehículo blindado todoterreno. Comparte el chasis básico del auto normal, pero añade
 * placas protectoras (el <i>bono de defensa</i>) que le dan una resistencia superior.</p>
 */
public class JugadorDefensivo extends Jugador {

    // Atributo específico encapsulado
    private double defenseBonus; // Multiplicador de bono de defensa en el rango [1.0, 2.0]

    /**
     * Constructor para instanciar un Jugador Defensivo.
     *
     * @param name Nombre del competidor.
     * @param skillLevel Nivel de habilidad básico [1, 100].
     * @param defenseBonus Multiplicador de bono defensivo (debe estar en el rango [1.0, 2.0]).
     * @throws IllegalArgumentException si el bono defensivo está fuera del rango permitido.
     */
    public JugadorDefensivo(String name, int skillLevel, double defenseBonus) {
        super(name, skillLevel);
        setDefenseBonus(defenseBonus);
    }

    // =========================================================================
    // Getters y Setters Propios - Validación Defensiva
    // =========================================================================

    public double getDefenseBonus() {
        return this.defenseBonus;
    }

    /**
     * Establece el multiplicador de bono defensivo con validación de rango.
     *
     * @param defenseBonus Multiplicador defensivo a asignar.
     * @throws IllegalArgumentException si el valor no está en el rango [1.0, 2.0].
     */
    public void setDefenseBonus(double defenseBonus) {
        if (defenseBonus < 1.0 || defenseBonus > 2.0) {
            throw new IllegalArgumentException("El bono de defensa debe ser un valor multiplicador entre 1.0 y 2.0.");
        }
        this.defenseBonus = defenseBonus;
    }

    // =========================================================================
    // Sobreescritura de Métodos (Polimorfismo)
    // =========================================================================

    /**
     * Sobreescritura del método abstracto de la clase base.
     * Multiplica la habilidad base por el bono defensivo para obtener el rendimiento final.
     *
     * @return El rendimiento real defensivo.
     */
    @Override
    public double calculatePerformance() {
        return this.getSkillLevel() * this.defenseBonus;
    }
}
