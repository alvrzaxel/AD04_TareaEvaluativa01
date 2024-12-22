/**************************************************
 * Autor: Axel Álvarez Santos
 * Fecha: 21/12/2024
 * Tarea: AD04 Tarea Evaluativa 01
 **************************************************/

package com.ud04app.services;

import com.ud04app.entities.Driver;
import com.ud04app.entities.Reservation;
import com.ud04app.entities.Trip;
import com.ud04app.util.Console;
import com.ud04app.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Clase servicio para gestionar las operaciones relacionadas con los viajes en la base de datos
 * Creación y visualización de viajes
 */
public class TripService {
    
    // Instancia del servicio de Conductor
    static DriverService driverService = new DriverService();
    
    /**
     * Crea un nuevo viaje solicitando los datos al usuario y lo guarda en la base de datos
     * Si ocurre algún error, realiza un rollback de la transacción
     */
    public void createTrip() {
        // Abre una sesión de Hibernate
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = null;
            
            try {
                // Inicia la transacción
                transaction = session.beginTransaction();
                
                // Obtiene un conductor
                // Si no se obtiene un conductor, retorna al menú principal
                Driver driver = driverService.getsDriver(session);
                if (driver == null) {
                    return;
                }
                
                // Solicita los datos del viaje y asocia al conductor
                Trip trip = requestTripData();
                trip.setDriver(driver);
                
                // Guarda el viaje en la base de datos y confirma los cambios
                session.persist(trip);
                transaction.commit();
                
                // Mensaje informativo al usuario
                System.out.println("\nViaje creado correctamente:\n" + trip);
                
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
     * Lista de los viajes disponibles en la base de datos
     * @param filtrarDisponibles Indica si se debe filtrar los viajes (futuros con plazas disponibles)
     */
    public boolean listTrips(boolean filtrarDisponibles) {
        // Abre una sesión de Hibernate para realizar la consulta
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Obtiene la lista de viajes según el filtro aplicado
            List<Trip> trips = getsListTrips(session, filtrarDisponibles);
            
            // Verifica si existen viajes para mostrar
            if (trips.isEmpty()) {
                if (filtrarDisponibles) {
                    System.out.println("No hay viajes disponibles.\n");
                } else {
                    System.out.println("No existen viajes en la base de datos.\n");
                }
                return false;
            }
            
            // Muestra la lista de viajes
            System.out.println("Viajes disponibles:");
            printListTrips(trips);
            return true;
        }
    }
    
    /**
     * Recupera una lista de viajes de la base de datos según el filtro indicado
     * @param session La sesión de Hibernate utilizada para la consulta
     * @param filterAvailable Si es true, filtra los viajes con fecha futura y plazas disponibles
     * @return Lista de viajes que cumple con los criterios
     */
    private List<Trip> getsListTrips(Session session, boolean filterAvailable) {
        // // Crea la consulta base para recuperar los viajes
        String queryString = "FROM Trip s ";
        if (filterAvailable) queryString = queryString + "WHERE s.fechaHora > CURRENT_DATE AND s.plazasDisponibles > 0 ";
        queryString += "ORDER BY s.fechaHora ASC";
        
        // Ejecuta la consulta con el filtro indicado
        Query<Trip> query = session.createQuery(queryString, Trip.class);
        return query.getResultList();
    }
    
    /**
     * Solicita los datos necesarios para crear un nuevo viaje
     * @return viaje Un objeto con los datos proporcionados por el usuario
     */
    private Trip requestTripData() {
        // Solicita las ciudades de destino y origen
        String ciudadDestino = Console.readString("Introduce la ciudad de destino: ");
        String ciudadOrigen = Console.readString("Introduce la ciudad de origen: ");
        
        // Solicita la fecha y hora del viaje
        LocalDateTime fechaHora = requestDateAndTime();
        
        // Solicita el número de plazas disponibles
        int plazasDisponibles = requestAvailableSeats();
        
        // Crea el objeto viaje con los datos proporcionados
        return new Trip(ciudadDestino, ciudadOrigen, fechaHora, plazasDisponibles);
    }
    
    /**
     * Solicita al usuario las plazas disponibles para el viaje
     * Comprueba que el número de plazas sea mayor o igual a 0
     * @return Número de plazas disponibles
     */
    private int requestAvailableSeats() {
        int seats;
        
        do {
            // Solicita y valida las plazas disponibles
            seats = Console.readInt("Introduce las plazas disponibles: ");
            if (seats <= 0) System.out.println("No puede tener menos de 1 plaza disponible.");
            
        } while (seats <= 0);
        
        return seats;
    }
    
    /**
     * Solicita al usuario la fecha y hora del viaje
     * El formato de la fecha debe ser "dd/MM/yyyy" y de la hora "HH:mm"
     * @return Objeto LocalDateTime que representa la fecha y hora proporcionada
     */
    private LocalDateTime requestDateAndTime() {
        LocalDateTime dateTime = null;
        do {
            // Solicita y valida la fecha y la hora
            String fechaStr = Console.readString("Introduce la fecha (dd/MM/yyyy): ");
            String horaStr = Console.readString("Introduce la hora (HH:mm): ");
            dateTime = parseDateTime(fechaStr, horaStr);
            
        } while (dateTime == null); // Reintenta si la fecha y hora no son válidas
        
        return dateTime;
    }
    
    /**
     * Parsea las cadenas de fecha y hora proporcionadas por el usuario a un objeto LocalDateTime
     * @param fecha Cadena con la fecha en formato "dd/MM/yyyy"
     * @param hora Cadena con la hora en formato "HH:mm"
     * @return LocalDatetIME que representa la fecha y hora proporcionada
     */
    private LocalDateTime parseDateTime(String fecha, String hora) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String dateTime = fecha.trim() + " " + hora.trim();
        
        try {
            // Intenta parsear la fechaHora a un objeto LocalDateTime
            return LocalDateTime.parse(dateTime, format);
        } catch (DateTimeParseException e) {
            System.out.println("Error al obtener la fecha y hora. Asegúrate de usar el formato correcto.");
            return null;
        }
    }
    
