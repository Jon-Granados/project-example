package cr.ac.ucr.if0001.model;

import java.util.Random;

/**
 * <h1>Clase: PartidaEsports</h1>
 * <p><b>Resumen:</b> Modela un enfrentamiento deportivo entre dos equipos. Contiene las referencias
 * de los equipos rivales, sus puntajes respectivos en el partido y una bitácora o reporte textual
 * de los acontecimientos del encuentro.</p>
 * 
 * <h2>Concepto Principal (El 'Qué'):</h2>
 * <p>Esta clase demuestra la relación de <b>Asociación Débil</b>. Los equipos contrincantes
 * (<code>EquipoEsports</code>) son recibidos como parámetros en el constructor desde el exterior del objeto.
 * La partida NO crea a los equipos, únicamente los conoce de forma temporal para realizar la simulación.
 * Si el objeto <code>PartidaEsports</code> es destruido por el <i>Garbage Collector</i> (recolector de basura)
 * de Java, las instancias de los equipos seguirán vivas de manera independiente en la RAM.
 * <br>
 * Asimismo, introduce el uso de la clase utilitaria <code>java.util.Random</code> para crear
 * <b>Simulaciones Probabilísticas</b> que calculan dinámicamente quién gana basándose en el desempeño
 * matemático del equipo.</p>
 * 
 * <h2>Analogía (La Intuición):</h2>
 * <p>La <code>PartidaEsports</code> es como un <b>Árbitro de un partido</b>. El árbitro no es dueño de los jugadores
 * ni de los clubes (composición), ni los clubes pertenecen a su oficina. Los equipos entran a la cancha, el árbitro
 * coordina el juego durante 90 minutos y, al finalizar, los equipos regresan a sus sedes intactos mientras el árbitro
 * se retira.</p>
 */
public class PartidaEsports {

    // Relaciones de Asociación Débil (conocimiento temporal sin posesión)
    private final EquipoEsports homeTeam;
    private final EquipoEsports awayTeam;
    
    private int homeScore;
    private int awayScore;
    private boolean played;
    private String matchLog; // Crónica textual del partido

    /**
     * Constructor de la partida asociando dos equipos preexistentes.
     *
     * @param homeTeam Equipo local.
     * @param awayTeam Equipo visitante.
     * @throws IllegalArgumentException si alguno de los equipos es nulo o si intentan jugar contra sí mismos.
     */
    public PartidaEsports(EquipoEsports homeTeam, EquipoEsports awayTeam) {
        if (homeTeam == null || awayTeam == null) {
            throw new IllegalArgumentException("Los equipos contrincantes no pueden ser nulos.");
        }
        if (homeTeam.getName().equals(awayTeam.getName())) {
            throw new IllegalArgumentException("Un equipo no puede jugar contra sí mismo.");
        }
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = 0;
        this.awayScore = 0;
        this.played = false;
        this.matchLog = "Partida pendiente por disputar.";
    }

    // =========================================================================
    // Getters y Setters
    // =========================================================================

    public EquipoEsports getHomeTeam() {
        return this.homeTeam;
    }

    public EquipoEsports getAwayTeam() {
        return this.awayTeam;
    }

    public int getHomeScore() {
        return this.homeScore;
    }

    public int getAwayScore() {
        return this.awayScore;
    }

    public boolean isPlayed() {
        return this.played;
    }

    public String getMatchLog() {
        return this.matchLog;
    }

    // =========================================================================
    // Simulación del Juego
    // =========================================================================

    /**
     * Ejecuta el juego simulando una serie de rondas competitivas con aleatoriedad.
     * Pondera las probabilidades de victoria basándose en el rendimiento agregado de los jugadores.
     *
     * @param rand Instancia de Random compartida para controlar la semilla probabilística.
     * @throws IllegalStateException si la partida ya ha sido simulada con anterioridad.
     */
    public void play(Random rand) {
        if (this.played) {
            throw new IllegalStateException("Esta partida ya ha sido jugada.");
        }

        // Obtener el nivel de rendimiento de los equipos (Modelo Matemático)
        double homePerf = this.homeTeam.calculateTeamPerformance();
        double awayPerf = this.awayTeam.calculateTeamPerformance();

        StringBuilder logBuilder = new StringBuilder();
        logBuilder.append(String.format("INICIO DEL ENCUENTRO: %s vs %s%n", this.homeTeam.getName(), this.awayTeam.getName()));
        logBuilder.append(String.format("[Estadísticas] Rendimiento promedio: %s (%.2f) | %s (%.2f)%n", 
                this.homeTeam.getName(), homePerf, this.awayTeam.getName(), awayPerf));

        // Simulación de 5 rondas de combate
        int homeRounds = 0;
        int awayRounds = 0;

        for (int round = 1; round <= 5; round++) {
            // Factor aleatorio en el rango [0.0, 50.0] para simular imprevistos o nervios del torneo
            double luckHome = rand.nextDouble() * 50.0;
            double luckAway = rand.nextDouble() * 50.0;

            double totalHome = homePerf + luckHome;
            double totalAway = awayPerf + luckAway;

            if (totalHome > totalAway) {
                homeRounds++;
                logBuilder.append(String.format(" - Ronda %d: ¡Punto anotado por %s!%n", round, this.homeTeam.getName()));
            } else {
                awayRounds++;
                logBuilder.append(String.format(" - Ronda %d: ¡Punto anotado por %s!%n", round, this.awayTeam.getName()));
            }
        }

        this.homeScore = homeRounds;
        this.awayScore = awayRounds;
        this.played = true;

        // Registro de estadísticas individuales (Anclaje al Mundo Real)
        // Recompensar al azar a uno de los miembros de cada equipo que anotó en la ronda
        if (homeRounds > 0 && this.homeTeam.getMemberCount() > 0) {
            int luckyIndex = rand.nextInt(this.homeTeam.getMemberCount());
            this.homeTeam.rewardPlayer(luckyIndex, homeRounds); // Suma estadísticas individuales
            logBuilder.append(String.format("[MVP Local] El jugador de %s destacó anotando %d puntos.%n", 
                this.homeTeam.getName(), homeRounds));
        }
        if (awayRounds > 0 && this.awayTeam.getMemberCount() > 0) {
            int luckyIndex = rand.nextInt(this.awayTeam.getMemberCount());
            this.awayTeam.rewardPlayer(luckyIndex, awayRounds);
            logBuilder.append(String.format("[MVP Visitante] El jugador de %s destacó anotando %d puntos.%n", 
                this.awayTeam.getName(), awayRounds));
        }

        // Asignación de puntos en la tabla del torneo
        // Ganador: 3 puntos. Perdedor: 0 puntos.
        if (this.homeScore > this.awayScore) {
            this.homeTeam.recordResult(3, true);
            this.awayTeam.recordResult(0, false);
            logBuilder.append(String.format("RESULTADO FINAL: Ganador %s (%d - %d)%n", this.homeTeam.getName(), this.homeScore, this.awayScore));
        } else {
            this.homeTeam.recordResult(0, false);
            this.awayTeam.recordResult(3, true);
            logBuilder.append(String.format("RESULTADO FINAL: Ganador %s (%d - %d)%n", this.awayTeam.getName(), this.awayScore, this.homeScore));
        }

        this.matchLog = logBuilder.toString();
    }
}
