package cr.ac.ucr.if0001.view;

import cr.ac.ucr.if0001.model.EquipoEsports;
import cr.ac.ucr.if0001.model.Jugador;
import cr.ac.ucr.if0001.model.JugadorOfensivo;
import cr.ac.ucr.if0001.model.JugadorDefensivo;
import java.util.List;

/**
 * <h1>Clase: VistaConsola</h1>
 * <p><b>Resumen:</b> Encapsula todas las impresiones en pantalla y el formateo estético del sistema.
 * Es la única clase autorizada para interactuar directamente con la salida estándar del sistema operativo.</p>
 * 
 * <h2>Concepto Principal (El 'Qué'):</h2>
 * <p>La <b>Vista</b> en el patrón MVC tiene una única responsabilidad: presentar datos al usuario.
 * Para lograr un acabado profesional y legible, se emplean dos técnicas del JDK:
 * <ul>
 *   <li><b>Formateo de Cadenas con printf():</b> Uso de especificadores de formato como <code>%-15s</code>
 *   (alineación a la izquierda con 15 caracteres de ancho) o <code>%4d</code> (número entero con ancho de 4 caracteres)
 *   para alinear las columnas de las tablas de datos, evitando desalineaciones causadas por tabuladores simples <code>\t</code>.</li>
 *   <li><b>Secuencias de Escape ANSI:</b> Códigos especiales de texto plano (ej. <code>\u001B[32m</code> para color verde)
 *   que son interpretados por la consola del sistema operativo para aplicar formato de estilo y color al texto impreso.</li>
 * </ul>
 * </p>
 * 
 * <h2>Analogía (La Intuición):</h2>
 * <p>La Vista es como la **Pantalla de un televisor**. El televisor no decide qué programas emitir ni calcula las señales
 * de satélite (eso es el controlador y el modelo); solo toma la señal de video cruda y la proyecta con luces de colores
 * y nitidez para que el espectador en la sala pueda disfrutarla.</p>
 * 
 * <h2>Trampas Comunes (Gotchas):</h2>
 * <ul>
 *   <li><b>Compatibilidad ANSI en Consolas Antiguas:</b> Las terminales antiguas de Windows (CMD antiguo) no soportan
 *   códigos ANSI por defecto y mostrarán caracteres extraños en pantalla como <code>[32mTexto</code>. Las terminales modernas
 *   como Windows Terminal o PowerShell en Windows 10/11 sí los interpretan de forma nativa e impecable.</li>
 *   <li><b>Olvidar el Código de Reseteo (Reset):</b> Si imprimes un texto en color rojo y no envías al final la secuencia
 *   de restauración <code>\u001B[0m</code>, toda la consola del sistema operativo se quedará teñida de rojo indefinidamente.</li>
 * </ul>
 */
public class VistaConsola {

    // Códigos ANSI para colores de consola
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BOLD = "\u001B[1m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    /**
     * Imprime un encabezado estilizado para los títulos de los menús.
     *
     * @param title Título del encabezado.
     */
    public void printHeader(String title) {
        System.out.println(ANSI_CYAN + "======================================================================" + ANSI_RESET);
        System.out.println(ANSI_BOLD + ANSI_WHITE + "   " + title.toUpperCase() + ANSI_RESET);
        System.out.println(ANSI_CYAN + "======================================================================" + ANSI_RESET);
    }

    /**
     * Despliega las opciones del menú principal.
     */
    public void printMainMenu() {
        printHeader("EsportsManager 2026 - Menú Principal");
        System.out.println(ANSI_YELLOW + "  1." + ANSI_RESET + " Simular Jornada Completa (Torneo)");
        System.out.println(ANSI_YELLOW + "  2." + ANSI_RESET + " Ver Tabla de Posiciones Oficial");
        System.out.println(ANSI_YELLOW + "  3." + ANSI_RESET + " Ver Detalle de Equipos e Integrantes");
        System.out.println(ANSI_YELLOW + "  4." + ANSI_RESET + " Sumar Diagonal de Matriz de Enfrentamientos");
        System.out.println(ANSI_YELLOW + "  5." + ANSI_RESET + " Ver Historial de Campeones (NIO)");
        System.out.println(ANSI_YELLOW + "  6." + ANSI_RESET + " Salir");
        System.out.print(ANSI_BOLD + "\nSeleccione una opción: " + ANSI_RESET);
    }

    /**
     * Imprime un mensaje general al usuario.
     *
     * @param msg Mensaje a imprimir.
     */
    public void printMessage(String msg) {
        System.out.println(msg);
    }

    /**
     * Imprime un mensaje de advertencia o error en color rojo.
     *
     * @param errorMsg Mensaje de error.
     */
    public void printError(String errorMsg) {
        System.out.println(ANSI_RED + ANSI_BOLD + "[ERROR] " + ANSI_RESET + ANSI_RED + errorMsg + ANSI_RESET);
    }

    /**
     * Imprime un mensaje de éxito en color verde.
     *
     * @param successMsg Mensaje de éxito.
     */
    public void printSuccess(String successMsg) {
        System.out.println(ANSI_GREEN + ANSI_BOLD + "[ÉXITO] " + ANSI_RESET + ANSI_GREEN + successMsg + ANSI_RESET);
    }

