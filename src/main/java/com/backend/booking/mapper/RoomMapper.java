package com.backend.booking.mapper;

import com.backend.booking.model.dto.request.RoomCreateOrUpdateDto;
import com.backend.booking.model.dto.response.RoomDetailsDto;
import com.backend.booking.model.dto.response.RoomDto;
import com.backend.booking.model.entity.Hotel;
import com.backend.booking.model.entity.Room;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UnavailableDatesMapper.class})
public interface RoomMapper {
    @Mapping(source = "hotelId", target = "hotel", qualifiedByName = "mapHotelById")
    Room toRoom(RoomCreateOrUpdateDto roomDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "hotelId", target = "hotel", qualifiedByName = "mapHotelById")
    void updateRoomFromDto(RoomCreateOrUpdateDto roomDto, @MappingTarget Room room);

    RoomDto toRoomEditDto(Room room);

    @Mapping(source = "hotel.id", target = "hotelId")
    RoomDetailsDto toRoomDetailsDto(Room room);

    @Mapping(source = "hotel.id", target = "hotelId")
    List<RoomDetailsDto> toRoomDetailsDtos(List<Room> rooms);

    @Named("mapHotelById")
    default Hotel mapHotelById(Long hotelId) {
        if (hotelId == null) {
            return null;
        }
        return new Hotel(hotelId);
    }
}
