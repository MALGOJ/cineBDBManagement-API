package com.example.cineBDB_managment.repository.crud;

import com.example.cineBDB_managment.model.entity.Room;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RoomCrudRepository extends CrudRepository<Room, Long> {

    Optional<Room> findById(Long idRoom);
    List<Room> findAll();
    boolean existsByName(String name);

    // Buscar salas con capacidad mayor o igual a X
    @Query("SELECT r FROM Room r WHERE r.capacity >= :minCapacity")
    List<Room> findByMinCapacity(@Param("minCapacity") int minCapacity);

    // Obtener salas disponibles para un horario específico
    @Query("SELECT r FROM Room r WHERE r.id NOT IN " +
            "(SELECT res.room.id FROM Reservation res WHERE res.schedule = :schedule)")
    List<Room> findAvailableRoomsBySchedule(@Param("schedule") LocalDateTime schedule);
}