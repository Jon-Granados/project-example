package cr.ac.ucr.if0001.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Clase: TorneoEsports</h1>
 * <p><b>Resumen:</b> Clase central del modelo que coordina el campeonato de Esports. Almacena
 * el historial de equipos agregados, una matriz 2D de resultados de enfrentamientos, y gestiona
 * la clasificación oficial mediante algoritmos manuales de ordenamiento y persistencia en disco.</p>
 * 
 * <h2>Concepto Principal (El 'Qué'):</h2>
 * <p>Esta clase reúne varios conceptos de alto rigor técnico:
 * <ul>
 *   <li><b>Agregación:</b> Contiene un arreglo de objetos <code>EquipoEsports[]</code>. Los equipos se instancian
 *   externamente en el controlador y son agregados al torneo. Su existencia no depende de forma exclusiva de la vida
 *   del torneo.</li>
 *   <li><b>Matrices Bidimensionales (Matrices 2D):</b> Se modela una matriz cuadrada <code>int[][] scoresMatrix</code>
 *   de 4x4. Cada fila y columna representa un equipo. El valor en la intersección <code>[i][j]</code> indica los puntos
 *   obtenidos por el equipo <code>i</code> en su encuentro contra el equipo <code>j</code>. Se implementa un recorrido
 *   para <b>sumar la diagonal principal</b> (los elementos con coordenadas <code>[i][i]</code>). En el mundo real de
 *   deportes, la diagonal representa a un equipo jugando contra sí mismo, lo cual no es posible (se inicializa en 0).</li>
 *   <li><b>Ordenamiento Burbuja Multicriterio (Cascading Bubble Sort):</b> Algoritmo de ordenación directa
 *   que resuelve empates aplicando sucesivamente reglas jerárquicas sin delegar en bibliotecas automáticas.</li>
 *   <li><b>Persistencia con Java NIO:</b> Utiliza <code>java.nio.file.Path</code> y <code>java.nio.file.Files</code>
 *   para guardar de manera acumulativa (modo <code>APPEND</code>) los nombres de los campeones históricos.</li>
 * </ul>
 * </p>
 * 
 * <h2>Analogía (La Intuición):</h2>
 * <p>La <code>Matriz 2D</code> es como una **Tabla de Doble Entrada** en un diario deportivo. Las filas muestran
 * los nombres de los equipos en el eje vertical y las columnas muestran los mismos nombres en el eje horizontal.
 * Si buscamos en la fila de 'Costa Rica' y la columna de 'Canadá', veremos los goles de ese enfrentamiento.
 * La diagonal de la tabla está sombreada o bloqueada (su suma es cero) porque Costa Rica no puede enfrentarse a sí misma.</p>
 * 
 * <h2>Trampas Comunes (Gotchas):</h2>
 * <ul>
 *   <li><b>Error de Excepción Checked de NIO:</b> Los métodos de la clase <code>Files</code> exigen obligatoriamente
 *   controlar excepciones de tipo <code>IOException</code>. En lugar de imprimir el error a la pantalla (lo cual violaría
 *   el patrón MVC desde el Modelo), encapsulamos la excepción dentro de una excepción no comprobada <code>RuntimeException</code>
 *   para que se propague hacia la capa del Controlador, donde será manejada e informada al usuario de forma segura.</li>
 * </ul>
 */
public class TorneoEsports {

    private String name;
    private EquipoEsports[] teams; // Agregación de equipos
    private int teamCount;
    
    // Matriz bidimensional de resultados cruzados
    // scoresMatrix[i][j] representa los puntos obtenidos por el equipo i jugando contra el equipo j
    private int[][] scoresMatrix;

    // Nombre del archivo para persistencia histórica
    private static final String HISTORY_FILE = "campeones.txt";

    /**
     * Inicializa el torneo de Esports configurando la capacidad física para 4 equipos.
     *
     * @param name Nombre del torneo.
     */
    public TorneoEsports(String name) {
        setName(name);
        this.teams = new EquipoEsports[4]; // Torneo cerrado de 4 equipos
        this.teamCount = 0;
        this.scoresMatrix = new int[4][4]; // Inicializada en 0 por defecto
    }

