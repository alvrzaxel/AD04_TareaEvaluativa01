/**************************************************
 * Autor: Axel Álvarez Santos
 * Fecha: 21/12/2024
 * Tarea: AD04 Tarea Evaluativa 01
 **************************************************/

package com.ud04app.main;

import com.ud04app.services.*;
import com.ud04app.util.Console;
import com.ud04app.util.HibernateUtil;

/**
 * Clase principal de la aplicación que gestiona el menú y la ejecución de
 * las opciones seleccionadas.
 */
public class MainApp {
    
    // Opciones del menú
    static final String[] MENU_OPTIONS = {
            "Crear conductor",
            "Crear viaje",
            "Buscar viajes disponibles",
            "Crear pasajero",
            "Crear reserva",
            "Cancelar reserva",
            "Listar viajes",
            "Salir"
    };
    
    // Instancia de los servicios que gestionan las operaciones
    static DriverService driverService = new DriverService();
    static TripService tripService = new TripService();
    static PassengerService passengerService = new PassengerService();
    static ReservationService reservationService = new ReservationService();
    
    /**
     * Entrada y gestión del ciclo de vida de la aplicación
     * @param args Parámetros de la línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        int selectedOption;
        
        try {
            do {
                // Muestra el menú y lee la opción seleccionada por el usuario
                selectedOption = printMenu();
                
                // Ejecuta la opción seleccionada
                executeOption(selectedOption);
                
            } while (selectedOption != 8); // Se ejecuta hasta que se selecciona la opción de salida
            
            // Mensaje de despedida
            System.out.println("Fin del programa.");
            
        } finally {
            // Cierra la fábrica de sesiones al terminar la aplicación
            HibernateUtil.shutdown();
        }
        
    }
    
    /**
     * Muestra el menú de opciones y devuelve la opción seleccionada por el usuario
     * @return La opción seleccionada por el usuario
     */
    private static int printMenu() {
        // Formato para el encabezado del menú
        String separator = "=".repeat(3);
        System.out.println(separator + " Menú de Gestión de Viajes Compartidos " + separator);
        
        // Muestra las opciones del menú
        for (int i = 0; i < MENU_OPTIONS.length; i++) {
            System.out.printf("%d. %s%n", i+1, MENU_OPTIONS[i]);
        }
        
        // Le la opción seleccionada por el usuario
        return selectOption();
    }
    
    /**
     * Lee una opción válida seleccionada por el usuario
     * @return La opción seleccionada
     */
    private static int selectOption() {
        int maxOptions = MENU_OPTIONS.length;
        int selectedOption;
        
        do {
            // Repite hasta que el usuario seleccione una opción válida
            selectedOption = Console.readInt("Selecciona una opción: ");
            
            // Comprueba que la opción está en el rango válido
            if (selectedOption < 1 || selectedOption > maxOptions) {
                System.out.println("Opción no válida, debe ser un número entre 1 y " + maxOptions + ".\n");
            }
            
        } while (selectedOption < 1 || selectedOption > maxOptions);
        
        return selectedOption;
    }
    
    /**
     * Ejecuta la opción seleccionada por el usuario
     * @param selectedOption La opción seleccionada por el usuario
     */
    private static void executeOption(int selectedOption) {
        // Nombre de la opción elegida
        String optionName = MENU_OPTIONS[selectedOption-1];
        
        // Muestra la opción seleccionada por el usuario
        System.out.println("\nOpción seleccionada: " + selectedOption + ". " + optionName);
        
        // Ejecutar la acción correspondiente según la opción seleccionada
        switch (selectedOption) {
            case 1:
                driverService.createDriver();
                break;
            case 2:
                tripService.createTrip();
                break;
            case 3:
                tripService.listTrips(true);
                break;
            case 4:
                passengerService.createPassenger();
                break;
            case 5:
                reservationService.createReservation();
                break;
            case 6:
                reservationService.cancelReservation();
                break;
            case 7:
                tripService.listTrips(false);
                break;
            case 8:
                System.out.println("Saliendo del programa...");
                break;
        }
    }
}