package com.example.cineBDB_managment.model.dto;

import java.time.LocalDateTime;

public record RoomDto(
        Long idRoomDto,
        String nameDto,
        int capacityDto, // Capacidad total de asientos
        LocalDateTime createdAtRoomDto,
        LocalDateTime updatedAtRoomDto
) {
}
