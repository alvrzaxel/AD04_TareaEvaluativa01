package com.ud04app.services;



import com.ud04app.entities.Conductor;
import com.ud04app.util.Console;
import com.ud04app.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Clase para gestionar las operaciones relacionadas con los conductores en la base de datos
 * Incluye la creación de conductores, la obtención de una lista de conductores y la interacción con el
 * usuario para la introducción de datos
 */
public class ConductorService {
    
    
    /**
     * Crea un nuevo conductor en la base de datos
     * Abre una sesión de Hibernate, solicita los datos del conductor y lo guarda en la base de datos
     * Si ocurre algún error, realiza un rollback
     */
    public void crearConductor() {
        // Obtiene la sesión actual de Hibernate
        Session session = (Session) HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        
        try {
            // Inicia la transacción
            transaction = session.beginTransaction();
            
            // Crea el objeto Conductor con los datos proporcionados por el usuario
            Conductor conductor = solicitaDatosConductor();
            
            // Guarda el conductor en la base de datos (si existe, lo fusiona)
            session.merge(conductor);
            
            // Commit de la transacción para confirmar los cambios en la base de datos
            transaction.commit();
            
            // Mensaje informativo
            System.out.println("Conductor creado correctamente.");
            System.out.println(conductor.toString());
            
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
            // Si ocurre un error, realiza un rollback de la transacción
            if (transaction != null) {
                System.out.println("Realizando rollback...");
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    
    /**
     * Solicita al usuario los datos necesarios para crear un conductor
     * @return Conductor: Objeto con los datos proporcionados por el usuario
     */
    public Conductor solicitaDatosConductor() {
        Conductor conductor = new Conductor();
        String nombre = "";
        String vehiculo = "";

        // Solicita el nombre y el vehículo del conductor al usuario
        nombre = Console.readString("Introduce el nombre del nuevo conductor: ");
        vehiculo = Console.readString("Introduce el vehículo del nuevo conductor: ");
        
        // Crea el objeto Conductor con los datos proporcionados
        conductor = new Conductor(nombre, vehiculo);
        
        return conductor;
    }
    
    /**
     * Obtiene la lista de todos los conductores disponibles en la base de datos
     * @param session La sesión de Hibernate usada para la consulta
     * @return Lis<Conductor>: Lista de objetos Conductor recuperados de la base de datos
     */
    public List<Conductor> getListConductor(Session session) {
        // Crea una consulta HQL para obtener todos los conductores
        Query<Conductor> query = session.createQuery("FROM Conductor", Conductor.class);
        return query.getResultList();
    }
}
