package com.backend.booking.model.filter;

import com.backend.booking.validation.HotelFilterValid;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@HotelFilterValid
public class HotelFilter {

    private Integer pageSize;
    private Integer pageNumber;

    private String name;
    private String adTitle;
    private String city;
    private String address;
    private Double maxDistanceToCenter;
    private Double minRating;
    private Double maxRating;
    private int minNumberOfRatings;
}
