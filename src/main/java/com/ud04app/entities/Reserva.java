package com.ud04app.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "reserva")
public class Reserva {
    
    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @Column(name = "fechaReserva")
    private String fechaReserva;
    
    @Column(name = "numeroPlazasReservadas")
    private int numeroPlazasReservadas;
    
    // Una reserva está relacionada con sólo un viaje
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "viaje_id")
    private Viaje viaje;
    
    // Una reserva está relacionada con sólo un pasajero
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "pasajero_id")
    private Pasajero pasajero;
    
    public Reserva() {
    }
    
    public Reserva(String fechaReserva, int numeroPlazasReservadas, Viaje viaje, Pasajero pasajero) {
        this.fechaReserva = fechaReserva;
        this.numeroPlazasReservadas = numeroPlazasReservadas;
        this.viaje = viaje;
        this.pasajero = pasajero;
    }
    
    // Getter id
    private int getId() {
        return id;
    }
    
    // Getter fechaReserva
    private String getFechaReserva() {
        return fechaReserva;
    }
    
    // Setter fechaReserva
    private void setFechaReserva(String fechaReserva) {
        this.fechaReserva = fechaReserva;
    }
    
    // Getter numeroPlazasReservadas
    private int getNumeroPlazasReservadas() {
        return numeroPlazasReservadas;
    }
    
    // Setter numeroPlazasReservadas
    private void setNumeroPlazasReservadas(int numeroPlazasReservadas) {
        this.numeroPlazasReservadas = numeroPlazasReservadas;
    }
    
    // Getter viaje
    private Viaje getViaje() {
        return viaje;
    }
    
    // Setter viaje
    private void setViaje(Viaje viaje) {
        this.viaje = viaje;
    }
    
    // Getter pasajero
    private Pasajero getPasajero() {
        return pasajero;
    }
    
    // Setter pasajero
    private void setPasajero(Pasajero pasajero) {
        this.pasajero = pasajero;
    }
    
    // toString
    @Override
    public String toString() {
        return "Reserva{" +
                "id=" + id +
                ", fechaReserva='" + fechaReserva + '\'' +
                ", numeroPlazasReservadas=" + numeroPlazasReservadas +
                ", viaje=" + viaje +
                ", pasajero=" + pasajero +
                '}';
    }
}
