package com.example.cineBDB_managment.service;

import com.example.cineBDB_managment.model.dto.ReservationDto;
import com.example.cineBDB_managment.model.entity.Movie;
import com.example.cineBDB_managment.model.entity.Reservation;
import com.example.cineBDB_managment.model.entity.Room;
import com.example.cineBDB_managment.model.mapper.ReservationMapper;
import com.example.cineBDB_managment.repository.crud.MovieCrudRepository;
import com.example.cineBDB_managment.repository.crud.ReservationCrudRepository;
import com.example.cineBDB_managment.repository.crud.RoomCrudRepository;
import com.example.cineBDB_managment.repository.inter.ReservationRepositoryInterface;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service

public class ReservationService implements ReservationRepositoryInterface {

    private final ReservationCrudRepository reservationCrudRepository;
    private final MovieCrudRepository movieCrudRepository;
    private final RoomCrudRepository roomCrudRepository;
    private final ReservationMapper reservationMapper;
    private final EmailService emailService;

    public ReservationService(ReservationCrudRepository reservationCrudRepository, MovieCrudRepository movieCrudRepository, RoomCrudRepository roomCrudRepository, ReservationMapper reservationMapper, EmailService emailService) {
        this.reservationCrudRepository = reservationCrudRepository;
        this.movieCrudRepository = movieCrudRepository;
        this.roomCrudRepository = roomCrudRepository;
        this.reservationMapper = reservationMapper;
        this.emailService = emailService;
    }

    @Override
    public ReservationDto getReservation(Long idReservation) {
        if (idReservation == null) {
            throw new IllegalArgumentException("El ID de la reserva no puede ser nulo");
        }
        Reservation reservation = reservationCrudRepository.findById(idReservation)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        return reservationMapper.toReservationDto(reservation);
    }

    @Override
    public List<ReservationDto> getAllReservations() {
        List<Reservation> reservations = reservationCrudRepository.findAll();
        if (reservations.isEmpty()) {
            throw new RuntimeException("No se encontraron reservas");
        }
        return reservationMapper.toReservationDto(reservations);
    }

    @Override
    public List<ReservationDto> getReservationsByCustomerEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("El email no puede ser nulo o vacío");
        }
        List<Reservation> reservations = reservationCrudRepository.findByCustomerEmail(email);
        if (reservations.isEmpty()) {
            throw new RuntimeException("No se encontraron reservas para el email especificado");
        }
        return reservationMapper.toReservationDto(reservations);
    }



    @Override
    public List<ReservationDto> getReservationsByMovie(Long movieId) {
        if (movieId == null) {
            throw new IllegalArgumentException("El ID de la película no puede ser nulo");
        }
        List<Reservation> reservations = reservationCrudRepository.findByMovieId(movieId);
        if (reservations.isEmpty()) {
            throw new RuntimeException("No se encontraron reservas para la película especificada");
        }
        return reservationMapper.toReservationDto(reservations);
    }

    @Override
    public List<String> getBookedSeats(Long roomId, LocalDateTime schedule) {
        if (roomId == null) {
            throw new IllegalArgumentException("El ID de la sala no puede ser nulo");
        }
        if (schedule == null) {
            throw new IllegalArgumentException("El horario no puede ser nulo");
        }
        return reservationCrudRepository.findBookedSeatsByRoomAndSchedule(roomId, schedule)
                .stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAvailableSeats(Long roomId, LocalDateTime schedule) {
        // Validaciones básicas
        if (roomId == null) {
            throw new IllegalArgumentException("El ID de la sala no puede ser nulo");
        }
        if (schedule == null) {
            throw new IllegalArgumentException("El horario no puede ser nulo");
        }
        if (schedule.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("No se pueden consultar asientos para horarios pasados");
        }

        // Obtener la sala para conocer su capacidad
        Room room = roomCrudRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Sala no encontrada"));

        // Obtener asientos ya reservados
        List<String> bookedSeats = getBookedSeats(roomId, schedule);

        // Generar todos los asientos posibles según capacidad de la sala
        // Ejemplo: Sala con 50 asientos (A1-A10, B1-B10, C1-C10, D1-D10, E1-E10)
        List<String> allSeats = generateAllSeats(room.getCapacity());

        // Filtrar los asientos disponibles
        return allSeats.stream()
                .filter(seat -> !bookedSeats.contains(seat))
                .collect(Collectors.toList());
    }

    private List<String> generateAllSeats(int capacity) {
        List<String> seats = new ArrayList<>();
        int rows = (int) Math.ceil(capacity / 10.0); // Asumimos 10 asientos por fila
        char rowChar = 'A';

        for (int i = 0; i < rows; i++) {
            for (int j = 1; j <= 10; j++) {
                if (seats.size() >= capacity) break;
                seats.add(rowChar + String.valueOf(j));
            }
            rowChar++;
        }
        return seats;
    }


    @Override
    public ReservationDto saveReservation(ReservationDto reservationDto) {
        if (reservationDto == null) {
            throw new IllegalArgumentException("El ReservationDto no puede ser nulo");
        }
        // Validar que la película y la sala existan
        Movie movie = movieCrudRepository.findById(reservationDto.movieIdDto())
                .orElseThrow(() -> new RuntimeException("Película no encontrada"));
        Room room = roomCrudRepository.findById(reservationDto.roomIdDto())
                .orElseThrow(() -> new RuntimeException("Sala no encontrada"));

        // Validar asientos disponibles
        List<String> bookedSeats = getBookedSeats(room.getId(), reservationDto.scheduleDto());
        if (reservationDto.selectedSeatsDto().stream().anyMatch(bookedSeats::contains)) {
            throw new RuntimeException("Algunos asientos ya están reservados");
        }

        // Guardar reserva
        Reservation reservation = reservationMapper.toReservationEntity(reservationDto);
        reservation.setMovie(movie);
        reservation.setRoom(room);
        Reservation savedReservation = reservationCrudRepository.save(reservation);

        // Enviar correo de confirmación
        emailService.sendReservationConfirmation(
                reservationDto.customerEmailDto(),
                movie.getTitle(),
                room.getName(),
                reservationDto.scheduleDto(),
                reservationDto.selectedSeatsDto()
        );

        return reservationMapper.toReservationDto(savedReservation);
    }

    @Override
    public void deleteReservation(Long idReservation) {
        if (idReservation == null) {
            throw new IllegalArgumentException("El ID de la reserva no puede ser nulo");
        }
        reservationCrudRepository.deleteById(idReservation);
    }

    @Override
    public Map<String, Long> generateReservationReport() {
        // Obtener todas las reservas
        List<Reservation> reservations = reservationCrudRepository.findAll();

        // Agrupar por película y contar reservas
        return reservations.stream()
                .collect(Collectors.groupingBy(
                        r -> r.getMovie().getTitle(),
                        Collectors.counting()
                ));
    }
}