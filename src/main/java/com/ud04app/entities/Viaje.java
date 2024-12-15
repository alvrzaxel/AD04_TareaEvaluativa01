package com.ud04app.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "viaje")
public class Viaje {
    
    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @Column(name = "ciudadDestino")
    private String ciudadDestino;
    
    @Column(name = "ciudadOrigen")
    private String ciudadOrigen;
    
    @Temporal(TemporalType.TIMESTAMP) // Indica que es un campo de fecha y hora
    @Column(name = "fechaHora")
    private Date fechaHora;
    
    @Column(name = "plazasDisponibles")
    private int plazasDisponibles;
    
    // Un viaje está relacionado sólo con un conductor
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "conductorId")
    private Conductor conductor;
    
    public Viaje() {
    }
    
    public Viaje(String ciudadDestino, String ciudadOrigen, Date fechaHora, int plazasDisponibles) {
        this.ciudadDestino = ciudadDestino;
        this.ciudadOrigen = ciudadOrigen;
        this.fechaHora = fechaHora;
        this.plazasDisponibles = plazasDisponibles;
    }
    
    public Viaje(String ciudadDestino, String ciudadOrigen, Date fechaHora, int plazasDisponibles, Conductor conductor) {
        this.ciudadDestino = ciudadDestino;
        this.ciudadOrigen = ciudadOrigen;
        this.fechaHora = fechaHora;
        this.plazasDisponibles = plazasDisponibles;
        this.conductor = conductor;
    }
    
    // Getter ciudadDestino
    public String getCiudadDestino() {
        return ciudadDestino;
    }
    
    // Setter ciudadDestino
    public void setCiudadDestino(String ciudadDestino) {
        this.ciudadDestino = ciudadDestino;
    }
    
    // Getter ciudadOrigen
    public String getCiudadOrigen() {
        return ciudadOrigen;
    }
    
    // Setter ciudadOrigen
    public void setCiudadOrigen(String ciudadOrigen) {
        this.ciudadOrigen = ciudadOrigen;
    }
    
    // Getter fechaHora
    public Date getFechaHora() {
        return fechaHora;
    }
    
    // Setter fechaHora
    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }
    
    // Getter plazasDisponibles
    public int plazasDisponibles() {
        return plazasDisponibles;
    }
    
    // Setter plazasDisponibles
    public void plazasDisponibles(int plazasDisponibles) {
        this.plazasDisponibles = plazasDisponibles;
    }
    
    // Getter conductor
    public Conductor getConductor() {
        return conductor;
    }
    
    // Setter conductor
    public void setConductor(Conductor conductor) {
        this.conductor = conductor;
    }
    
    // toString
    @Override
    public String toString() {
        return "VIAJE #ID-" + id + "\n"
                + "- Ciudad Destino: " + ciudadDestino + "\n"
                + "- Ciudad Origen: " + ciudadOrigen + "\n"
                + "- Fecha y Hora: " + fechaHora + "\n"
                + "- Plazas Disponibles: " + plazasDisponibles + "\n"
                + conductor;
    }
    
}

