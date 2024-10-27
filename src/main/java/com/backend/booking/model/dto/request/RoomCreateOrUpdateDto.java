package com.backend.booking.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoomCreateOrUpdateDto {
    private String title;
    private String description;
    private Integer number;
    private Double price;
    private Integer maxGuests;
    @NotNull
    private Long hotelId; // Связь с отелем
}
