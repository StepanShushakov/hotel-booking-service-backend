package com.backend.booking.model.dto.response;

import com.backend.booking.model.dto.UnavailableDatesDto;
import lombok.Data;

import java.util.List;

@Data
public class RoomDetailsDto {
    private Long id;
    private String title;
    private String description;
    private Integer number;
    private Double price;
    private Integer maxGuests;
    private Long hotelId;
    private List<UnavailableDatesDto> unavailableDates; // Список недоступных дат
}
