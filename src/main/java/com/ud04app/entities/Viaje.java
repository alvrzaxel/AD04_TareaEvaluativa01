package com.ud04app.entities;

import jakarta.persistence.*;

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
    
    @Column(name = "fechaHora")
    private String fechaHora;
    
    @Column(name = "plazasDisponibles")
    private int plazasDisponibles;
    
    // Un viaje está relacionado sólo con un conductor
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "conductor_id")
    private Conductor conductor;
    
    public Viaje() {
    }
    
    public Viaje(String ciudadDestino, String ciudadOrigen, String fechaHora, int plazasDisponibles, Conductor conductor) {
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
    public String getFechaHora() {
        return fechaHora;
    }
    
    // Setter fechaHora
    public void setFechaHora(String fechaHora) {
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
    
    // toString
    @Override
    public String toString() {
        return "Viaje{" +
                "id=" + id +
                ", ciudadDestino='" + ciudadDestino + '\'' +
                ", ciudadOrigen='" + ciudadOrigen + '\'' +
                ", fechaHora='" + fechaHora + '\'' +
                ", plazasDisponibles=" + plazasDisponibles +
                ", conductor=" + conductor +
                '}';
    }
    
}

