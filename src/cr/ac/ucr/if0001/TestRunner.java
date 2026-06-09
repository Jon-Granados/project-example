package cr.ac.ucr.if0001;

import cr.ac.ucr.if0001.model.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * <h1>Clase de Pruebas: TestRunner</h1>
 * <p><b>Resumen:</b> Programa de verificación automatizada que valida cada uno de los
 * requerimientos de lógica matemática del modelo (POO, Composición, Copias Defensivas, 
 * Bubble Sort manual, Matrices y Persistencia NIO). Sirve como ejemplo de pruebas unitarias
 * para los estudiantes.</p>
 */
public class TestRunner {

    public static void main(String[] args) {
        System.out.println("=== INICIANDO VALIDACIÓN AUTOMÁTICA DEL MODELO ===");
        
        try {
            testComposicionLimites();
            testCopiasDefensivas();
            testDiagonalMatriz();
            testBubbleSortStandings();
            testPersistenciaNIO();
            
            System.out.println("\n\u001B[32m[APROBADO] Todas las pruebas lógicas pasaron exitosamente.\u001B[0m");
        } catch (Exception e) {
            System.err.println("\n\u001B[31m[FALLÓ] Se detectó un error en las pruebas lógicas:\u001B[0m");
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void testComposicionLimites() {
        System.out.print("1. Probando Composición Fuerte y Límites del Equipo... ");
        EquipoEsports team = new EquipoEsports("Team Alpha");
        
        // Agregar 3 jugadores
        team.addPlayer("P1", 80, 1.2, true);
        team.addPlayer("P2", 85, 1.3, false);
        team.addPlayer("P3", 75, 1.1, true);
        
        if (team.getMemberCount() != 3) {
            throw new AssertionError("El conteo de miembros debería ser 3.");
        }

        // El 4to jugador debe lanzar IllegalStateException
        try {
            team.addPlayer("P4", 90, 1.4, true);
            throw new AssertionError("Se esperaba una IllegalStateException al exceder el límite de 3 jugadores.");
        } catch (IllegalStateException e) {
            // Correcto
        }
        System.out.println("OK");
    }

    private static void testCopiasDefensivas() {
        System.out.print("2. Probando Copias Defensivas en Getters... ");
        EquipoEsports team = new EquipoEsports("Team Beta");
        team.addPlayer("StarPlayer", 90, 1.5, true);

        // Obtener la colección expuesta por el getter
        Jugador[] membersExposed = team.getMembers();
        
        // Modificar el arreglo devuelto (no debería afectar al equipo interno)
        membersExposed[0] = null;
        
        Jugador[] membersInternalCheck = team.getMembers();
        if (membersInternalCheck[0] == null) {
            throw new AssertionError("La copia defensiva falló: el arreglo interno del equipo fue alterado externamente.");
        }
        
        // Modificar los valores internos de un objeto devuelto (tampoco debería afectar al equipo interno)
        membersExposed = team.getMembers();
        membersExposed[0].setName("HackName");
        
        membersInternalCheck = team.getMembers();
        if (membersInternalCheck[0].getName().equals("HackName")) {
            throw new AssertionError("La copia defensiva falló: el objeto Jugador interno fue modificado por referencia.");
        }
        System.out.println("OK");
    }

    private static void testDiagonalMatriz() {
        System.out.print("3. Probando Recorrido de Matriz y Suma de Diagonal... ");
        TorneoEsports torneo = new TorneoEsports("Copa Test");
        
        // Llenar matriz 2D
        torneo.recordMatchScoreMatrix(0, 1, 3, 2); // Fila 0, Col 1 = 3 | Fila 1, Col 0 = 2
        torneo.recordMatchScoreMatrix(2, 3, 4, 1); // Fila 2, Col 3 = 4 | Fila 3, Col 2 = 1

        // La diagonal principal [i][i] debe seguir valiendo 0 por lógica de diseño
        int diagonalSum = torneo.calculateDiagonalSum();
        if (diagonalSum != 0) {
            throw new AssertionError("La diagonal principal de enfrentamientos cruzados debería valer 0. Valor: " + diagonalSum);
        }
        System.out.println("OK");
    }

    private static void testBubbleSortStandings() {
        System.out.print("4. Probando Algoritmo Bubble Sort Multicriterio... ");
        TorneoEsports torneo = new TorneoEsports("Liga Test");
        
        // Crear 4 equipos con diferencias estadísticas
        EquipoEsports t1 = new EquipoEsports("Team 1"); // 3 pts, 1 victoria, rendimiento alto
        t1.addPlayer("P1", 90, 1.5, true);
        t1.recordResult(3, true);

        EquipoEsports t2 = new EquipoEsports("Team 2"); // 6 pts
        t2.addPlayer("P2", 50, 1.0, false);
        t2.recordResult(6, true);

        EquipoEsports t3 = new EquipoEsports("Team 3"); // 3 pts, 1 victoria, rendimiento bajo
        t3.addPlayer("P3", 50, 1.0, false);
        t3.recordResult(3, true);

        EquipoEsports t4 = new EquipoEsports("Team 4"); // 0 pts
        t4.addPlayer("P4", 80, 1.2, true);
        t4.recordResult(0, false);

        // Agregarlos
        torneo.addTeam(t1);
        torneo.addTeam(t2);
        torneo.addTeam(t3);
        torneo.addTeam(t4);

        // Ordenar standings
        torneo.sortStandings();
        EquipoEsports[] sorted = torneo.getTeams();

        // El orden esperado es:
        // 1. Team 2 (6 pts)
        // 2. Team 1 (3 pts, victoria, mayor rendimiento 90*1.5=135)
        // 3. Team 3 (3 pts, victoria, menor rendimiento 50*1.0=50)
        // 4. Team 4 (0 pts)
        if (!sorted[0].getName().equals("Team 2")) {
            throw new AssertionError("Fallo en Criterio 1 de Ordenamiento.");
        }
        if (!sorted[1].getName().equals("Team 1")) {
            throw new AssertionError("Fallo en Criterio 2 o 3 (Rendimiento) de Ordenamiento.");
        }
        if (!sorted[2].getName().equals("Team 3")) {
            throw new AssertionError("Fallo en Criterio 3 (Rendimiento) de Ordenamiento.");
        }
        if (!sorted[3].getName().equals("Team 4")) {
            throw new AssertionError("Fallo de ordenamiento del último lugar.");
        }
        System.out.println("OK");
    }

    private static void testPersistenciaNIO() throws IOException {
        System.out.print("5. Probando Persistencia Java NIO... ");
        
        // Limpiamos archivo si existía
        Path file = Path.of("campeones.txt");
        Files.deleteIfExists(file);

        TorneoEsports torneo = new TorneoEsports("Torneo Nacional");
        EquipoEsports champion = new EquipoEsports("Monarcas de Costa Rica");
        champion.addPlayer("C1", 90, 1.5, true);
        champion.recordResult(9, true);
        torneo.addTeam(champion);
        
        // Agregar otros 3 equipos vacíos para llenar el torneo
        torneo.addTeam(new EquipoEsports("T2"));
        torneo.addTeam(new EquipoEsports("T3"));
        torneo.addTeam(new EquipoEsports("T4"));

        // Guardar campeón en disco
        torneo.saveChampionToDisk();

        if (!Files.exists(file)) {
            throw new AssertionError("El archivo 'campeones.txt' debería haber sido creado.");
        }

        // Cargar historial
        List<String> history = torneo.loadChampionsHistory();
        if (history.isEmpty() || !history.get(0).contains("Monarcas de Costa Rica")) {
            throw new AssertionError("El historial cargado no coincide con el campeón guardado.");
        }
        
        // Limpiar archivo creado
        Files.deleteIfExists(file);
        System.out.println("OK");
    }
}
