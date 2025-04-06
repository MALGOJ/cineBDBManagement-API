package com.example.cineBDB_managment.controller;

import com.example.cineBDB_managment.model.dto.RoomDto;
import com.example.cineBDB_managment.service.RoomService;
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

@RestController
@RequestMapping("/api/v1/rooms")
@Tag(name = "Rooms", description = "Endpoints for managing cinema rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @Operation(summary = "Get all rooms", description = "Retrieve a list of all available rooms")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of rooms")
    @ApiResponse(responseCode = "401", description = "Unauthorized access")
    @GetMapping
    public ResponseEntity<List<RoomDto>> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @Operation(summary = "Get available rooms", description = "Get rooms available for a specific schedule")
    @ApiResponse(responseCode = "200", description = "List of available rooms")
    @ApiResponse(responseCode = "400", description = "Invalid date/time format")
    @ApiResponse(responseCode = "401", description = "Unauthorized access")
    @GetMapping("/available")
    public ResponseEntity<List<RoomDto>> getAvailableRooms(
            @Parameter(description = "Date and time to check availability (format: yyyy-MM-ddTHH:mm:ss)")
            @RequestParam LocalDateTime schedule) {
        return ResponseEntity.ok(roomService.getAvailableRooms(schedule));
    }

    @Operation(summary = "Get room by ID", description = "Retrieve a room by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Room found and returned")
    @ApiResponse(responseCode = "404", description = "Room not found")
    @ApiResponse(responseCode = "401", description = "Unauthorized access")
    @GetMapping("/{id}")
    public ResponseEntity<RoomDto> getRoomById(
            @Parameter(description = "ID of the room to be retrieved") @PathVariable Long id) {
        return ResponseEntity.ok(roomService.getRoom(id));
    }

    @Operation(summary = "Create a new room", description = "Add a new room to the system")
    @ApiResponse(responseCode = "201", description = "Room created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid room data")
    @ApiResponse(responseCode = "401", description = "Unauthorized access")
    @PostMapping
    public ResponseEntity<RoomDto> createRoom(
            @Valid @RequestBody RoomDto roomDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(roomService.saveRoom(roomDto));
    }

    @Operation(summary = "Update a room", description = "Update an existing room's information")
    @ApiResponse(responseCode = "200", description = "Room updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid room data")
    @ApiResponse(responseCode = "404", description = "Room not found")
    @ApiResponse(responseCode = "401", description = "Unauthorized access")
    @PutMapping("/{id}")
    public ResponseEntity<RoomDto> updateRoom(
            @Parameter(description = "ID of the room to be updated") @PathVariable Long id,
            @Valid @RequestBody RoomDto roomDto) {
        return ResponseEntity.ok(roomService.updateRoom(id, roomDto));
    }

    @Operation(summary = "Delete a room", description = "Remove a room from the system")
    @ApiResponse(responseCode = "204", description = "Room deleted successfully")
    @ApiResponse(responseCode = "404", description = "Room not found")
    @ApiResponse(responseCode = "401", description = "Unauthorized access")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(
            @Parameter(description = "ID of the room to be deleted") @PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }
}