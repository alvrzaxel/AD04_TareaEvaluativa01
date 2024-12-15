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
    
    // Un conductor está relacionado con varios viajes
    @OneToMany(mappedBy = "conductor", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    List<Viaje> viajes = new ArrayList<>();
    
    public Conductor() {
    }
    
    public Conductor(String name, String vehiculo, List<Viaje> viajes) {
        this.name = name;
        this.vehiculo = vehiculo;
        this.viajes = viajes;
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
    
    // Getter viajes
    public List<Viaje> getViajes() {
        return viajes;
    }
    
    // Setter viajes
    public void setViajes(List<Viaje> viajes) {
        this.viajes = viajes;
    }
    
    // toString
    @Override
    public String toString() {
        return "Conductor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", vehiculo='" + vehiculo + '\'' +
                ", viajes=" + viajes +
                '}';
    }
}
