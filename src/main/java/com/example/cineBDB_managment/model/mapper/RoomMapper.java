package com.example.cineBDB_managment.model.mapper;

import com.example.cineBDB_managment.model.dto.RoomDto;
import com.example.cineBDB_managment.model.entity.Room;
import org.mapstruct.*;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@Mapper(    componentModel = "spring",  // Integración con Spring
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoomMapper {

    @Mappings({
            @Mapping(source = "id", target = "idRoomDto"),
            @Mapping(source = "name", target = "nameDto"),
            @Mapping(source = "capacity", target = "capacityDto"),
            @Mapping(source = "createdAt", target = "createdAtRoomDto"),
            @Mapping(source = "updatedAt", target = "updatedAtRoomDto")
    })
    RoomDto toRoomDto(Room room);

    List<RoomDto> toRoomDto(List<Room> roomList);

    @InheritInverseConfiguration
    Room toRoomEntity(RoomDto roomDto);

    @InheritInverseConfiguration
    List<Room> toRoomEntity(List<RoomDto> roomDtoList);
}