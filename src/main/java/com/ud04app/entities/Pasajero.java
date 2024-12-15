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
    
    // Un pasajero está relacionado con varias reservas
    @OneToMany(mappedBy = "pasajero", cascade = CascadeType.ALL)
    List<Reserva> reservas = new ArrayList<>();
    
    public Pasajero() {
    
    }
    
    public Pasajero (String email, String nombre, List<Reserva> reservas) {
        this.email = email;
        this.nombre = nombre;
        this.reservas = reservas;
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
    
    // Getter reservas
    public List<Reserva> getReservas() {
        return reservas;
    }
    
    // Setter reservas
    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }
    
    // toString
    @Override
    public String toString() {
    return "Pasajero{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", nombre='" + nombre + '\'' +
                ", reservas=" + reservas +
                '}';}
}
