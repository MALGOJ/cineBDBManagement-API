package com.example.cineBDB_managment.model.dto;

public record SeatDto(
        String seatId, // Ej: "A1", "B5"
        boolean isAvailable,
        Long roomId
) {
}
