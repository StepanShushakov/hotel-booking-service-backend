package com.backend.booking.model.dto.request;

import lombok.Data;

@Data
public class HotelCreateOrUpdateDto {
    private String name;
    private String adTitle;
    private String city;
    private String address;
    private Double distanceToCenter;
}
