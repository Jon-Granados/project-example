package cr.ac.ucr.if0001.controller;

import cr.ac.ucr.if0001.model.*;
import cr.ac.ucr.if0001.view.VistaConsola;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * <h1>Clase: TorneoController</h1>
 * <p><b>Resumen:</b> Actúa como el intermediario (Controlador) entre la capa de interfaz
 * de usuario (Vista) y la lógica matemática (Modelo). Maneja las entradas del teclado, orquesta
 * el avance de las jornadas y gestiona las fallas operativas mediante captura de excepciones.</p>
 * 
 * <h2>Concepto Principal (El 'Qué'):</h2>
 * <p>El <b>Controlador</b> en el patrón MVC responde a los eventos del usuario. Traduce las acciones
 * del teclado en llamadas a métodos del modelo y actualiza la vista correspondiente.
 * <br>
 * En esta clase se implementa:
 * <ul>
 *   <li><b>Captura de Excepciones (try-catch):</b> Evita que el programa aborte ante entradas incorrectas
 *   de texto cuando se esperan opciones numéricas (capturando <code>NumberFormatException</code>).</li>
 *   <li><b>Saneamiento del Buffer de Scanner:</b> Uso de <code>scanner.nextLine()</code> combinado con parsing manual,
 *   lo cual previene el famoso error (*gotcha*) de Java donde el carácter de salto de línea <code>\n</code> queda
 *   atrapado al usar <code>nextInt()</code>, arruinando lecturas futuras.</li>
 *   <li><b>Simulación en Vivo Dinámica:</b> Detención del hilo principal de ejecución usando <code>Thread.sleep()</code>
 *   para simular pausas dramáticas en los eventos.</li>
 * </ul>
 * </p>
 * 
 * <h2>Analogía (La Intuición):</h2>
 * <p>El Controlador es el **Director de Orquesta**. No toca ningún instrumento (los instrumentos son el Modelo)
 * y no genera las ondas de sonido que la audiencia escucha (eso es la Vista). Sin embargo, el director mueve sus manos
 * y le indica al violinista cuándo entrar y con qué volumen, coordinando todo el espectáculo.</p>
 * 
 * <h2>Trampas Comunes (Gotchas):</h2>
 * <ul>
 *   <li><b>Error de Hilo Interrumpido:</b> Al utilizar <code>Thread.sleep(...)</code>, Java exige atrapar obligatoriamente
 *   la excepción checked <code>InterruptedException</code>. Omitirla causará que el código no compile.</li>
 *   <li><b>Ignorar buffers sucios:</b> Usar <code>scanner.nextInt()</code> directamente es una de las causas principales
 *   de bugs en proyectos estudiantiles. La recomendación académica siempre es leer la línea completa como String
 *   y luego convertirla al tipo de dato deseado.</li>
 * </ul>
 */
public class TorneoController {

    private final TorneoEsports model;
    private final VistaConsola view;
    private final Scanner scanner;
    private final Random rand;

    // Control de Jornadas del Torneo (Todos contra todos - 4 equipos = 3 jornadas de juego)
    private int currentJornada;
    private boolean tournamentFinished;

    /**
     * Inicializa el controlador acoplando las capas correspondientes de MVC.
     *
     * @param model Instancia central del modelo del torneo.
     * @param view Instancia de la vista para salidas en pantalla.
     */
    public TorneoController(TorneoEsports model, VistaConsola view) {
        this.model = model;
        this.view = view;
        this.scanner = new Scanner(System.in);
        this.rand = new Random();
        this.currentJornada = 1;
        this.tournamentFinished = false;
        
        // Carga inicial del torneo con equipos y nóminas
        initializeTournamentData();
    }

