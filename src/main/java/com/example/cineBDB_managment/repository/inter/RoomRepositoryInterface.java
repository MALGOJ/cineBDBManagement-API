package com.example.cineBDB_managment.repository.inter;

import com.example.cineBDB_managment.model.dto.RoomDto;

import java.time.LocalDateTime;
import java.util.List;

public interface RoomRepositoryInterface {

    RoomDto getRoom(Long idRoom);
    List<RoomDto> getAllRooms();
    List<RoomDto> getRoomsByMinCapacity(int minCapacity);
    List<RoomDto> getAvailableRooms(LocalDateTime schedule);
    boolean existsByName(String name);
    RoomDto saveRoom(RoomDto roomDto);
    RoomDto updateRoom(Long idRoom, RoomDto roomDto);
    void deleteRoom(Long idRoom);
}