    // =========================================================================
    // Getters y Setters
    // =========================================================================

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        if (name == null || name.strip().isEmpty()) {
            throw new IllegalArgumentException("El nombre del torneo no puede estar vacío.");
        }
        this.name = name;
    }

    public EquipoEsports[] getTeams() {
        // Copia defensiva de la colección de equipos agregados
        EquipoEsports[] copies = new EquipoEsports[this.teamCount];
        System.arraycopy(this.teams, 0, copies, 0, this.teamCount);
        return copies;
    }

    public int getTeamCount() {
        return this.teamCount;
    }

    // =========================================================================
    // Lógica de Agregación
    // =========================================================================

    /**
     * Agrega un equipo preexistente al torneo.
     *
     * @param team Instancia de EquipoEsports.
     * @throws IllegalStateException si el torneo ya está completo con 4 equipos.
     * @throws IllegalArgumentException si el equipo es nulo.
     */
    public void addTeam(EquipoEsports team) {
        if (team == null) {
            throw new IllegalArgumentException("No se puede agregar un equipo nulo al torneo.");
        }
        if (this.teamCount >= this.teams.length) {
            throw new IllegalStateException("El torneo de Esports ya tiene cupos completos (máximo 4 equipos).");
        }
        this.teams[this.teamCount] = team;
        this.teamCount++;
    }

    // =========================================================================
    // Lógica de Matrices 2D y Diagonal Principal
    // =========================================================================

    /**
     * Registra en la matriz 2D los puntos acumulados de un partido.
     *
     * @param teamIndexHome Índice del equipo local en el arreglo [0..3].
     * @param teamIndexAway Índice del equipo visitante en el arreglo [0..3].
     * @param scoreHome Puntos obtenidos por el equipo local.
     * @param scoreAway Puntos obtenidos por el equipo visitante.
     */
    public void recordMatchScoreMatrix(int teamIndexHome, int teamIndexAway, int scoreHome, int scoreAway) {
        if (teamIndexHome < 0 || teamIndexHome >= 4 || teamIndexAway < 0 || teamIndexAway >= 4) {
            throw new IllegalArgumentException("Índice de matriz fuera de los límites.");
        }
        this.scoresMatrix[teamIndexHome][teamIndexAway] = scoreHome;
        this.scoresMatrix[teamIndexAway][teamIndexHome] = scoreAway;
    }

    public int[][] getScoresMatrix() {
        // Retorna una copia defensiva de la matriz 2D para proteger los datos internos
        int[][] copy = new int[4][4];
        for (int i = 0; i < 4; i++) {
            System.arraycopy(this.scoresMatrix[i], 0, copy[i], 0, 4);
        }
        return copy;
    }

    /**
     * Recorre la diagonal principal de la matriz bidimensional de puntajes y suma sus valores.
     * Ilustra la lógica de itineración matricial donde la fila es igual a la columna [i][i].
     *
     * @return Suma acumulativa de la diagonal principal.
     */
    public int calculateDiagonalSum() {
        int sum = 0;
        // La diagonal ocurre cuando el índice de fila coincide con el de columna
        for (int i = 0; i < this.scoresMatrix.length; i++) {
            // Validación defensiva para evitar IndexOutOfBounds en matrices irregulares
            if (i < this.scoresMatrix[i].length) {
                sum += this.scoresMatrix[i][i];
            }
        }
        return sum;
    }

    // =========================================================================
    // Algoritmo de Ordenamiento Manual: Bubble Sort FIFA
    // =========================================================================

    /**
     * Ordena de forma manual los equipos del torneo en base a criterios en cascada:
     * 1. Mayor cantidad de puntos de torneo acumulados.
     * 2. En caso de empate, mayor cantidad de partidas ganadas.
     * 3. En caso de empate, mayor promedio de habilidad técnica de sus jugadores.
     */
    public void sortStandings() {
        int n = this.teamCount;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                boolean swapNeeded = false;
                EquipoEsports teamA = this.teams[j];
                EquipoEsports teamB = this.teams[j + 1];

                // Criterio 1: Mayor cantidad de puntos acumulados
                if (teamA.getTournamentPoints() < teamB.getTournamentPoints()) {
                    swapNeeded = true;
                } else if (teamA.getTournamentPoints() == teamB.getTournamentPoints()) {
                    // Criterio 2 (Desempate): Mayor número de partidas ganadas
                    if (teamA.getMatchesWon() < teamB.getMatchesWon()) {
                        swapNeeded = true;
                    } else if (teamA.getMatchesWon() == teamB.getMatchesWon()) {
                        // Criterio 3 (Desempate): Mayor rendimiento promedio de sus jugadores en RAM
                        if (teamA.calculateTeamPerformance() < teamB.calculateTeamPerformance()) {
                            swapNeeded = true;
                        }
                    }
                }

                if (swapNeeded) {
                    // Intercambio de posiciones (Algoritmo de Burbuja)
                    EquipoEsports temp = this.teams[j];
                    this.teams[j] = this.teams[j + 1];
                    this.teams[j + 1] = temp;
                }
            }
        }
    }

    // =========================================================================
    // Persistencia de Archivos con Java NIO (Entrada/Salida)
    // =========================================================================

    /**
     * Guarda el nombre del equipo campeón en el archivo plano de registro.
     * Si el archivo no existe, lo crea automáticamente; si existe, añade una nueva línea
     * al final (modo APPEND) usando la API Java NIO.
     *
     * @throws RuntimeException si se produce un fallo crítico de I/O en disco (Checked Exception encapsulada).
     */
    public void saveChampionToDisk() {
        if (this.teamCount == 0) {
            return;
        }
        
        // Primero nos aseguramos de que los standings estén actualizados y ordenados
        sortStandings();
        String championName = this.teams[0].getName(); // El índice 0 es el campeón actual

        Path path = Path.of(HISTORY_FILE);
        // Concatenamos el campeón con el salto de línea nativo del SO para evitar problemas de visualización
        String entry = championName + " | Campeón del torneo: " + this.name + System.lineSeparator();

        try {
            // Escribe la línea en el archivo de texto
            // StandardOpenOption.CREATE: Crea el archivo si no existe en disco.
            // StandardOpenOption.APPEND: Escribe los bytes nuevos al final sin sobreescribir lo anterior.
            Files.writeString(path, entry, 
                StandardOpenOption.CREATE, 
                StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            // Encapsulamiento de Checked Exception a Unchecked Exception para adherir al patrón MVC
            throw new RuntimeException("Error crítico de escritura al persistir el campeón en disco: " + e.getMessage(), e);
        }
    }

    /**
     * Carga el historial de campeones del simulador leyendo todas las líneas de texto del disco.
     *
     * @return Una lista de cadenas con todos los campeones históricos.
     * @throws RuntimeException si ocurre un fallo al leer el archivo.
     */
    public List<String> loadChampionsHistory() {
        Path path = Path.of(HISTORY_FILE);
        
        // Validación defensiva para evitar la NoSuchFileException antes de abrir el archivo
        if (!Files.exists(path)) {
            return new ArrayList<>(); // Retorna lista vacía si no existe historial previo
        }

        try {
            // Lee todas las líneas y las vuelca automáticamente en una estructura List
            return Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException("Error crítico de lectura al obtener el historial de campeones: " + e.getMessage(), e);
        }
    }
}
