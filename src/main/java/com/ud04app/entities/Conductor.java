package com.ud04app.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "conductor")
public class Conductor {
    
    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @Column(name = "nombre")
    private String name;
    
    @Column(name = "vehiculo")
    private String vehiculo;
    
    
    public Conductor() {
    }
    
    public Conductor(String name, String vehiculo) {
        this.name = name;
        this.vehiculo = vehiculo;
    }
    
    // Getter id
    public int getId() {
        return id;
    }
    
    // Getter name
    public String getName() {
        return name;
    }
    
    // Setter name
    public void setName(String name) {
        this.name = name;
    }
    
    // Getter vehiculo
    public String getVehiculo() {
        return vehiculo;
    }
    
    // Setter vehiculo
    public void setVehiculo(String vehiculo) {
        this.vehiculo = vehiculo;
    }
    
    
    // toString
    @Override
    public String toString() {
        return "CONDUCTOR #ID-" + id + "\n" +
                "- Nombre: " + name + "\n" +
                "- Vehículo: " + vehiculo + "\n";
    }
}
