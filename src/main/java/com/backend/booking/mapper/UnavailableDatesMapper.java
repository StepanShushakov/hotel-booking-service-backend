package com.backend.booking.mapper;

import com.backend.booking.model.entity.UnavailableDates;
import com.backend.booking.model.dto.UnavailableDatesDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UnavailableDatesMapper {

    // Маппинг из сущности UnavailableDates в DTO
    UnavailableDatesDto toUnavailableDatesDto(UnavailableDates unavailableDates);

    // Маппинг из DTO в сущность UnavailableDates
    UnavailableDates toUnavailableDates(UnavailableDatesDto unavailableDatesDto);

    // Маппинг списка дат из сущностей в список DTO
    List<UnavailableDatesDto> toUnavailableDatesDtos(List<UnavailableDates> unavailableDates);

    // Маппинг списка дат из DTO в сущности
    List<UnavailableDates> toUnavailableDatesList(List<UnavailableDatesDto> unavailableDatesDtos);

}
