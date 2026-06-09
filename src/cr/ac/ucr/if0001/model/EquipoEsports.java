package cr.ac.ucr.if0001.model;

/**
 * <h1>Clase: EquipoEsports</h1>
 * <p><b>Resumen:</b> Modela un equipo de Esports en el simulador. Almacena su nombre,
 * estadísticas de torneo y una colección de jugadores de tamaño fijo mediante un arreglo lineal.</p>
 * 
 * <h2>Concepto Principal (El 'Qué'):</h2>
 * <p>Esta clase ilustra tres conceptos avanzados del diseño de software:
 * <ul>
 *   <li><b>Composición Fuerte:</b> El ciclo de vida de los jugadores está atado al del equipo.
 *   Los objetos de tipo <code>Jugador</code> se instancian directamente en la clase <code>EquipoEsports</code>
 *   mediante el método <code>addPlayer(...)</code> el cual recibe solo datos primitivos. El exterior no manipula
 *   las referencias iniciales de creación.</li>
 *   <li><b>Arreglos de Objetos:</b> Uso de un arreglo lineal <code>Jugador[]</code> con una capacidad física
 *   fija (3 celdas). Se implementan validaciones para evitar desbordamientos de memoria física y punteros nulos.</li>
 *   <li><b>Copias Defensivas (Defense Copies):</b> En los métodos de retorno (getters), en lugar de entregar
 *   la dirección de memoria directa de nuestro arreglo interno o de las instancias de los jugadores (lo cual provocaría
 *   <b>fuga de referencias</b> y rompería el encapsulamiento), se crea un nuevo arreglo y copias (clones)
 *   de cada jugador para entregarlas de forma segura.</li>
 * </ul>
 * </p>
 * 
 * <h2>Analogía (La Intuición):</h2>
 * <p>Piensen en una <b>Caja Fuerte</b> que contiene joyas (jugadores). Si alguien les pide ver las joyas,
 * ustedes no les entregan la llave de la caja fuerte ni las joyas originales para que se las lleven a casa
 * (eso sería retornar la referencia original y permitir que las dañen o roben). En su lugar, sacan fotos idénticas
 * en 3D de alta resolución (las copias defensivas) y se las entregan. La caja fuerte sigue herméticamente protegida.</p>
 * 
 * <h2>Trampas Comunes (Gotchas):</h2>
 * <ul>
 *   <li><b>Fuga de Referencia Directa:</b> Retornar <code>return this.members;</code> permite que un código
 *   externo haga <code>equipo.getMembers()[0] = null;</code>, borrando un jugador en la base del equipo de forma silenciosa.</li>
 *   <li><b>NullPointerException en Bucles:</b> Al recorrer arreglos físicos fijos incompletos, si una celda no ha sido inicializada,
 *   intentar acceder a ella provocará que el programa explote. Es obligatorio comprobar siempre si <code>members[i] != null</code>.</li>
 * </ul>
 */
public class EquipoEsports {

    private String name;
    private Jugador[] members; // Arreglo unidimensional de objetos
    private int memberCount;   // Contador lógico de elementos insertados
    
    // Estadísticas de Torneo
    private int tournamentPoints;
    private int matchesWon;
    private int matchesLost;

    /**
     * Constructor para inicializar un Equipo de Esports con capacidad fija de 3 miembros.
     *
     * @param name Nombre del equipo.
     */
    public EquipoEsports(String name) {
        setName(name);
        this.members = new Jugador[3]; // Límite físico de 3 competidores por equipo
        this.memberCount = 0;
        this.tournamentPoints = 0;
        this.matchesWon = 0;
        this.matchesLost = 0;
    }

