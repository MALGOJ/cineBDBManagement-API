package com.example.cineBDB_managment.repository.crud;

import com.example.cineBDB_managment.model.entity.Reservation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationCrudRepository extends CrudRepository<Reservation, Long> {

    Optional<Reservation> findById(Long idReservation);
    List<Reservation> findAll();

    // Buscar reservas por email de cliente
    @Query("SELECT r FROM Reservation r WHERE r.customerEmail = :email")
    List<Reservation> findByCustomerEmail(@Param("email") String email);

    // Buscar reservas por película
    @Query("SELECT r FROM Reservation r WHERE r.movie.id = :movieId")
    List<Reservation> findByMovieId(@Param("movieId") Long movieId);

    // Buscar reservas por sala y horario
    @Query("SELECT r FROM Reservation r WHERE r.room.id = :roomId AND r.schedule = :schedule")
    List<Reservation> findByRoomAndSchedule(
            @Param("roomId") Long roomId,
            @Param("schedule") LocalDateTime schedule
    );

    // Verificar disponibilidad de asientos en una sala y horario
    @Query("SELECT r.selectedSeats FROM Reservation r WHERE r.room.id = :roomId AND r.schedule = :schedule")
    List<List<String>> findBookedSeatsByRoomAndSchedule(
            @Param("roomId") Long roomId,
            @Param("schedule") LocalDateTime schedule
    );
}