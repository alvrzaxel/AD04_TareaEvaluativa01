/**************************************************
 * Autor: Axel Álvarez Santos
 * Fecha: 21/12/2024
 * Tarea: AD04 Tarea Evaluativa 01
 **************************************************/

package com.ud04app.services;

import com.ud04app.entities.Passenger;
import com.ud04app.util.Console;
import com.ud04app.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Clase servicio para gestionar las operaciones relacionadas con los pasajeros en la base de datos
 * Creación y visualización de pasajeros
 */
public class PassengerService {
    
    /**
     * Crea un nuevo pasajero en la base de datos
     * Si ocurre algún error, realiza un rollback de la transacción
     */
    public void createPassenger() {
        // Abre una sesión de Hibernate
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = null;
            
            try {
                // Inicia la transacción
                transaction = session.beginTransaction();
                
                // Solicita los datos del nuevo pasajero
                Passenger passenger = requestPassengerData();
                
                // Guarda el pasajero y confirma los cambios en la base de datos
                session.persist(passenger);
                transaction.commit();
                
                // Mensaje informativo al usuario
                System.out.println("\nPasajero creado correctamente:");
                System.out.println(passenger);
                
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());
                // Si ocurre un error, realiza un rollback de la transacción
                if (transaction != null) {
                    System.out.println("Realizando rollback...");
                    transaction.rollback();
                }
                e.printStackTrace();
            }
            
        } catch (Exception e) {
            System.out.println("Error al abrir la sesión.");
            e.printStackTrace();
        }
    }
    
    /**
     * Solicita al usuario los datos necesarios para crear un pasajero
     * @return Pasajero: Objeto con los datos proporcionados por el usuario
     */
    private Passenger requestPassengerData() {
        // Solicita el email y el nombre del pasajero
        String name = Console.readString("Introduce el nombre del nuevo pasajero: ");
        String email = Console.readString("Introduce el email del nuevo pasajero: ");
        
        // Crea el objeto Pasajero con los datos proporcionados
        return new Passenger(email, name);
    }
    
    /**
     * Imprime y permite seleccionar un pasajero de la base de datos
     * @param session La sesión de Hibernate activa
     * @return El pasajero seleccionado por el usuario
     */
    public Passenger getsPassenger(Session session) {
        // Obtiene la lista de pasajeros
        List<Passenger> passengers = getListPassengers(session);
        if (passengers.isEmpty()) {
            System.out.println("No existen pasajeros en la base de datos.");
            return null;
        }
        
        // Muestra los pasajeros disponibles para seleccionar
        System.out.println("Pasajeros disponibles:");
        printPassengers(passengers);
        
        // Permite al usuario elegir un pasajero específico
        return selectPassenger(session);
    }
    
    /**
     * Solicita al usuario que elija un pasajero de la lista
     * @param session La sesión de Hibernate activa
     * @return El pasajero seleccionado por el usuario
     */
    private Passenger selectPassenger(Session session) {
        Passenger passenger;
        
        do {
            // Solicita al usuario que ingrese el ID del pasajero
            int idPassenger = Console.readInt("Elige un pasajero (ID): ");
            
            // Busca al pasajero con el ID proporcionado
            passenger = session.get(Passenger.class, idPassenger);
            
            if (passenger == null) {
                System.out.println("Elige entre los pasajeros disponibles.");
            }
            
        } while (passenger == null);
        
        return passenger;
    }
    
    /**
     * Obtiene la lista de pasajeros de la base de datos
     * @param session La sesión de Hibernate activa
     * @return Lista de pasajeros obtenida de la base de datos
     */
    public List<Passenger> getListPassengers(Session session) {
        Query<Passenger> query = session.createQuery("FROM Passenger", Passenger.class);
        return query.getResultList();
    }
    
    /**
     * Imprime la lista de pasajeros en la consola
     * @param passengers Lista de pasajeros a mostrar
     */
    public void printPassengers(List<Passenger> passengers) {
        for (Passenger aPassenger : passengers) {
            System.out.println(aPassenger);
        }
    }
}