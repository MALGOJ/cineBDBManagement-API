package com.example.cineBDB_managment.model.mapper;

import com.example.cineBDB_managment.model.dto.ReservationDto;
import com.example.cineBDB_managment.model.entity.Movie;
import com.example.cineBDB_managment.model.entity.Reservation;
import com.example.cineBDB_managment.model.entity.Room;
import org.mapstruct.*;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@Mapper(    componentModel = "spring",  // Integración con Spring
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReservationMapper {

    @Mappings({
            @Mapping(source = "id", target = "idReservationDto"),
            @Mapping(source = "movie.id", target = "movieIdDto"),
            @Mapping(source = "room.id", target = "roomIdDto"),
            @Mapping(source = "customerEmail", target = "customerEmailDto"),
            @Mapping(source = "schedule", target = "scheduleDto"),
            @Mapping(source = "selectedSeats", target = "selectedSeatsDto"),
            @Mapping(source = "createdAt", target = "createdAtReservationDto"),
            @Mapping(source = "updatedAt", target = "updatedAtReservationDto")
    })
    ReservationDto toReservationDto(Reservation reservation);

    @InheritInverseConfiguration
    @Mapping(target = "movie", source = "movieIdDto", qualifiedByName = "idToMovie")
    @Mapping(target = "room", source = "roomIdDto", qualifiedByName = "idToRoom")
    Reservation toReservationEntity(ReservationDto reservationDto);

    // Métodos auxiliares para convertir IDs → Entidades
    @Named("idToMovie")
    default Movie idToMovie(Long id) {
        if (id == null) return null;
        Movie movie = new Movie();
        movie.setId(id);
        return movie;
    }

    @Named("idToRoom")
    default Room idToRoom(Long id) {
        if (id == null) return null;
        Room room = new Room();
        room.setId(id);
        return room;
    }

    List<ReservationDto> toReservationDto(List<Reservation> reservationList);
    List<Reservation> toReservationEntity(List<ReservationDto> reservationDtoList);
}
