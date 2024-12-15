package com.ud04app.util;

import com.ud04app.entities.Conductor;
import com.ud04app.entities.Pasajero;
import com.ud04app.entities.Reserva;
import com.ud04app.entities.Viaje;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateUtil {
    
    private static SessionFactory sessionFactory;
    
    static {
        try {
            sessionFactory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(Conductor.class)
                    .addAnnotatedClass(Pasajero.class)
                    .addAnnotatedClass(Reserva.class)
                    .addAnnotatedClass(Viaje.class)
                    .buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al inicializar Hibernate");
        }
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public static void shutdown() {
        getSessionFactory().close();
    }
}