    // =========================================================================
    // Métodos Accesores y Mutadores con Validación Defensiva
    // =========================================================================

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        if (name == null || name.strip().isEmpty()) {
            throw new IllegalArgumentException("El nombre del equipo no puede estar vacío.");
        }
        this.name = name;
    }

    public int getTournamentPoints() {
        return this.tournamentPoints;
    }

    public int getMatchesWon() {
        return this.matchesWon;
    }

    public int getMatchesLost() {
        return this.matchesLost;
    }

    public int getMemberCount() {
        return this.memberCount;
    }

    /**
     * Acumula estadísticas de resultado de partidas en el torneo de forma controlada.
     *
     * @param points Puntos a sumar (3 por victoria, 0 por derrota).
     * @param won Indica si ganó la partida.
     */
    public void recordResult(int points, boolean won) {
        if (points < 0) {
            throw new IllegalArgumentException("Los puntos de resultado no pueden ser negativos.");
        }
        this.tournamentPoints += points;
        if (won) {
            this.matchesWon++;
        } else {
            this.matchesLost++;
        }
    }

    // =========================================================================
    // Lógica de Composición Fuerte y Copias Defensivas
    // =========================================================================

    /**
     * <b>Composición Fuerte:</b> Este método recibe valores primitivos, decide
     * internamente qué objeto instanciar, y lo almacena dentro de su arreglo privado.
     * El exterior jamás maneja la referencia del jugador creado.
     *
     * @param name Nombre del jugador.
     * @param skillLevel Habilidad del jugador.
     * @param bonus Multiplicador de bono ofensivo o defensivo.
     * @param isOffensive Si es verdadero instancia JugadorOfensivo, sino JugadorDefensivo.
     * @throws IllegalStateException si el arreglo ya alcanzó su capacidad máxima de 3.
     */
    public void addPlayer(String name, int skillLevel, double bonus, boolean isOffensive) {
        if (this.memberCount >= this.members.length) {
            throw new IllegalStateException("Límite excedido: El equipo de Esports ya cuenta con 3 miembros.");
        }

        // Instanciación controlada y encapsulada
        if (isOffensive) {
            this.members[this.memberCount] = new JugadorOfensivo(name, skillLevel, bonus);
        } else {
            this.members[this.memberCount] = new JugadorDefensivo(name, skillLevel, bonus);
        }
        
        this.memberCount++;
    }

    /**
     * <b>Copia Defensiva:</b> Crea un nuevo arreglo y copia cada elemento individual
     * duplicando la instancia exacta del objeto según su tipo real (polimorfismo).
     * Esto impide fugas de referencia a la memoria interna del equipo.
     *
     * @return Un arreglo con copias independientes de los jugadores agregados hasta el momento.
     */
    public Jugador[] getMembers() {
        // Retornamos un arreglo ajustado solo a los jugadores reales agregados (tamaño lógico)
        Jugador[] copies = new Jugador[this.memberCount];

        for (int i = 0; i < this.memberCount; i++) {
            Jugador original = this.members[i];
            
            // Verificación segura y clonación manual de subclases
            if (original instanceof JugadorOfensivo) {
                JugadorOfensivo of = (JugadorOfensivo) original;
                JugadorOfensivo copy = new JugadorOfensivo(of.getName(), of.getSkillLevel(), of.getAttackBonus());
                copy.addPoints(of.getPointsScored()); // Preserva los puntos acumulados por el jugador
                copies[i] = copy;
            } else if (original instanceof JugadorDefensivo) {
                JugadorDefensivo df = (JugadorDefensivo) original;
                JugadorDefensivo copy = new JugadorDefensivo(df.getName(), df.getSkillLevel(), df.getDefenseBonus());
                copy.addPoints(df.getPointsScored());
                copies[i] = copy;
            }
        }
        return copies;
    }

    /**
     * Calcula de forma agregada el promedio de la capacidad competitiva de todos
     * los miembros actuales del equipo para ser usado en las simulaciones de juego.
     *
     * @return Promedio de rendimiento decimal. Retorna 0.0 si el equipo no posee jugadores.
     */
    public double calculateTeamPerformance() {
        if (this.memberCount == 0) {
            return 0.0;
        }

        double total = 0.0;
        for (int i = 0; i < this.memberCount; i++) {
            if (this.members[i] != null) {
                // Polimorfismo en acción: cada subclase calcula el rendimiento de forma distinta
                total += this.members[i].calculatePerformance();
            }
        }
        return total / this.memberCount;
    }

    /**
     * Permite sumar puntos individuales al jugador estrella del equipo en un partido.
     *
     * @param playerIndex Índice lógico del jugador en el arreglo [0, memberCount-1].
     * @param points Puntos anotados a agregar.
     */
    public void rewardPlayer(int playerIndex, int points) {
        if (playerIndex < 0 || playerIndex >= this.memberCount) {
            throw new IllegalArgumentException("Índice de jugador inválido en el equipo.");
        }
        this.members[playerIndex].addPoints(points);
    }
}
