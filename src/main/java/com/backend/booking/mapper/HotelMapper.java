package com.backend.booking.mapper;

import com.backend.booking.model.dto.response.HotelDtoWithRating;
import com.backend.booking.model.dto.response.HotelDtoWithoutRating;
import com.backend.booking.model.dto.request.HotelCreateOrUpdateDto;
import com.backend.booking.model.entity.Hotel;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface HotelMapper {
    HotelDtoWithoutRating toHotelDtoWithoutRating(Hotel hotel);
    HotelDtoWithRating toHotelDtoWithRating(Hotel hotel);
    Hotel toHotel(HotelCreateOrUpdateDto hotelDto);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateHotelFromDto(HotelCreateOrUpdateDto hotelDto, @MappingTarget Hotel hotel);
}
