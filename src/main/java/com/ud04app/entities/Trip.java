/**************************************************
 * Autor: Axel Álvarez Santos
 * Fecha: 21/12/2024
 * Tarea: AD04 Tarea Evaluativa 01
 **************************************************/

package com.ud04app.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Clase entidad "Viaje" mapeada a la tabla "viaje"
@Entity
@Table(name = "viaje")
public class Trip {
    
    // Clave primaria, valor autoincrementable
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    // Columna que almacena la ciudad de destino
    @Column(name = "ciudadDestino")
    private String destinationCity;
    
    // Columna que almacena la ciudad de origen
    @Column(name = "ciudadOrigen")
    private String cityOfOrigin;
    
    // Columna que almacena la fecha y hora del viaje
    // @Temporal especifica que el campo es de tipo fecha y hora en la base de datos
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fechaHora")
    private LocalDateTime dateTime;
    
    // Columna que almacena el número de plazas disponibles en el viaje
    @Column(name = "plazasDisponibles")
    private int seatsAvailable;
    
    // Relación de muchos a uno con la entidad "Conductor"
    // @JoinColumn define la columna en la base de datos que establece esta relación.
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "conductorId")
    private Driver driver;
    
    // Constructor por defecto
    public Trip() {
    }
    
    // Constructor con parámetros: ciudadDestino, ciudadOrigen, fechaHora y plazasDisponibles
    public Trip(String destinationCity, String cityOfOrigin, LocalDateTime dateTime, int seatsAvailable) {
        this.destinationCity = destinationCity;
        this.cityOfOrigin = cityOfOrigin;
        this.dateTime = dateTime;
        this.seatsAvailable = seatsAvailable;
    }
    
    // Constructor con parámetros completos, incluyendo conductor
    public Trip(String destinationCity, String cityOfOrigin, LocalDateTime dateTime, int seatsAvailable, Driver driver) {
        this(destinationCity, cityOfOrigin, dateTime, seatsAvailable);
        this.driver = driver;
    }
    
    // Getter para el ID del viaje
    public int getId() {
        return id;
    }
    
    // Getter y Setter para la ciudad de destino del viaje
    public String getDestinationCity() {
        return destinationCity;
    }
    
    public void setDestinationCity(String ciudadDestino) {
        this.destinationCity = ciudadDestino;
    }
    
    // Getter y setter para la ciudad de origen del viaje
    public String getCityOfOrigin() {
        return cityOfOrigin;
    }
    
    public void setCityOfOrigin(String ciudadOrigen) {
        this.cityOfOrigin = ciudadOrigen;
    }
    
    // Getter y Setter para la fecha y la hora del viaje
    public LocalDateTime getDateAndTime() {
        return dateTime;
    }
    
    public void setDateAndTime(LocalDateTime fechaHora) {
        this.dateTime = fechaHora;
    }
    
    // Getter y Setter para las plazas disponibles del viaje
    public int getSeatsAvailable() {
        return seatsAvailable;
    }
    
    public void setSeatsAvailable(int seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }
    
    // Getter y Setter para el conductor relacionado con el viaje
    public Driver getDriver() {
        return driver;
    }
    
    public void setDriver(Driver driver) {
        this.driver = driver;
    }
    
    // Representación en formato string legible de la reserva
    @Override
    public String toString() {
        // Define un formato personalizado para la fecha y hora
        DateTimeFormatter formatoFechaHora = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy, HH:mm'h'");
        
        // Formatea la fechaHora con el formato personalizado
        String fechaHoraFormateada = dateTime.format(formatoFechaHora);
        
        return "VIAJE #ID_" + id + "\n"
                + "- Ciudad Destino: " + destinationCity + "\n"
                + "- Ciudad Origen: " + cityOfOrigin + "\n"
                + "- Fecha y Hora: " + fechaHoraFormateada + "\n"
                + "- Plazas Disponibles: " + seatsAvailable + "\n"
                + driver;
    }
}