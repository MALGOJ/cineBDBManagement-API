package com.example.cineBDB_managment.controller;

import com.example.cineBDB_managment.model.dto.ReservationDto;
import com.example.cineBDB_managment.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/reservations")
@Tag(name = "Reservations", description = "Endpoints for managing seat reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Operation(summary = "Get all reservations", description = "Retrieve a list of all reservations")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of reservations")
    @ApiResponse(responseCode = "401", description = "Unauthorized access")
    @GetMapping
    public ResponseEntity<List<ReservationDto>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @Operation(summary = "Get reservation by ID", description = "Retrieve a reservation by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Reservation found and returned")
    @ApiResponse(responseCode = "404", description = "Reservation not found")
    @ApiResponse(responseCode = "401", description = "Unauthorized access")
    @GetMapping("/{id}")
    public ResponseEntity<ReservationDto> getReservationById(
            @Parameter(description = "ID of the reservation to be retrieved") @PathVariable Long id) {
        return ResponseEntity.ok(reservationService.getReservation(id));
    }

    @Operation(summary = "Create a new reservation", description = "Make a new seat reservation")
    @ApiResponse(responseCode = "201", description = "Reservation created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid reservation data or seat not available")
    @ApiResponse(responseCode = "401", description = "Unauthorized access")
    @PostMapping
    public ResponseEntity<ReservationDto> createReservation(
            @Valid @RequestBody ReservationDto reservationDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reservationService.saveReservation(reservationDto));
    }

    @Operation(summary = "Get reservations by movie", description = "Get all reservations for a specific movie")
    @ApiResponse(responseCode = "200", description = "List of reservations for the movie")
    @ApiResponse(responseCode = "401", description = "Unauthorized access")
    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<ReservationDto>> getReservationsByMovie(
            @Parameter(description = "ID of the movie") @PathVariable Long movieId) {
        return ResponseEntity.ok(reservationService.getReservationsByMovie(movieId));
    }

    @Operation(summary = "Get reservations by customer email", description = "Get all reservations for a specific customer")
    @ApiResponse(responseCode = "200", description = "List of reservations for the customer")
    @ApiResponse(responseCode = "401", description = "Unauthorized access")
    @GetMapping("/customer/{email}")
    public ResponseEntity<List<ReservationDto>> getReservationsByCustomer(
            @Parameter(description = "Email of the customer") @PathVariable String email) {
        return ResponseEntity.ok(reservationService.getReservationsByCustomerEmail(email));
    }

    @Operation(summary = "Get available seats", description = "Get available seats for a room at a specific time")
    @ApiResponse(responseCode = "200", description = "List of available seats")
    @ApiResponse(responseCode = "400", description = "Invalid parameters")
    @ApiResponse(responseCode = "401", description = "Unauthorized access")
    @GetMapping("/available-seats")
    public ResponseEntity<List<String>> getAvailableSeats(
            @Parameter(description = "ID of the room") @RequestParam Long roomId,
            @Parameter(description = "Date and time of the screening (format: yyyy-MM-ddTHH:mm:ss)")
            @RequestParam LocalDateTime schedule) {
        return ResponseEntity.ok(reservationService.getAvailableSeats(roomId, schedule));
    }
    @Operation(summary = "Get reservation report", description = "Generate a report of reservations by movie")
    @ApiResponse(responseCode = "200", description = "Report generated successfully")
    @ApiResponse(responseCode = "401", description = "Unauthorized access")
    @GetMapping("/report")
    public ResponseEntity<Map<String, Long>> getReservationReport() {
        return ResponseEntity.ok(reservationService.generateReservationReport());
    }

    @Operation(summary = "Delete a reservation", description = "Cancel an existing reservation")
    @ApiResponse(responseCode = "204", description = "Reservation deleted successfully")
    @ApiResponse(responseCode = "404", description = "Reservation not found")
    @ApiResponse(responseCode = "401", description = "Unauthorized access")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(
            @Parameter(description = "ID of the reservation to be deleted") @PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}