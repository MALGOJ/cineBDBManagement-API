package com.example.cineBDB_managment.repository.inter;

import com.example.cineBDB_managment.model.dto.ReservationDto;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ReservationRepositoryInterface {

    ReservationDto getReservation(Long idReservation);
    List<ReservationDto> getAllReservations();
    List<ReservationDto> getReservationsByCustomerEmail(String email);
    List<ReservationDto> getReservationsByMovie(Long movieId);
    List<String> getBookedSeats(Long roomId, LocalDateTime schedule);
    List<String> getAvailableSeats(Long roomId, LocalDateTime schedule);
    ReservationDto saveReservation(ReservationDto reservationDto);
    void deleteReservation(Long idReservation);
    Map<String, Long> generateReservationReport();
}