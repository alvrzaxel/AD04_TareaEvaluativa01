/**************************************************
 * Autor: Axel Álvarez Santos
 * Fecha: 21/12/2024
 * Tarea: AD04 Tarea Evaluativa 01
 **************************************************/

package com.ud04app.entities;

import jakarta.persistence.*;

// Clase entidad "Passenger" mapeada con la tabla "pasajero"
@Entity
@Table(name = "pasajero")
public class Passenger {
    
    // Clave primaria, valor autoincrementable
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    // Columna que almacena el email del pasajero
    @Column(name = "email")
    private String email;
    
    // Columna que almacena el nombre del pasajero
    @Column(name = "nombre")
    private String name;
    
    // Constructor por defecto
    public Passenger() {
    }
    
    // Constructor con parámetros
    public Passenger(String email, String name) {
        this.email = email;
        this.name = name;
    }
    
    // Getter para el ID del pasajero
    public int getId() {
        return id;
    }
    
    // Getter y Setter para el email del pasajero
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    // Getter y Setter para el nombre del pasajero
    public String getName() {
        return name;
    }
    
    public void setName(String nombre) {
        this.name = nombre;
    }
    
    // Representación en formato string del pasajero
    @Override
    public String toString() {
        return "PASAJERO #ID_" + id + "\n"
                + "- Nombre: " + name + "\n"
                + "- Email: " + email + "\n";
    }
}