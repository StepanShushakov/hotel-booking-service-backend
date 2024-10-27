package com.backend.booking.model.dto.response;

import lombok.Data;

@Data
public class HotelDtoWithRating {
    private Long id;
    private String name;
    private String adTitle;
    private String city;
    private String address;
    private Double distanceToCenter;
    private Double rating;
    private Integer numberOfRatings;
}
