package com.example.cineBDB_managment.repository.inter;

import com.example.cineBDB_managment.model.dto.MovieDto;
import java.util.List;

public interface MovieRepositoryInterface {

    MovieDto getMovie(Long idMovie);
    List<MovieDto> getAllMovies();
    List<MovieDto> getMoviesByGender(String genre);
   // List<MovieDto> searchMoviesByTitle(String title);
    boolean existsByTitle(String title);
    MovieDto saveMovie(MovieDto movieDto);
    MovieDto updateMovie(Long idMovie, MovieDto movieDto);
    void deleteMovie(Long idMovie);
}