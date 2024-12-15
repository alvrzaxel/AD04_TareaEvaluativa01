package com.ud04app.services;

import com.ud04app.entities.Conductor;
import com.ud04app.entities.Viaje;
import com.ud04app.util.Console;
import com.ud04app.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ViajeService {
    
    // Función principal para crear un nuevo viaje
    public void crearViaje() {
        
        // Obtiene la sesión de Hibernate y comienza una transacción
        Session session = (Session) HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        
        try {
            // Inicia la transacción
            transaction = session.beginTransaction();
            
            // Solicita los datos del viaje al usuario
            Viaje viaje = solicitaDatosViaje();
            // Obtiene el conductor si es necesario
            Conductor conductor = obtenerConductor(session, transaction);
            
            // Si el conductor es válido, se asocia al viaje
            if (conductor != null) {
                viaje.setConductor(conductor);
            }
            
            //
            session.merge(viaje);
            transaction.commit();
            System.out.println("Viaje creado correctamente.\n");
            System.out.println(viaje.toString());

            
        } catch (Exception e) {
            // Manejo de excepciones y rollback en caso de error
            System.out.println("Ha ocurrido un error inesperado: " + e.getMessage());
            System.out.println("Realizando rollback...");
            System.out.println(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    
    /**
     * Solicita los datos necesarios para crear un nuevo viaje
     * @return viaje
     */
    private Viaje solicitaDatosViaje() {
        Viaje viaje = null;
        String ciudadDestino, ciudadOrigen;
        Date fechaHora;
        int plazasDisponibles = 0;
        
     
        // Solicita las ciudades de destino y origen
        ciudadDestino = Console.readString("Introduce la ciudad de destino: ");
        ciudadOrigen = Console.readString("Introduce la ciudad de origen: ");
        
        do {
            // Solicita y valida la fecha y la hora
            String fechaStr = Console.readString("Introduce la fecha (dd/MM/yyyy): ");
            String horaStr = Console.readString("Introduce la hora (HH:mm): ");
            fechaHora = parseFechaHora(fechaStr, horaStr);
            
        } while (fechaHora == null);
        
        do {
            // Solicita y valida las plazas disponibles
            plazasDisponibles = Console.readInt("Introduce las plazas disponibles: ");
            if (plazasDisponibles < 0) {
                System.out.println("No puedes tener menos de 0 plazas disponibles.");
            }
            
        } while (plazasDisponibles < 0);
        
        // Crea el objeto viaje con los datos proporcionados
        viaje = new Viaje(ciudadDestino, ciudadOrigen, fechaHora, plazasDisponibles);
        
        return viaje;
    }
    
    /**
     * Obtiene un conductor disponible y asociarlo al viaje
     * @param session session
     * @param transaction transaction
     * @return conductor
     */
    private Conductor obtenerConductor(Session session, Transaction transaction) {
        Conductor conductor = null;
        
        // Pregunta al usuario si quiere asociar un conductor
        String respuestaAsociar = Console.readString("\n¿Quieres asociar un conductor al viaje? (S/N): ").toLowerCase();
        ConductorService conductorService = new ConductorService();
        
        // Si no se desea asociar un conductor, se retorna null
        if (!respuestaAsociar.startsWith("s") && !respuestaAsociar.startsWith("y")) {
            return null;
        }
        
        // Obtiene la lista de conductores disponibles
        List<Conductor> conductores = conductorService.getListConductor(session);
        
        // Si no hay conductores disponibles, permite crear uno nuevo
        if (conductores.isEmpty()) {
            System.out.println("No hay conductores disponibles.");
            
            String respuestaCrear = Console.readString("\n¿Quieres crear un nuevo conductor? (S/N): ").toLowerCase();
            
            if (respuestaCrear.startsWith("s") || respuestaCrear.startsWith("y")) {
                conductor = conductorService.solicitaDatosConductor();
                session.merge(conductor); // Guarda el nuevo conductor
                session.flush(); // Asegura que se persista inmediatamente
                conductores = conductorService.getListConductor(session); // Refresca la lista de conductores
            } else {
                return null;
            }
        }
        
        // Muestra la lista de conductores disponibles
        System.out.println("Conductores disponibles: ");
        for (Conductor conductor1 : conductores) {
            System.out.println(conductor1.toString());
        }
        
        int idConductor;
        do {
            // Permite al usuario elegir un conductor
            idConductor = Console.readInt("Elige el conductor (ID): ");
            conductor = session.get(Conductor.class, idConductor);
            
            // Si el conductor no es válido, solicita otra vez el ID
            if (conductor == null) {
                System.out.println("ID de conductor no válido, inténtalo de nuevo.");
            }
            
        } while (conductor == null);
        
        return conductor;
    }
    
    
    
    // Parsea una fecha y hora proporcionadas como cadenas de texto
    private Date parseFechaHora(String fecha, String hora) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String fechaHora = fecha.trim() + " " + hora.trim();
        
        try {
            // Intenta parsear la fechaHora a un objeto Date
            return formato.parse(fechaHora);
        } catch (ParseException e) {
            System.out.println("Error al parsear la fecha y hora. Asegúrate de usar el formato correcto.");
            return null;
        }
    }
    
    public void printListViajes() {
        // Obtiene la sesión de Hibernate y comienza una transacción
        Session session = (Session) HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        
        try {
            transaction = session.beginTransaction();
            
            // Obtiene la lista de conductores disponibles
            List<Viaje> viajes = getListViajesDisponibles(session);
            
            System.out.println("Viajes disponibles: ");
            for (Viaje viaje : viajes) {
                System.out.println(viaje.toString());
            }
            
            transaction.commit();
            
        } catch (Exception e) {
            // Manejo de excepciones y rollback en caso de error
            System.out.println("Ha ocurrido un error inesperado: " + e.getMessage());
            System.out.println("Realizando rollback...");
            System.out.println(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    
    private List<Viaje> getListViajesDisponibles(Session session) {
        Query<Viaje> query = session.createQuery("FROM Viaje s WHERE s.plazasDisponibles > 0", Viaje.class);
        return query.getResultList();
    }
}
