package com.example.cineBDB_managment.controller;

import com.example.cineBDB_managment.model.dto.MovieDto;
import com.example.cineBDB_managment.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
@Tag(name = "Movies", description = "Endpoints for managing movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @Operation(summary = "Get all movies", description = "Retrieve a list of all available movies")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of movies")
    @ApiResponse(responseCode = "401", description = "Unauthorized access")
    @GetMapping
    public ResponseEntity<List<MovieDto>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @Operation(summary = "Get movie by ID", description = "Retrieve a movie by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Movie found and returned")
    @ApiResponse(responseCode = "404", description = "Movie not found")
    @ApiResponse(responseCode = "401", description = "Unauthorized access")
    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> getMovieById(
            @Parameter(description = "ID of the movie to be retrieved") @PathVariable Long id) {
        return ResponseEntity.ok(movieService.getMovie(id));
    }

    @Operation(summary = "Create a new movie", description = "Add a new movie to the system")
    @ApiResponse(responseCode = "201", description = "Movie created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid movie data")
    @ApiResponse(responseCode = "401", description = "Unauthorized access")
    @PostMapping
    public ResponseEntity<MovieDto> createMovie(
            @Valid @RequestBody MovieDto movieDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(movieService.saveMovie(movieDto));
    }

    @Operation(summary = "Update a movie", description = "Update an existing movie's information")
    @ApiResponse(responseCode = "200", description = "Movie updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid movie data")
    @ApiResponse(responseCode = "404", description = "Movie not found")
    @ApiResponse(responseCode = "401", description = "Unauthorized access")
    @PutMapping("/{id}")
    public ResponseEntity<MovieDto> updateMovie(
            @Parameter(description = "ID of the movie to be updated") @PathVariable Long id,
            @Valid @RequestBody MovieDto movieDto) {
        return ResponseEntity.ok(movieService.updateMovie(id, movieDto));
    }

    @Operation(summary = "Delete a movie", description = "Remove a movie from the system")
    @ApiResponse(responseCode = "204", description = "Movie deleted successfully")
    @ApiResponse(responseCode = "404", description = "Movie not found")
    @ApiResponse(responseCode = "401", description = "Unauthorized access")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(
            @Parameter(description = "ID of the movie to be deleted") @PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get movies by gender", description = "Filter movies by their gender")
    @ApiResponse(responseCode = "200", description = "List of movies by gender")
    @ApiResponse(responseCode = "401", description = "Unauthorized access")
    @GetMapping("/gender/{gender}")
    public ResponseEntity<List<MovieDto>> getMoviesByGender(
            @Parameter(description = "Gender to filter by") @PathVariable String gender) {
        return ResponseEntity.ok(movieService.getMoviesByGender(gender));
    }
/*
    @Operation(summary = "Assign room to movie", description = "Assign a room for a movie screening")
    @ApiResponse(responseCode = "200", description = "Room assigned successfully")
    @ApiResponse(responseCode = "400", description = "Invalid parameters")
    @ApiResponse(responseCode = "401", description = "Unauthorized access")
    @PostMapping("/{movieId}/assign-room/{roomId}")
    public ResponseEntity<Void> assignRoomToMovie(
            @PathVariable Long movieId,
            @PathVariable Long roomId) {
        movieService.assignRoomToMovie(movieId, roomId);
        return ResponseEntity.ok().build();
    }

 */
}