    /**
     * Despliega la tabla de posiciones con un formateo tabular perfecto con printf.
     *
     * @param sortedTeams Arreglo de equipos ya ordenados por el modelo.
     */
    public void printStandingsTable(EquipoEsports[] sortedTeams) {
        printHeader("TABLA OFICIAL DE POSICIONES - ESPORTS MANAGER");
        
        // Cabecera de la tabla formateada
        // %-3s: Posición, %-22s: Nombre equipo, %5s: Puntos, %5s: PJ, %5s: PG, %5s: PP, %15s: Rendimiento Promedio
        System.out.printf(ANSI_BOLD + "%-4s | %-20s | %5s | %4s | %4s | %4s | %15s%n" + ANSI_RESET, 
                "POS", "EQUIPO", "PTS", "PJ", "PG", "PP", "REND. GRUPAL");
        System.out.println("----------------------------------------------------------------------");

        for (int i = 0; i < sortedTeams.length; i++) {
            EquipoEsports team = sortedTeams[i];
            int pj = team.getMatchesWon() + team.getMatchesLost(); // Partidos Jugados
            
            // Pintamos de verde brillante al líder actual del torneo
            String color = (i == 0) ? ANSI_GREEN + ANSI_BOLD : "";
            
            System.out.printf(color + "%-4d | %-20s | %5d | %4d | %4d | %4d | %15.2f" + ANSI_RESET + "%n", 
                    (i + 1), 
                    team.getName(), 
                    team.getTournamentPoints(), 
                    pj, 
                    team.getMatchesWon(), 
                    team.getMatchesLost(),
                    team.calculateTeamPerformance());
        }
        System.out.println("----------------------------------------------------------------------");
    }

    /**
     * Despliega en pantalla el detalle de todos los equipos y sus respectivos jugadores,
     * ilustrando el polimorfismo y herencia en la salida.
     *
     * @param teams Colección de equipos.
     */
    public void printTeamsDetail(EquipoEsports[] teams) {
        printHeader("Reporte Detallado de Equipos y Nóminas");

        for (EquipoEsports team : teams) {
            System.out.println(ANSI_BLUE + ANSI_BOLD + "\nEQUIPO: " + team.getName().toUpperCase() + ANSI_RESET);
            System.out.println("----------------------------------------------------------------------");
            System.out.printf("%-18s | %-12s | %-12s | %-12s%n", 
                    "NOMBRE JUGADOR", "ROL TÁCTICO", "HABILIDAD", "PTS ANOTADOS");
            System.out.println("----------------------------------------------------------------------");

            // Copia defensiva expuesta
            Jugador[] players = team.getMembers();
            if (players.length == 0) {
                System.out.println(" (Sin jugadores registrados)");
            } else {
                for (Jugador p : players) {
                    String rol = "";
                    String extra = "";
                    
                    // Verificación de tipo en tiempo de ejecución (instanceof)
                    if (p instanceof JugadorOfensivo) {
                        rol = "Ofensivo";
                        extra = String.format("Bono Atq: x%.1f", ((JugadorOfensivo) p).getAttackBonus());
                    } else if (p instanceof JugadorDefensivo) {
                        rol = "Defensivo";
                        extra = String.format("Bono Def: x%.1f", ((JugadorDefensivo) p).getDefenseBonus());
                    }

                    System.out.printf("%-18s | %-12s | %-12d | %-12d (%s)%n", 
                            p.getName(), rol, p.getSkillLevel(), p.getPointsScored(), extra);
                }
            }
            System.out.println("----------------------------------------------------------------------");
        }
    }

    /**
     * Renderiza en la pantalla la matriz de puntuación cruzada entre equipos de forma bidimensional.
     *
     * @param matrix La matriz de puntuación de 4x4.
     * @param teams Arreglo de equipos para etiquetar filas y columnas.
     */
    public void printScoresMatrix(int[][] matrix, EquipoEsports[] teams) {
        printHeader("Matriz de Resultados Cruzados (Enfrentamientos)");
        
        System.out.print("           ");
        for (EquipoEsports team : teams) {
            // Recorta el nombre del equipo a 6 caracteres para que quepa en la columna
            String label = team.getName().substring(0, Math.min(team.getName().length(), 6));
            System.out.printf("%8s", label);
        }
        System.out.println("\n-------------------------------------------------------------");

        for (int i = 0; i < matrix.length; i++) {
            String rowLabel = teams[i].getName().substring(0, Math.min(teams[i].getName().length(), 6));
            System.out.printf("%-10s|", rowLabel);
            
            for (int j = 0; j < matrix[i].length; j++) {
                // Si estamos en la diagonal, la coloreamos de amarillo para llamar la atención del estudiante
                if (i == j) {
                    System.out.printf(ANSI_YELLOW + "%8d" + ANSI_RESET, matrix[i][j]);
                } else {
                    System.out.printf("%8d", matrix[i][j]);
                }
            }
            System.out.println();
        }
        System.out.println("-------------------------------------------------------------");
    }

    /**
     * Muestra la bitácora o historial de campeones del simulador cargada desde el archivo.
     *
     * @param history Lista con las líneas leídas en el disco.
     */
    public void printChampionsHistory(List<String> history) {
        printHeader("Historial de Honor - Campeones de Esports");
        
        if (history.isEmpty()) {
            System.out.println(ANSI_YELLOW + " No se registran torneos finalizados en el historial todavía." + ANSI_RESET);
            return;
        }

        for (int i = 0; i < history.size(); i++) {
            System.out.printf(ANSI_GREEN + "  [%02d] " + ANSI_RESET + "%s%n", (i + 1), history.get(i));
        }
    }
}