    /**
     * Orquesta el bucle principal de ejecución y el despliegue del menú.
     */
    public void run() {
        boolean exit = false;
        
        while (!exit) {
            view.printMainMenu();
            String userInput = scanner.nextLine();
            
            // Bloque try-catch para capturar fallos de entrada (Programación Robusta)
            try {
                int option = Integer.parseInt(userInput.trim());
                
                switch (option) {
                    case 1:
                        simulateNextJornada();
                        break;
                    case 2:
                        showStandings();
                        break;
                    case 3:
                        showTeamsDetail();
                        break;
                    case 4:
                        showDiagonalSum();
                        break;
                    case 5:
                        showHistory();
                        break;
                    case 6:
                        exit = true;
                        view.printSuccess("Gracias por utilizar EsportsManager 2026. ¡Hasta pronto!");
                        break;
                    default:
                        view.printError("Opción inválida. Digite un número entre 1 y 6.");
                }
            } catch (NumberFormatException e) {
                // Captura del fallo si el usuario digita letras en lugar de números
                view.printError("Entrada inválida. Debe ingresar un número entero correspondiente a las opciones del menú.");
            }
            
            // Pausa decorativa antes de volver a pintar el menú principal
            if (!exit) {
                view.printMessage(VistaConsola.ANSI_CYAN + "\nPresione ENTER para continuar..." + VistaConsola.ANSI_RESET);
                scanner.nextLine();
            }
        }
    }

    /**
     * Configura los datos base de prueba para el torneo inicial.
     * Crea 4 equipos y les agrega 3 jugadores a cada uno (Composición Fuerte).
     */
    private void initializeTournamentData() {
        // Equipo 1
        EquipoEsports team1 = new EquipoEsports("Costa Rica Tekken");
        team1.addPlayer("Cherry", 85, 1.3, true); // Jugador Ofensivo
        team1.addPlayer("Panda", 78, 1.2, false); // Jugador Defensivo
        team1.addPlayer("Monza", 90, 1.5, true);
        model.addTeam(team1);

        // Equipo 2
        EquipoEsports team2 = new EquipoEsports("Panama Valorant");
        team2.addPlayer("Viper", 82, 1.1, true);
        team2.addPlayer("Sage", 75, 1.6, false);
        team2.addPlayer("Sova", 80, 1.2, false);
        model.addTeam(team2);

        // Equipo 3
        EquipoEsports team3 = new EquipoEsports("Colombia Smash");
        team3.addPlayer("Mario", 88, 1.4, true);
        team3.addPlayer("Link", 81, 1.3, false);
        team3.addPlayer("Fox", 84, 1.2, true);
        model.addTeam(team3);

        // Equipo 4
        EquipoEsports team4 = new EquipoEsports("Mexico StreetFighter");
        team4.addPlayer("Ryu", 92, 1.5, true);
        team4.addPlayer("ChunLi", 83, 1.4, false);
        team4.addPlayer("Ken", 87, 1.1, true);
        model.addTeam(team4);
    }

    /**
     * Simula la siguiente jornada de enfrentamientos de forma estructurada.
     * Implementa efectos visuales dramáticos utilizando pausas con Thread.sleep().
     */
    private void simulateNextJornada() {
        if (tournamentFinished) {
            view.printError("El torneo ya ha finalizado. Puede ver el campeón en la tabla de posiciones o en el archivo de historial.");
            return;
        }

        view.printHeader("Simulando Jornada " + currentJornada + " / 3");
        
        EquipoEsports[] teams = model.getTeams();
        
        // Emparejamientos basados en la jornada actual
        int homeIdxA, awayIdxA;
        int homeIdxB, awayIdxB;

        if (currentJornada == 1) {
            // Costa Rica (0) vs Panama (1) | Colombia (2) vs Mexico (3)
            homeIdxA = 0; awayIdxA = 1;
            homeIdxB = 2; awayIdxB = 3;
        } else if (currentJornada == 2) {
            // Costa Rica (0) vs Colombia (2) | Panama (1) vs Mexico (3)
            homeIdxA = 0; awayIdxA = 2;
            homeIdxB = 1; awayIdxB = 3;
        } else {
            // Costa Rica (0) vs Mexico (3) | Panama (1) vs Colombia (2)
            homeIdxA = 0; awayIdxA = 3;
            homeIdxB = 1; awayIdxB = 2;
        }

        // Simular primera partida de la jornada
        playMatch(teams[homeIdxA], teams[awayIdxA], homeIdxA, awayIdxA);
        System.out.println();
        
        // Simular segunda partida de la jornada
        playMatch(teams[homeIdxB], teams[awayIdxB], homeIdxB, awayIdxB);

        view.printSuccess("¡Jornada " + currentJornada + " finalizada con éxito!");
        
        // Avanzar la jornada
        currentJornada++;
        
        if (currentJornada > 3) {
            tournamentFinished = true;
            declareAndSaveChampion();
        }
    }

