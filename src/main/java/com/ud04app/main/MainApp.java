package com.ud04app.main;

import com.ud04app.services.ConductorService;
import com.ud04app.services.PasajeroService;
import com.ud04app.services.ReservaService;
import com.ud04app.services.ViajeService;
import com.ud04app.util.Console;

public class MainApp {
    
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
    
    // Instancia de los servicios
    static ConductorService conductorService = new ConductorService();
    static ViajeService viajeService = new ViajeService();
    static PasajeroService pasajeroService = new PasajeroService();
    static ReservaService reservaService = new ReservaService();
    
    public static void main(String[] args) {
        int selectedOption = 0;
        
        do {
            
            selectedOption = printMenu();
            
            executeOption(selectedOption);
            
        } while (selectedOption != 8);
        
        System.out.println("byeeeeee");
        
    }
    
    private static int printMenu() {
        
        String separator = "=".repeat(3);
        System.out.println(separator + " Menú de Gestión de Viajes Compartidos " + separator);
        
        for (int i = 0; i < MENU_OPTIONS.length; i++) {
            System.out.printf("%d. %s%n", i+1, MENU_OPTIONS[i]);
        }
        
        return selectOptions(MENU_OPTIONS.length);
    }
    
    private static int selectOptions(int maxOptions) {
        int selectOptions = -1;
        
        try {
            do {
                
                String input = Console.readString("\nSelecciona una opción: ");
                
                try {
                    selectOptions = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    selectOptions = -1;
                }
                
                if (selectOptions < 1 || selectOptions > maxOptions) {
                    System.out.println("Opción no válida, debe ser un número entre 1 y " + maxOptions + ".");
                }
                
            } while (selectOptions < 1 || selectOptions > maxOptions);
            
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error inesperado.");
        }
        
        return selectOptions;
    }
    
    private static void executeOption(int selectedOption) {
        
        System.out.println("Opción seleccionada: " + selectedOption + ". " + MENU_OPTIONS[selectedOption-1]);
        
        switch (selectedOption) {
            
            case 1:
                conductorService.crearConductor();
                break;
            case 2:
                viajeService.crearViaje();
                break;
            case 3:
                viajeService.printListViajes();
                break;
            case 4:
                System.out.println("4");
                break;
            case 5:
                System.out.println("5");
                break;
            case 6:
                System.out.println("6");
                break;
            case 7:
                System.out.println("7");
                break;
        }
    }
}
