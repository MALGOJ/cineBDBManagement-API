package com.example.cineBDB_managment.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reservation", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) //por demanda
    @JoinColumn(name = "movie_id", referencedColumnName = "id_movie", nullable = false)
    private Movie movie; // Relación Many-to-One con Movie

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", referencedColumnName = "id_room", nullable = false)
    private Room room; // Relación Many-to-One con Room

    @Column(name = "customer_email", nullable = false, length = 100)
    private String customerEmail;

    @Column(name = "schedule", nullable = false)
    private LocalDateTime schedule; // Horario de la reserva

    @ElementCollection
    @CollectionTable(name = "reservation_seats", joinColumns = @JoinColumn(name = "reservation_id"))
    @Column(name = "seat_number")
    private List<String> selectedSeats; // Ej: ["A1", "B2"]

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}