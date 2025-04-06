package com.example.cineBDB_managment.service;

import com.example.cineBDB_managment.model.dto.RoomDto;
import com.example.cineBDB_managment.model.entity.Room;
import com.example.cineBDB_managment.model.mapper.RoomMapper;
import com.example.cineBDB_managment.repository.crud.RoomCrudRepository;
import com.example.cineBDB_managment.repository.inter.RoomRepositoryInterface;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RoomService implements RoomRepositoryInterface {

    private final RoomCrudRepository roomCrudRepository;
    private final RoomMapper roomMapper;

    public RoomService(RoomCrudRepository roomCrudRepository, RoomMapper roomMapper) {
        this.roomCrudRepository = roomCrudRepository;
        this.roomMapper = roomMapper;
    }

    @Override
    public RoomDto getRoom(Long idRoom) {
        if (idRoom == null) {
            throw new IllegalArgumentException("El ID de la sala no puede ser nulo");
        }
        Room room = roomCrudRepository.findById(idRoom)
                .orElseThrow(() -> new RuntimeException("Sala no encontrada"));
        return roomMapper.toRoomDto(room);
    }

    @Override
    public List<RoomDto> getAllRooms() {
        List<Room> rooms = roomCrudRepository.findAll();
        if (rooms.isEmpty()) {
            throw new RuntimeException("No se encontraron salas");
        }
        return roomMapper.toRoomDto(rooms);
    }

    @Override
    public List<RoomDto> getRoomsByMinCapacity(int minCapacity) {
        if (minCapacity <= 0) {
            throw new IllegalArgumentException("La capacidad mínima debe ser mayor que cero");
        }
        List<Room> rooms = roomCrudRepository.findByMinCapacity(minCapacity);
        if (rooms.isEmpty()) {
            throw new RuntimeException("No se encontraron salas con la capacidad mínima especificada");
        }
        return roomMapper.toRoomDto(rooms);
    }

    @Override
    public List<RoomDto> getAvailableRooms(LocalDateTime schedule) {
        if (schedule == null) {
            throw new IllegalArgumentException("El horario no puede ser nulo");
        }
        List<Room> rooms = roomCrudRepository.findAvailableRoomsBySchedule(schedule);
        if (rooms.isEmpty()) {
            throw new RuntimeException("No se encontraron salas disponibles para el horario especificado");
        }
        return roomMapper.toRoomDto(rooms);
    }

    @Override
    public boolean existsByName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
        }
        return roomCrudRepository.existsByName(name);
    }

    @Override
    public RoomDto saveRoom(RoomDto roomDto) {
        if (roomDto == null) {
            throw new IllegalArgumentException("El RoomDto no puede ser nulo");
        }
        Room room = roomMapper.toRoomEntity(roomDto);
        Room savedRoom = roomCrudRepository.save(room);
        return roomMapper.toRoomDto(savedRoom);
    }

    @Override
    public RoomDto updateRoom(Long idRoom, RoomDto roomDto) {
        if (idRoom == null) {
            throw new IllegalArgumentException("El ID de la sala no puede ser nulo");
        }
        if (roomDto == null) {
            throw new IllegalArgumentException("El RoomDto no puede ser nulo");
        }

        Room existingRoom = roomCrudRepository.findById(idRoom)
                .orElseThrow(() -> new RuntimeException("Sala no encontrada"));

        // Actualizar los campos de la entidad existente con los valores del DTO
        existingRoom.setName(roomDto.nameDto());
        existingRoom.setCapacity(roomDto.capacityDto());
        existingRoom.setUpdatedAt(LocalDateTime.now());

        Room updatedRoom = roomCrudRepository.save(existingRoom);
        return roomMapper.toRoomDto(updatedRoom);
    }

    @Override
    public void deleteRoom(Long idRoom) {
        if (idRoom == null) {
            throw new IllegalArgumentException("El ID de la sala no puede ser nulo");
        }
        roomCrudRepository.deleteById(idRoom);
    }
}