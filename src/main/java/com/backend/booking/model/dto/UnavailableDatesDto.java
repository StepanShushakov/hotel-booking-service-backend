package com.backend.booking.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UnavailableDatesDto {
    private LocalDate unavailableFrom;
    private LocalDate unavailableTo;
}
