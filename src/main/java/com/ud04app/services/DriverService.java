/**************************************************
 * Autor: Axel Álvarez Santos
 * Fecha: 21/12/2024
 * Tarea: AD04 Tarea Evaluativa 01
 **************************************************/

package com.ud04app.services;

import com.ud04app.entities.Driver;
import com.ud04app.util.Console;
import com.ud04app.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Clase servicio para gestionar las operaciones relacionadas con los conductores en la base de datos
 * Creación y obtención de conductores
 */
public class DriverService {
    
    /**
     * Crea un nuevo conductor en la base de datos
     * Si ocurre algún error, realiza un rollback de la transacción
     */
    public void createDriver() {
        // Abre una sesión de Hibernate
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = null;
            
            try {
                // Inicia la transacción
                transaction = session.beginTransaction();
                
                // Solicita los datos del driver
                Driver driver = requestDriverData();
                
                // Guarda el conductor y confirma los cambios en la base de datos
                session.persist(driver);
                transaction.commit();
                
                // Mensaje informativo al usuario
                System.out.println("\nConductor creado correctamente:");
                System.out.println(driver.toString());
                
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
     * Solicita al usuario los datos necesarios para crear un conductor
     * @return El conductor con los datos proporcionados por el usuario
     */
    public Driver requestDriverData() {
        // Solicita el nombre y el vehículo del nuevo conductor al usuario
        String name = Console.readString("Introduce el nombre del nuevo conductor: ");
        String vehicle = Console.readString("Introduce el vehículo del nuevo conductor: ");
        
        // Devuelve objeto Conductor con los datos proporcionados
        return new Driver(name, vehicle);
    }
    
    /**
     * Permite al usuario seleccionar un conductor de una lista de conductores disponibles
     * @param session La sesión de Hibernate utilizada para la consulta
     * @return Conductor El conductor seleccionado
     */
    public Driver getsDriver(Session session) {
        // Obtiene la lista de conductores disponibles en la base de datos
        List<Driver> drivers = getListDrivers(session);
        
        // Si no existen conductores, devuelve null
        if (drivers.isEmpty()) {
            System.out.println(
                    """
                    No existen conductores disponibles en la base de datos.
                    Para crear un viaje, por favor, crea primero un nuevo conductor.
                    """
            );
            return null;
        }
        
        // Muestra la lista de conductores disponibles
        System.out.println("Conductores disponibles: ");
        printDrivers(drivers);
        
        // Permite al usuario seleccionar un conductor
        return selectDriver(session);
    }
    
    /**
     * Permite al usuario seleccionar un conductor por su ID
     * @param session La sesión de Hibernate utilizada para obtener el conductor
     * @return El conductor seleccionado
     */
    private Driver selectDriver(Session session) {
        Driver driver;
        
        do {
            // Bucle hasta que se seleccione un conductor válido
            int idDriver = Console.readInt("Elige el conductor (ID): ");
            driver = session.get(Driver.class, idDriver);
            
            // Si el conductor no es válido, solicita otra vez el ID
            if (driver == null) {
                System.out.println("ID de conductor no válido, inténtalo de nuevo.");
            }
            
        } while (driver == null);
        
        return driver;
    }
    
    /**
     * Obtiene la lista de todos los conductores disponibles en la base de datos
     * @param session La sesión de Hibernate usada para la consulta
     * @return Lis<Conductor>: Lista de objetos Conductor recuperados de la base de datos
     */
    private List<Driver> getListDrivers(Session session) {
        // Crea una consulta HQL y la ejecuta para obtener todos los conductores almacenados
        Query<Driver> query = session.createQuery("FROM Driver", Driver.class);
        return query.getResultList();
    }
    
    /**
     * Muestra la lista de conductores disponibles
     * @param drivers Lista de conductores a mostrar
     */
    private void printDrivers(List<Driver> drivers) {
        for (Driver aDriver : drivers) {
            System.out.println(aDriver);
        }
    }
}