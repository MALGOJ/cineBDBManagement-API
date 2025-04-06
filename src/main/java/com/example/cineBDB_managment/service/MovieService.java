package com.example.cineBDB_managment.service;

import com.example.cineBDB_managment.model.dto.MovieDto;
import com.example.cineBDB_managment.model.entity.Movie;
import com.example.cineBDB_managment.model.mapper.NewMovieMapper;
import com.example.cineBDB_managment.repository.crud.MovieCrudRepository;
import com.example.cineBDB_managment.repository.inter.MovieRepositoryInterface;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service

public class MovieService implements MovieRepositoryInterface {

    private final MovieCrudRepository movieCrudRepository;
    private final NewMovieMapper newMovieMapper;

    public MovieService(MovieCrudRepository movieCrudRepository, NewMovieMapper newMovieMapper) {
        this.movieCrudRepository = movieCrudRepository;
        this.newMovieMapper = newMovieMapper;
    }


    @Override
    public MovieDto getMovie(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID de la película no puede ser nulo");
        }
        Movie movie = movieCrudRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Película no encontrada"));
        return newMovieMapper.toMovieDto (movie);
    }

    @Override
    public List<MovieDto> getAllMovies() {
        List<Movie> movies = movieCrudRepository.findAll();
        if (movies.isEmpty()) {
            throw new RuntimeException("No se encontraron películas");
        }
        return newMovieMapper.toMovieDto (movies);
    }

    @Override
    public List<MovieDto> getMoviesByGender(String gender) {
        if (gender == null || gender.isEmpty()) {
            throw new IllegalArgumentException("El género no puede ser nulo o vacío");
        }
        List<Movie> movies = movieCrudRepository.findByGender(gender);
        if (movies == null || movies.isEmpty()) {
            throw new RuntimeException("No se encontraron películas para el género especificado");
        }
        return newMovieMapper.toMovieDto (movies);
    }

    @Override
    public boolean existsByTitle(String title) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("El título no puede ser nulo o vacío");
        }
        return movieCrudRepository.existsByTitle(title);
    }

    @Override
    public MovieDto saveMovie(MovieDto movieDto) {
        if (movieDto == null) {
            throw new IllegalArgumentException("El MovieDto no puede ser nulo");
        }
        Movie movie = newMovieMapper.toMovieEntity (movieDto);
        Movie savedMovie = movieCrudRepository.save(movie);
        return newMovieMapper.toMovieDto(savedMovie);
    }

    @Override
    public MovieDto updateMovie(Long idMovie, MovieDto movieDto) {
        if (idMovie == null) {
            throw new IllegalArgumentException("El ID de la película no puede ser nulo");
        }
        if (movieDto == null) {
            throw new IllegalArgumentException("El MovieDto no puede ser nulo");
        }

        Movie existingMovie = movieCrudRepository.findById(idMovie)
                .orElseThrow(() -> new RuntimeException("Película no encontrada"));

        // Actualizar los campos de la entidad existente con los valores del DTO
        existingMovie.setTitle(movieDto.titleDto());
        existingMovie.setGender(movieDto.genderDto());
        existingMovie.setDurationMinutes(movieDto.durationMinutesDto());
        existingMovie.setRating(movieDto.ratingDto());
        existingMovie.setUpdatedAt(LocalDateTime.now());

        Movie updatedMovie = movieCrudRepository.save(existingMovie);
        return newMovieMapper.toMovieDto(updatedMovie);
    }

    @Override
    public void deleteMovie(Long idMovie) {
        if (idMovie == null) {
            throw new IllegalArgumentException("El ID de la película no puede ser nulo");
        }
        movieCrudRepository.deleteById(idMovie);
    }

}