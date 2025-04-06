package com.example.cineBDB_managment.model.mapper;

import com.example.cineBDB_managment.model.dto.MovieDto;
import com.example.cineBDB_managment.model.entity.Movie;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(    componentModel = "spring",  // Integración con Spring
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NewMovieMapper {

    @Mapping(source = "id", target = "idMovieDto")
    @Mapping(source = "title", target = "titleDto")
    @Mapping(source = "gender", target = "genderDto")
    @Mapping(source = "durationMinutes", target = "durationMinutesDto")
    @Mapping(source = "rating", target = "ratingDto")
    @Mapping(source = "createdAt", target = "createdAtMovieDto")
    @Mapping(source = "updatedAt", target = "updatedAtMovieDto")
    MovieDto toMovieDto(Movie movie);

    List<MovieDto> toMovieDto(List<Movie> movieList);

    @InheritInverseConfiguration
    @Mapping(target = "rooms", ignore = true)
    Movie toMovieEntity(MovieDto movieDto);

    @InheritInverseConfiguration
    List<Movie> toMovieEntity(List<MovieDto> movieDtoList);
}