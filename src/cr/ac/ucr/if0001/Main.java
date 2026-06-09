package cr.ac.ucr.if0001;

import cr.ac.ucr.if0001.model.TorneoEsports;
import cr.ac.ucr.if0001.view.VistaConsola;
import cr.ac.ucr.if0001.controller.TorneoController;

/**
 * <h1>Clase Principal: Main</h1>
 * <p><b>Resumen:</b> Punto de entrada oficial de la aplicación. Su única responsabilidad
 * es inicializar las tres capas del patrón de diseño arquitectónico <b>Modelo-Vista-Controlador (MVC)</b>
 * y arrancar el ciclo de ejecución del controlador.</p>
 * 
 * <h2>Concepto Principal (El 'Qué'):</h2>
 * <p>El método <code>public static void main(String[] args)</code> es el punto de inicio que la
 * **Máquina Virtual de Java (JVM)** busca para cargar la aplicación en memoria e iniciar el flujo de ejecución.
 * En una aplicación correctamente estructurada bajo el patrón MVC, la clase principal no realiza
 * cálculos lógicos complejos (modelo) ni despliega directamente menús (controlador). Su función es actuar
 * de pegamento o "instalador", creando las instancias necesarias de cada capa y estableciendo
 * las interconexiones iniciales.</p>
 * 
 * <h2>Analogía (La Intuición):</h2>
 * <p>La clase <code>Main</code> es el **Interruptor de Encendido** de una fábrica automatizada.
 * El interruptor no produce bienes (modelo) ni supervisa a los obreros (controlador), pero sin presionarlo,
 * las máquinas de la fábrica nunca recibirían electricidad para ponerse en marcha.</p>
 */
public class Main {

    /**
     * Método de inicio del programa. Instancia e interconecta las capas del MVC.
     *
     * @param args Argumentos de la línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        // 1. Instanciamos el Modelo (Contiene la lógica de datos y simulación en RAM)
        TorneoEsports modeloTorneo = new TorneoEsports("Copa Universitaria de Esports 2026");

        // 2. Instanciamos la Vista (Controla la renderización en consola)
        VistaConsola vistaConsola = new VistaConsola();

        // 3. Instanciamos el Controlador, pasando como parámetros las instancias del Modelo y la Vista
        // para establecer una comunicación fluida.
        TorneoController controlador = new TorneoController(modeloTorneo, vistaConsola);

        // 4. Iniciamos la ejecución del bucle interactivo de la aplicación
        controlador.run();
    }
}
