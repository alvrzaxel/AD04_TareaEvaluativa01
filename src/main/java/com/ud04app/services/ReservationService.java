/**************************************************
 * Autor: Axel Álvarez Santos
 * Fecha: 21/12/2024
 * Tarea: AD04 Tarea Evaluativa 01
 **************************************************/

package com.ud04app.services;

import com.ud04app.entities.Passenger;
import com.ud04app.entities.Reservation;
import com.ud04app.entities.Trip;
import com.ud04app.util.Console;
import com.ud04app.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Clase servicio para gestionar las operaciones relacionadas con las reservas
 * Incluye la creación, visualización y cancelación
 */
public class ReservationService {
    
    // Instancias de los servicios PassengerService y TripService
    static PassengerService passengerService = new PassengerService();
    static TripService tripService = new TripService();
    
    /**
     * Crea una nueva reserva para un pasajero en un viaje
     * actualizando las plazas disponibles en el viaje
     */
    public void createReservation() {
        // Abre una sesión de Hibernate
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = null;
            
            try {
                // Inicia la transacción
                transaction = session.beginTransaction();
                
                // Obtiene el pasajero que realizará la reserva
                Passenger passenger = passengerService.getsPassenger(session);
                // Si no obtiene un pasajero, informa y retorna al menú principal
                if (passenger == null) {
                    System.out.println("Para crear una reserva, por favor, crea primero un pasajero nuevo.");
                    return;
                }
                
                // Solicita los datos de la reserva al usuario
                Reservation reservation = requestReservationData();
                
                // Lista los viajes disponibles para la reserva
                // Si no hay viajes, retorna al menú principal
                if(!tripService.listTrips(true)) return;
                
                // Selecciona el viaje para asociar a la reserva
                // Si no se elige un viaje, retorna al menú principal
                Trip trip = tripService.selectTrip(session, reservation.getNumberOfReservedSeats());
                if (trip == null) return;
                
                // Asocia la reserva al pasajero y al viaje
                reservation.setPassenger(passenger);
                reservation.setTravel(trip);
                session.persist(reservation);
                
                // Actualiza las plazas disponibles del viaje en la base de datos
                tripService.updateAvailableSeats(session, trip.getId(), reservation.getNumberOfReservedSeats());
                transaction.commit();
                
                // Mensaje informativo al usuario
                System.out.println("\nReserva creada correctamente:");
                System.out.println(reservation);
                
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
     * Solicita al usuario los datos necesarios para crear una reserva
     * @return Objeto Reserva con el número de plazas a reservar
     */
    private Reservation requestReservationData() {
        // Solicita las plazas a reservar
        int numberOfReservedSeats = Console.readInt("¿Cuántas plazas quieres reservar?: ");
        
        // Crea y devuelve una reserva
        return new Reservation(numberOfReservedSeats);
    }
    
    /**
     * Cancela una reserva existente seleccionándola por el ID
     */
    public void cancelReservation() {
        // Abre una sesión de Hibernate
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = null;
            
            try {
                // Inicia la transacción
                transaction = session.beginTransaction();
                
                // Selecciona la reserva a cancelar
                // Si no se selecciona ninguna, retorna al menú principal
                Reservation reservation = selectReservation(session);
                if (reservation == null) return;
                
                // Elimina la reserva de la base de datos
                session.remove(reservation);
                
                // Actualiza las plazas disponibles del viaje en la base de datos
                tripService.updateAvailableSeatsForReservation(session, reservation);
                transaction.commit();
                
                // Mensaje informativo al usuario
                System.out.print("Reserva cancelada correctamente.\n");
                
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
     * Selecciona una reserva existente por su ID
     * @param session La sesión de Hibernate
     * @return Reserva seleccionada o null si no existe
     */
    private Reservation selectReservation(Session session) {
        // Obtiene la lista de reservas disponibles
        List<Reservation> reservations = getsListReservations(session);
        if (reservations.isEmpty()) {
            System.out.println("No existen reservas en la base de datos.\n");
            return null;
        }
        
        // Muestra la lista de reservas
        System.out.println("Reservas:");
        printReservations(reservations);
        
        // Permite al usuario seleccionar una reserva válida
        Reservation reservation;
        do {
            // Solicita al usuario el ID de la reserva a cancelar
            int idReservation = Console.readInt("Elige una reserva (ID): ");
            reservation = session.get(Reservation.class, idReservation);
            
            if (reservation == null) {
                System.out.println("Reserva no encontrada.");
                
                // Permite salir al menú principal si no se quiere cancelar la reserva
                String answer = Console.readString("¿Deseas cancelar otra reserva? (S/N): ");
                if (!answer.startsWith("s") && !answer.startsWith("S")) {
                    System.out.println("Cargando menú principal...\n");
                    break;
                }
            }
        } while (reservation == null);
        
        return reservation;
    }
    
    /**
     * Obtiene la lista de todas las reservas almacenadas en la base de datos
     * @param session La sesión de Hibernate
     * @return Lista de reservas
     */
    private List<Reservation> getsListReservations(Session session) {
        // Crea una consulta HQL y la ejecuta para obtener todas las reservas almacenadas
        Query<Reservation> query = session.createQuery("FROM Reservation", Reservation.class);
        return query.getResultList();
    }
    
    /**
     * Imprime en consola la lista de reservas proporcionada
     * @param reservations Lista de reservas a imprimir
     */
    private void printReservations(List<Reservation> reservations) {
        for (Reservation aReservation : reservations) {
            System.out.println(aReservation);
        }
    }
}