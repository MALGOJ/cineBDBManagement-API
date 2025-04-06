package com.example.cineBDB_managment.model.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ReservationDto(
        Long idReservationDto,
        Long movieIdDto, // ID de la película (relación)
        Long roomIdDto,  // ID de la sala (relación)
        String customerEmailDto, // Email del cliente
        LocalDateTime scheduleDto, // Horario de la reserva
        List<String> selectedSeatsDto, // Ej: ["A1", "A2"]
        LocalDateTime createdAtReservationDto,
        LocalDateTime updatedAtReservationDto
) {
}
