/**************************************************
 * Autor: Axel Álvarez Santos
 * Fecha: 21/12/2024
 * Tarea: AD04 Tarea Evaluativa 01
 **************************************************/

package com.ud04app.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Clase utilitaria para gestionar la configuración y el ciclo de vida de Hibernate
 * Proporciona una instancia de SessionFactory para manejar las sesiones con la base de datos
 */
public class HibernateUtil {
    // Instancia de la fábrica de sesiones Hibernate
    private static SessionFactory sessionFactory;
    
    static {
        try {
            // Inicialización de la fábrica de sesiones con la configuración de Hibernate
            sessionFactory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .buildSessionFactory();
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al inicializar Hibernate");
        }
    }
    
    /**
     * Obtiene la fábrica de sesiones de Hibernate
     * Proporciona acceso a la instancia de SessionFactory para gestionar las sesiones
     * @return La instancia de SessionFactory
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    /**
     * Cierra la fábrica de sesiones de Hibernate
     */
    public static void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            getSessionFactory().close();
        }
    }
}
