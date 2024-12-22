/**************************************************
 * Autor: Axel Álvarez Santos
 * Fecha: 21/12/2024
 * Tarea: AD04 Tarea Evaluativa 01
 **************************************************/

package com.ud04app.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Clase entidad "Reservation" mapeada con la tabla "reserva"
@Entity
@Table(name = "reserva")
public class Reservation {
    
    // Clave primaria, valor autoincrementable
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    // Columna que almacena la fecha de la reserva
    @Column(name = "fechaReserva")
    private LocalDateTime reservationDate;
    
    // Columna que almacena el número de plazas reservadas
    @Column(name = "numeroPlazasReservadas")
    private int numberOfReservedSeats;
    
    // Relación de muchos a uno con la entidad "Viaje"
    // @JoinColumn define la columna que establece la relación en la base de datos
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "viaje_id")
    private Trip trip;
    
    // Relación de muchos a uno con la entidad "Pasajero"
    // @JoinColumn define la columna que establece la relación en la base de datos.
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "pasajero_id")
    private Passenger passenger;
    
    // Constructor por defecto, inicializa la fecha de reserva al momento actual
    public Reservation() {
        this.reservationDate = LocalDateTime.now();
    }
    
    // Constructor con número de plazas reservadas
    public Reservation(int numberOfReservedSeats) {
        this();
        this.numberOfReservedSeats = numberOfReservedSeats;
    }
    
    // Constructor con número de plazas, viaje y pasajero
    public Reservation(int numberOfReservedSeats, Trip trip, Passenger passenger) {
        this(numberOfReservedSeats);
        this.trip = trip;
        this.passenger = passenger;
    }
    
    // Getter para el ID de la reserva
    public int getId() {
        return id;
    }
    
    // Getter y Setter para la fecha de la reserva
    public LocalDateTime getReservationDate() {
        return reservationDate;
    }
    
    public void setReservationDate(LocalDateTime fechaReserva) {
        this.reservationDate = fechaReserva;
    }
    
    // Getter y Setter para el número de plazas reservadas en la reserva
    public int getNumberOfReservedSeats() {
        return numberOfReservedSeats;
    }
    
    public void setNumberOfReservedSeats(int numeroPlazasReservadas) {
        this.numberOfReservedSeats = numeroPlazasReservadas;
    }
    
    // Getter y Setter para el viaje relacionado con la reserva
    public Trip getTravel() {
        return trip;
    }
    
    public void setTravel(Trip trip) {
        this.trip = trip;
    }
    
    // Getter y Setter para el pasajero relacionado con la reserva
    public Passenger getPassenger() {
        return passenger;
    }
    
    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }
    
    // Representación en formato string legible de la reserva
    @Override
    public String toString() {
        // Define un formato personalizado para la fecha y hora
        DateTimeFormatter formatoFechaHora = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy, HH:mm'h'");
        
        // Formatea la fechaHora con el formato personalizado
        String fechaHoraFormateada = reservationDate.format(formatoFechaHora);
        
        return "RESERVA #ID_" + id + "\n"
                + "- Fecha reserva: " + fechaHoraFormateada + "\n"
                + "- Número de plazas reservadas: " + numberOfReservedSeats + "\n"
                + trip
                + passenger;
    }
}