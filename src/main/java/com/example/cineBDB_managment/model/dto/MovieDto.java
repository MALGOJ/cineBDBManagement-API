package com.example.cineBDB_managment.model.dto;

import java.time.LocalDateTime;

public record MovieDto(
        Long idMovieDto,
        String titleDto,
        String genderDto,
        int durationMinutesDto, // Duración en minutos
        String ratingDto, // Clasificación
        LocalDateTime createdAtMovieDto,
        LocalDateTime updatedAtMovieDto
) {
}