    /**
     * Ejecuta una partida individual imprimiendo animaciones temporales y registrando
     * el resultado tanto en las estadísticas de los equipos como en la matriz cruzada del modelo.
     */
    private void playMatch(EquipoEsports home, EquipoEsports away, int homeIndex, int awayIndex) {
        view.printMessage(VistaConsola.ANSI_BOLD + "Iniciando partida: " + home.getName() + " vs " + away.getName() + "..." + VistaConsola.ANSI_RESET);
        
        // Efecto dramático de carga en consola (Anclaje al Mundo Real)
        try {
            for (int i = 0; i < 3; i++) {
                Thread.sleep(500); // Pausa de 500ms
                System.out.print(VistaConsola.ANSI_YELLOW + ". " + VistaConsola.ANSI_RESET);
            }
            System.out.println();
        } catch (InterruptedException e) {
            view.printError("La simulación dramática fue interrumpida inesperadamente.");
            Thread.currentThread().interrupt(); // Restaura estado de interrupción
        }

        // Instanciación del objeto Partida (Asociación Débil)
        PartidaEsports match = new PartidaEsports(home, away);
        match.play(rand);

        // Desplegar la crónica de acontecimientos simulados
        view.printMessage(match.getMatchLog());

        // Registrar puntajes en la matriz cruzada bidimensional del torneo
        model.recordMatchScoreMatrix(homeIndex, awayIndex, match.getHomeScore(), match.getAwayScore());
    }

    /**
     * Declara al equipo campeón del torneo, lo despliega de forma destacada,
     * e invoca al modelo para guardarlo de manera permanente en el disco.
     */
    private void declareAndSaveChampion() {
        // Ordenar la tabla de posiciones con el algoritmo Bubble Sort manual
        model.sortStandings();
        EquipoEsports champion = model.getTeams()[0]; // El equipo del índice 0 es el campeón

        view.printHeader("🏆 ¡TENEMOS UN CAMPEÓN DE TORNEO! 🏆");
        view.printSuccess(String.format("El equipo %s se consagra como el monarca absoluto con un total de %d puntos de torneo.", 
                champion.getName().toUpperCase(), champion.getTournamentPoints()));
        
        // Persistencia NIO
        try {
            model.saveChampionToDisk();
            view.printSuccess("Los datos del campeón se han guardado permanentemente en el historial 'campeones.txt'.");
        } catch (RuntimeException e) {
            // Captura de la excepción de persistencia propagada desde la capa de modelo
            view.printError("No se pudo escribir el campeón en el disco: " + e.getMessage());
        }
    }

    /**
     * Solicita al modelo ordenar la tabla de posiciones y le ordena a la vista desplegarla.
     */
    private void showStandings() {
        model.sortStandings();
        view.printStandingsTable(model.getTeams());
    }

    /**
     * Ordena a la vista desplegar el reporte exhaustivo de equipos e integrantes.
     */
    private void showTeamsDetail() {
        view.printTeamsDetail(model.getTeams());
    }

    /**
     * Despliega la matriz bidimensional de resultados cruzados e ilustra la lógica
     * académica de la suma de la diagonal principal.
     */
    private void showDiagonalSum() {
        // Imprime la matriz de 4x4
        view.printScoresMatrix(model.getScoresMatrix(), model.getTeams());
        
        // Sumar y mostrar la diagonal principal
        int sum = model.calculateDiagonalSum();
        
        view.printMessage(VistaConsola.ANSI_BOLD + "\nEXPLICACIÓN DIDÁCTICA:" + VistaConsola.ANSI_RESET);
        view.printMessage("La diagonal principal de la matriz cruzada comprende los valores de la posición [i][i]");
        view.printMessage("(por ejemplo: [0][0], [1][1], [2][2] y [3][3]). Estos corresponden a los encuentros");
        view.printMessage("de un equipo jugando contra sí mismo. En este simulador de torneo, dicho valor es lógicamente 0.");
        
        view.printSuccess("Suma acumulada de la diagonal principal: " + sum);
    }

    /**
     * Carga el archivo histórico usando NIO en el modelo y delega su renderizado en la vista.
     */
    private void showHistory() {
        try {
            List<String> history = model.loadChampionsHistory();
            view.printChampionsHistory(history);
        } catch (RuntimeException e) {
            view.printError("No se pudo leer el archivo de historial en disco: " + e.getMessage());
        }
    }
}
