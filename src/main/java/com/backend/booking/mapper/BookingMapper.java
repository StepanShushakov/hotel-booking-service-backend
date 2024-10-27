package com.backend.booking.mapper;

import com.backend.booking.model.dto.BookingDto;
import com.backend.booking.model.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "roomId", source = "room.id")
    BookingDto toBookingDto(Booking booking);
}
