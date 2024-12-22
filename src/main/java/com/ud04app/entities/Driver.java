/**************************************************
 * Autor: Axel Álvarez Santos
 * Fecha: 21/12/2024
 * Tarea: AD04 Tarea Evaluativa 01
 **************************************************/

package com.ud04app.entities;

import jakarta.persistence.*;

// Clase entidad "Driver" mapeada a la tabla "conductor"
@Entity
@Table(name = "conductor")
public class Driver {
    
    // Clave primaria, valor autoincrementable
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    // Columna que almacena el nombre del conductor
    @Column(name = "nombre")
    private String name;
    
    // Columna que almacena el vehículo del conductor
    @Column(name = "vehiculo")
    private String vehicle;
    
    // Constructor por defecto
    public Driver() {
    }
    
    // Constructor con parámetros
    public Driver(String name, String vehicle) {
        this.name = name;
        this.vehicle = vehicle;
    }
    
    // Getter para el ID del conductor
    public int getId() {
        return id;
    }
    
    // Getter y Setter para el nombre del conductor
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    // Getter y Setter para el vehiculo del conductor
    public String getVehicle() {
        return vehicle;
    }
    
    public void setVehicle(String vehiculo) {
        this.vehicle = vehiculo;
    }
    
    // Representación en formato string del conductor
    @Override
    public String toString() {
        return "CONDUCTOR #ID_" + id + "\n" +
                "- Nombre: " + name + "\n" +
                "- Vehículo: " + vehicle + "\n";
    }
}