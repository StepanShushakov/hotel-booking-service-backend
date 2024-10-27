package com.backend.booking.model.dto.response;

import lombok.Data;

@Data
public class HotelDtoWithoutRating {
    private Long id;
    private String name;
    private String adTitle;
    private String city;
    private String address;
    private Double distanceToCenter;
}
