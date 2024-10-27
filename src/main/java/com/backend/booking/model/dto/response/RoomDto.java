package com.backend.booking.model.dto.response;

import lombok.Data;

@Data
public class RoomDto {
    private Long id;
    private String title;
    private String description;
    private Double price;
}