    /**
     * Actualiza las plazas disponibles de un viaje en la base de datos
     * @param session La sesión de Hibernate
     * @param idTrip ID del viaje a actualizar
     * @param reservedSeats Número de plazas a restar a las disponibles
     */
    public void updateAvailableSeats(Session session, int idTrip, int reservedSeats) {
        // Obtiene el viaje desde la base de datos usando su ID
        Trip trip = session.get(Trip.class, idTrip);
        
        // Actualiza el número de plazas disponibles restando las plazas reservadas
        trip.setSeatsAvailable(trip.getSeatsAvailable() - reservedSeats);
        
        // Sincroniza el objeto trip con la base de datos
        session.merge(trip);
    }
    
    /**
     * Actualiza las plazas disponibles cuando se anula una reserva
     * Llama a `updateAvailableSeats` con un valor negativo para agregar las plazas canceladas.
     * @param session La sesión de Hibernate
     * @param reservation La reserva que se ha sido cancelada
     */
    public void updateAvailableSeatsForReservation(Session session, Reservation reservation) {
        // Crea una consulta para obtener el viaje asociado a la reserva y obtiene su ID
        Query<Trip> query = session.createQuery("FROM Trip s WHERE s.id = :idTrip", Trip.class);
        
        // Obtiene el ID del viaje y lo asociado al parámetro id
        int idTrip = reservation.getTravel().getId();
        query.setParameter("idTrip", idTrip);
        
        // Ejecuta la consulta y obtiene el viaje correspondiente
        Trip trip = query.getSingleResult();
        
        // Llamada para actualizar las plazas, pasando un valor negativo para incrementar las plazas disponibles
        updateAvailableSeats(session, trip.getId(), -reservation.getNumberOfReservedSeats());
    }
    
    /**
     * Permite seleccionar un viaje de la lista disponible por ID
     * Comprueba que cumple con los requisitos
     * @param session La sesión de Hibernate para consultar los datos
     * @param minSeats Número mínimo de plazas requeridas para el viaje
     * @return El viaje seleccionado
     */
    public Trip selectTrip(Session session, int minSeats) {
        Trip trip;
        
        do {
            // Bucle hasta que se seleccione un conductor válido
            int idTrip = Console.readInt("Elige el viaje (ID): ");
            trip = session.get(Trip.class, idTrip);
            
            // Comprueba que el ID del viaje existe
            if (trip == null) {
                System.out.println("Elige entre los viajes disponibles.");
                continue;
            }
            
            // Comprueba si no hay suficientes plazas disponibles
            if (trip.getSeatsAvailable() < minSeats) {
                System.out.println("No hay suficientes plazas disponibles para el viaje.");
                
                // Permite salir al menú principal si no se puede hacer la reserva
                String respuesta = Console.readString("¿Deseas reservar otro viaje? (S/N): ");
                if (!respuesta.startsWith("s") && !respuesta.startsWith("S")) {
                    System.out.println("Cargando menú principal...");
                    trip = null;
                    break;
                }
                trip = null;
            }
            
        } while (trip == null);
        
        return trip;
    }
    
    /**
     * Imprime en consola la lista de viajes
     * @param trips Lista de viajes a imprimir
     */
    private void printListTrips(List<Trip> trips) {
        for (Trip aTrip : trips) {
            System.out.println(aTrip);
        }
    }
}
