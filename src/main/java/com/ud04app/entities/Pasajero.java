package com.ud04app.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pasajero")
public class Pasajero {
    
    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "nombre")
    private String nombre;
    
    public Pasajero() {
    
    }
    
    public Pasajero (String email, String nombre) {
        this.email = email;
        this.nombre = nombre;
    }
    
    // Getter id
    public int getId() {
        return id;
    }
    
    // Getter email
    public String getEmail() {
        return email;
    }
    
    // Setter email
    public void setEmail(String email) {
        this.email = email;
    }
    
    // Getter Nombre
    public String getNombre() {
        return nombre;
    }
    
    // Setter email
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    // toString
    @Override
    public String toString() {
    return "Pasajero{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';}
}
